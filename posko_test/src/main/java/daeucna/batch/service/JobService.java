package daeucna.batch.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;

import daeucna.batch.util.FileUtil;
import daeucna.config.batch.MatchingExtraProcessorAuto;
import daeucna.config.batch.MatchingSetup;
import daeucna.config.batch.MatchingSetup.Matching;
import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import daeucna.mapper.secondary.batch.OracleMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobService {

	@Value("${pytyon.path}")
	String sPythonPrg;

	@Value("${python.ai.target}")
	String sPythonAiTarget;
	
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MatchingInnerDelingMapper matchingInnerDelingMapper;

    @Autowired
    private OracleMapper oracleMapper;
    
    
    @SuppressWarnings("rawtypes")
	@Async("commAsync")
    public void matchingJob(String jobGroupId, Map<String, String> params) throws Exception {
    	
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_group", jobGroupId);
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "자동매칭(" + params.toString() + ")");
    	matchingInnerDelingMapper.createUserJob(paramLog);
    	
    	long startTime = System.currentTimeMillis();
    	log.info("Job Started : " + startTime);
    	log.debug("params=" + params.toString());
    	
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");
    	MatchingSetup matchingSetup = (MatchingSetup) FileUtil.strToObj(sb.toString(), MatchingSetup.class);

    	String sJobTypeList = params.get("jobType").toUpperCase();
    	List<String> lJobType = Arrays.asList(sJobTypeList != null?sJobTypeList.split(","):new String[0]);

    	//파라미터가 ALL일 경우 ALL 대신 등록된 모든 Type를 넣어준다.
    	if (lJobType.size() > 0 && lJobType.indexOf("ALL") != -1) {
    		lJobType = new ArrayList();
    		for (Matching matching : matchingSetup.getMatchingSetup()) {
    			if (matching.getActive()) {
    				lJobType.add(matching.getType());
    			} else {
    				log.info("JobType(" + matching.getType() + ") is Disabled");
    			}
    		}
    	}
    	
    	
    	for (String sJobType : lJobType) {
        	log.info("Current running job type: " + sJobType);
        	JobExecution jobExe = invokeJob("matchingInnerDelng", sJobType, params);
        	if (!jobExe.getStatus().equals(BatchStatus.COMPLETED)) {
        		throw new Exception("Job execution error : Batchstatus(" + jobExe.getStatus() + "), ExitStatus(" + jobExe.getExitStatus() + ")" );
        	}
    	}
    	
    	long endTime = System.currentTimeMillis();
    	log.info("Job Type : " + lJobType.toString());
    	log.info("Job Ended: " + endTime);
    	log.info("Running Time : " + (endTime - startTime) + "ms");

    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    	
    }
    

    @SuppressWarnings("rawtypes")
	@Async("aiAsync")
    public void extraJobSub(String jobGroupId, Map paramRec) throws Exception {
    	
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_group", jobGroupId);
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "Extra매칭(" + paramRec.toString() + ")");
    	matchingInnerDelingMapper.createUserJob(paramLog);
    	
    	long startTime = System.currentTimeMillis();
    	log.info("Job Started : " + startTime);
    	log.debug("params=" + paramRec.toString());

    	MatchingExtraProcessorAuto matchingExtraProcessorAuto = new MatchingExtraProcessorAuto(matchingInnerDelingMapper);
    	
    	//2건씩 합산 매칭일 경우 최대 20000건 까지
    	for (int i=0; i<20000;i=i+1000) {
        	matchingExtraProcessorAuto.process(paramRec, 1, 2, 0, i);
    	}
    	for (int i=0; i<20000;i=i+1000) {
        	matchingExtraProcessorAuto.process(paramRec, 2, 1, i, 0);
    	}
    	for (int i=0; i<20000;i=i+1000) {
        	matchingExtraProcessorAuto.process(paramRec, 2, 2, i, i);
    	}
    	
    	//3건씩 매칭일 경우 최대 5000건 까지
    	for (int i=0; i<2000;i=i+100) {
        	matchingExtraProcessorAuto.process(paramRec, 1, 3, 0, i);
    	}
    	for (int i=0; i<2000;i=i+100) {
        	matchingExtraProcessorAuto.process(paramRec, 3, 1, i, 0);
    	}
    	
    	long endTime = System.currentTimeMillis();
    	log.info("Job Ended: " + endTime);
    	log.info("Running Time : " + (endTime - startTime) + "ms");

    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    	
    }    

    @SuppressWarnings("rawtypes")
	@Async("aiAsync")
    public void aiJobSub(String jobGroupId, Map paramRec) throws Exception {
    	

    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_group", jobGroupId);
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "AI매칭(" + paramRec.toString() + ")");
    	matchingInnerDelingMapper.createUserJob(paramLog);

    	
    	long startTime = System.currentTimeMillis();
    	log.info("Job Started : " + startTime);
    	log.debug("params=" + paramRec.toString());
    	
    	String sSysSe = (String) paramRec.get("sys_se");
    	String sAccnutYm = (String) paramRec.get("accnut_ym");
    	String sCprCode = (String) paramRec.get("cpr_code");
    	String sPartCpr = (String) paramRec.get("partn_cpr");
    	String sDelngCrncy = (String) paramRec.get("delng_crncy");

    	log.debug("call python");
    	new ProcessExecutor()
	    	.command(sPythonPrg, sPythonAiTarget, sSysSe, sAccnutYm, sCprCode, sPartCpr, sDelngCrncy)
	        .redirectOutput(new LogOutputStream() {
	          @Override
	          protected void processLine(String line) {
	            log.info(line);
	          }
	        })
	        .execute();   	
     	
    	long endTime = System.currentTimeMillis();
    	log.info("Job Ended: " + endTime);
    	log.info("Running Time : " + (endTime - startTime) + "ms");

    	
    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    	
    }
    
    
    public JobExecution invokeJob(String jobName, String jobType, Map<String, String> params) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        final String date = dateFormat.format(new Date()) + ":" + uuid.toString();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("syncDate", date)
                .addString("jobType", jobType)
                .addString("sysSe", params.get("sysSe"))
                .addString("accnutYm", params.get("accnutYm"))
                .addString("searchCond", params.get("searchCond"))
                .toJobParameters(); 

        var jobToStart = context.getBean(jobName, Job.class);
        JobExecution jobExe = jobLauncher.run(jobToStart, jobParameters);
        
        return jobExe;
    }
    
    
    
    @SuppressWarnings("rawtypes")
	@Async("commAsync")
    public void createData(String jobGroupId, Map<String, String> params) throws Exception {
    	
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_group", jobGroupId);
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "작업데이타생성(" + params.toString() + ")");
    	matchingInnerDelingMapper.createUserJob(paramLog);

    	
    	long startTime = System.currentTimeMillis();
    	log.info("Create Data Started : " + startTime);
    	log.debug("params=" + params.toString());

    	//기존데이타 삭제
		int iDeleted = matchingInnerDelingMapper.deleteOriginalData(params);
    	log.debug("Deleted OrgData : " + iDeleted + "건");
    	
		//신규데이타 생성
    	//매칭키에 대한 정보 (sql로 조인하여 조회하기에는 너무 느리고 데이타 중복도 발생함)
    	List<Map> lMatchingInfo = oracleMapper.getMatchingInfo(params);
    	Map<String, Map> mMatchingInfo = new HashMap<String, Map>();
    	for (Map curMap : lMatchingInfo) {
    		String sKey = String.valueOf(curMap.get("SEQ"));
    		mMatchingInfo.put(sKey, curMap);
    	}

    	List<Map> lOrgData = oracleMapper.getOriginalData(params);    	
    	int iInserted = 0;
    	int limit = 1000; //1000건씩 batch
    	List<Map> lInserted = new ArrayList<Map>();
    	for (Map curRec : lOrgData) {
    		String sKey = String.valueOf(curRec.get("SEQ"));
    		Map curMatchingInfo = mMatchingInfo.get(sKey);
    		if (curMatchingInfo != null) {
        		curRec.put("MATCHING_CAUSE", curMatchingInfo.get("MATCHING_CAUSE"));
        		curRec.put("MATCH_KEY", curMatchingInfo.get("MATCH_KEY"));
    		}
    		lInserted.add(curRec);
    		if (lInserted.size() == limit) {
    			matchingInnerDelingMapper.insertOriginalData(Map.of("itemList", lInserted));
    			iInserted = iInserted + lInserted.size();
    			lInserted.clear();
    		}
    	}
    	if (lInserted.size() > 0) {
    		matchingInnerDelingMapper.insertOriginalData(Map.of("itemList", lInserted));
			iInserted = iInserted + lInserted.size();    		
    	}
    	log.info("Inserted OrgData : " + iInserted + "건");
    	
    	iDeleted = matchingInnerDelingMapper.deleteData(params);
    	log.debug("Deleted Work Data : " + iDeleted + "건");
    	iInserted = matchingInnerDelingMapper.insertDataFromOriginal(params);
    	log.info("Inserted Work Data : " + iInserted + "건");
    	
    	iDeleted = matchingInnerDelingMapper.deleteDataAi(params);
    	log.debug("Deleted Work AI Data : " + iDeleted + "건");
    	iInserted = matchingInnerDelingMapper.insertDataAiFromOriginal(params);
    	log.info("Inserted Work AI Data : " + iInserted + "건");
    	
    	long endTime = System.currentTimeMillis();
    	log.info("Create Data Ended : " + endTime);
    	log.info("Running Time : " + (endTime - startTime) + "ms");    
    	
    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    }    

    
    
    @SuppressWarnings("rawtypes")
	@Async("commAsync")
    public void returnRwsultData(String jobGroupId, Map<String, String> params) throws Exception {
    	
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_group", jobGroupId);
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "결과데이타리턴(" + params.toString() + ")");
    	matchingInnerDelingMapper.createUserJob(paramLog);
    	
    	long startTime = System.currentTimeMillis();
    	log.info("Update Data Started : " + startTime);
    	log.debug("params=" + params.toString());

    	//기존데이타 삭제
		int iUpdated = matchingInnerDelingMapper.updateNewMatchKey(params);
    	log.debug("Updated OrgData : " + iUpdated + "건");

    	long endTime = System.currentTimeMillis();
    	log.info("Update Data Ended : " + endTime);
    	log.info("Running Time : " + (endTime - startTime) + "ms");    	
    	
    	
    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    	
    }      
}
