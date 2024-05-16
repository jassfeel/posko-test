package daeucna.batch.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import daeucna.batch.service.JobService;
import daeucna.batch.util.FileUtil;
import daeucna.config.batch.MatchingSetup;
import daeucna.config.batch.MatchingSetup.Matching;
import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/job")
@Slf4j
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private MatchingInnerDelingMapper matchingInnerDelingMapper;
    

    @PostMapping("/create")
    public Map<String, String> createJob( @RequestBody Map<String, String> params) throws Exception {
    	
    	/*
    	 * {                                
    	 *   "sysSe": "LS_ALL",             
    	 *   "accnutYm": "202306",          
    	 * }  
    	 */            
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	String sJobGroup = uuid.toString();
    	
    	log.debug("Start Create Job");
    	jobService.createData(sJobGroup, params);
    	log.debug("End Create Job");
    	
    	Map<String, String> rtnMap = new HashMap<String, String>();
    	rtnMap.put("jobGroupId", sJobGroup);
    	rtnMap.put("jobMessage", "신규 작업데이타를 생성합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.");
    	
        return rtnMap;
    }
    
    
    @PostMapping("/matching")
    public String matchingJob( @RequestBody Map<String, String> params) throws Exception {
    	
    	/*
    	 * {                                
    	 *   "sysSe": "LS_ALL",             
    	 *   "accnutYm": "202306",          
    	 * }  
    	 */                           
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	String sJobGroup = uuid.toString();
    	
    	log.debug("Start Matching Job");
    	jobService.matchingJob(sJobGroup, params);
    	log.debug("End Matching Job");
    	
    	Map<String, String> rtnMap = new HashMap<String, String>();
    	rtnMap.put("jobGroupId", sJobGroup);
    	rtnMap.put("jobMessage", "매칭작업을 시작합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.");
    	    	
        return "매칭작업을 시작합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.";
    }
    
    @PostMapping("/extramatching")
    public Map<String, String> extraMatchingJob( @RequestBody Map<String, String> params) throws Exception {
    	
    	/*
    	 * {                                
    	 *   "sysSe": "LS_ALL",             
    	 *   "accnutYm": "202306",          
    	 * }  
    	 */                       
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	String sJobGroup = uuid.toString();
    	
    	log.debug("Start Extra Matching Job");
    	List<Map> retData = matchingInnerDelingMapper.getCustomItemReadData(params);
//    	List<Map> retData = new ArrayList<Map>();
//    	Map m = new HashMap();
//    	m.put("sys_se", "LS_ALL"); 
//    	m.put("accnut_ym", "202311"); 
//    	m.put("cpr_code", "A15300"); 
//    	m.put("partn_cpr", "A01100"); 
//    	retData.add(m);
    	for(Map curMap : retData) {
    		jobService.extraJobSub(sJobGroup, curMap);
    	}
    	log.debug("End Extra Matching Job");
    	
    	Map<String, String> rtnMap = new HashMap<String, String>();
    	rtnMap.put("jobGroupId", sJobGroup);
    	rtnMap.put("jobMessage", "Extra 매칭작업을 시작합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.");
    	
        return rtnMap;
    }      
    
    
    @PostMapping("/aimatching")
    public Map<String, String> aiMatchingJob( @RequestBody Map<String, String> params) throws Exception {
    	
    	/*
    	 * {                                
    	 *   "sysSe": "LS_ALL",             
    	 *   "accnutYm": "202306",          
    	 * }  
    	 */             
    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	String sJobGroup = uuid.toString();
    	
    	log.debug("Start AI Matching Job");
    	List<Map> retData = matchingInnerDelingMapper.getAiReadData(params);
    	for(Map curMap : retData) {
    		jobService.aiJobSub(sJobGroup, curMap);
    	}
    	log.debug("End AI Matching Job");
    	
    	Map<String, String> rtnMap = new HashMap<String, String>();
    	rtnMap.put("jobGroupId", sJobGroup);
    	rtnMap.put("jobMessage", "AI 매칭작업을 시작합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.");
    	
        return rtnMap;
    }    

    @PostMapping("/return")
    public Map<String, String> returnJob( @RequestBody Map<String, String> params) throws Exception {
    	
    	/*
    	 * {                                
    	 *   "sysSe": "LS_ALL",             
    	 *   "accnutYm": "202306",          
    	 * }  
    	 */        
    	UUID uuid = UUID.randomUUID();
    	String sJobGroup = uuid.toString();

    	log.debug("Start Return Job");
    	jobService.returnRwsultData(sJobGroup, params);
    	log.debug("End Return Job");
    	
    	Map<String, String> rtnMap = new HashMap<String, String>();
    	rtnMap.put("jobGroupId", sJobGroup);
    	rtnMap.put("jobMessage", "매칭결과 반영을 시작합니다. 작업이 끝난후 작업결과는 별도로 확인 바랍니다.");
    	
        return rtnMap;
    }
    
    
    @GetMapping("/configJobStr")
    public String configJobStr() throws Exception {
    	
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");

    	return sb.toString();
    }

    @GetMapping("/configJobJsonObj")
    public JsonObject configJobJsonObj() throws Exception {
    	
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");
    	JsonObject jobj = FileUtil.strToJsonObj(sb.toString());

    	return jobj;
    }

    @GetMapping("/configJobObj")
    public Object configJobObj() throws Exception {
    	
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");
    	MatchingSetup matchingSetup = (MatchingSetup) FileUtil.strToObj(sb.toString(), MatchingSetup.class);

    	return matchingSetup;
    }

    @GetMapping("/config")
    public Object config() throws Exception {
    	return configJobObj();
    }

    @GetMapping("/config/jobList")
    public Object configJobList() throws Exception {
    	
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");
    	MatchingSetup matchingSetup = (MatchingSetup) FileUtil.strToObj(sb.toString(), MatchingSetup.class);

    	List<String> lJobList = new ArrayList<String>();
    	for (Matching curMatching : matchingSetup.getMatchingSetup()) {
    		lJobList.add(curMatching.getType() + ':' + curMatching.getTypeName());
    	}
    	
    	return lJobList;
    }

    @GetMapping("/batchJobInfo/{jobExecutionId}")
    public Object statusBatchJob(@PathVariable(name = "jobExecutionId") Integer iJobExecutionId) throws Exception {
    	Map<String, Object> rtnVal = new HashMap<String, Object>();
    	
    	Map<String, Object> mParam = new HashMap<String, Object>();
    	mParam.put("jobExecutionId", iJobExecutionId);
    	List<Map> lmJob = matchingInnerDelingMapper.getJobStatus(mParam);
    	if (lmJob.size() > 0) rtnVal.put("JobStatus", lmJob.get(0));
    	    	
    	List<Map> lmParameter = matchingInnerDelingMapper.getJobParameter(mParam);
    	if (lmParameter.size() > 0) lmJob.get(0).put("Parameter", lmParameter.get(0));

    	List<Map> lmStep = matchingInnerDelingMapper.getStepStatus(mParam);
    	if (lmStep.size() > 0) lmJob.get(0).put("StepsStatus", lmStep);
    	
    	
    	return rtnVal;
    }

    @GetMapping("/userJobInfo/{userJobGroup}")
    public Object statusBatchJob(@PathVariable(name = "userJobGroup") String sUserJobGroup) throws Exception {
    	Map<String, Object> rtnVal = new HashMap<String, Object>();
    	
    	Map<String, Object> mParam = new HashMap<String, Object>();
    	mParam.put("userJobGroup", sUserJobGroup);
    	List<Map> lmJob = matchingInnerDelingMapper.getUserJobStatus(mParam);
    	rtnVal.put("JobList", lmJob);
    	boolean blnStatus = true;
    	for (Map curMap : lmJob) {
    		String ScurStatus = (String) curMap.get("status"); 
    		if (!"Finished".equalsIgnoreCase(ScurStatus)) {
    			blnStatus = false;
    			break;
    		}
    	}
    	rtnVal.put("JobStatus", blnStatus);
    	
    	
    	return rtnVal;
    }
    
}
