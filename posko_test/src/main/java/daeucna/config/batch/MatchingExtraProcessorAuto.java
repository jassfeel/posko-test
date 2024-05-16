package daeucna.config.batch;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import daeucna.batch.util.StatisticsUtil;
import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MatchingExtraProcessorAuto {

	private final MatchingInnerDelingMapper matchingInnerDelingMapper;

    @SuppressWarnings("unchecked")
	public void process(Map paramRec, int iCmbnOwnCnt, int iCmbnTranCnt, int iStartOwn, int iStartTran) throws Exception {

    	//Job Create Log
    	UUID uuid = UUID.randomUUID();
    	HashMap<String, String> mt = new HashMap<String, String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String sDate = dateFormat.format(new Date()) + ":" + uuid.toString();
    	
    	Map<String, Object> paramLog = new HashMap<String, Object>();
    	paramLog.put("user_job_id", sDate);
    	paramLog.put("user_job_name", "자동조합매칭(" + paramRec.toString() + ",[" + iCmbnOwnCnt + "," + iStartOwn + "],[" + iCmbnTranCnt + "," + iStartTran + "])");
    	matchingInnerDelingMapper.createUserJob(paramLog);

    	
    	int iUpdated = 0;
    	
    	String sSysSe = (String) paramRec.get("sys_se");
    	String sAccnutYm = (String) paramRec.get("accnut_ym");
    	String sCprCode = (String) paramRec.get("cpr_code");
    	String sPartCpr = (String) paramRec.get("partn_cpr");

    	//작업시작
    	Map<String, Object> mParam = new HashMap<String, Object>();
    	mParam.put("sysSe", sSysSe);
    	mParam.put("accnutYm", sAccnutYm);
    	
    	//----------------------------------------------------------------------------
    	//자기법인 데이타 가져오기
    	mParam.put("cprCode", sCprCode);
    	mParam.put("partnCpr", sPartCpr);
    	List<Map> lMatchingDataOne = matchingInnerDelingMapper.getMatchingExtraDataOne(mParam);

    	//상대법인 데이타 가져오기
    	mParam.put("cprCode", sPartCpr);
    	mParam.put("partnCpr", sCprCode);
    	List<Map> lMatchingDataTwo = matchingInnerDelingMapper.getMatchingExtraDataTwo(mParam);
    	
    	//Combination 데이타 만들기
		List<Map> compResult = new ArrayList<Map>();
		List<List<Map>> llMatchingDataOne = new ArrayList<List<Map>>();
		List<List<Map>> llMatchingDataTwo = new ArrayList<List<Map>>();
    			
		int iEndOwn = lMatchingDataOne.size();
		if (iCmbnOwnCnt == 2) {
			iEndOwn = iStartOwn + 1000;
			if (iEndOwn > lMatchingDataOne.size()) iEndOwn = lMatchingDataOne.size();
		}
		if (iCmbnOwnCnt > 2) {
			iEndOwn = iStartOwn + 100;
			if (iEndOwn > lMatchingDataOne.size()) iEndOwn = lMatchingDataOne.size();
		}
		int iEndTran = lMatchingDataTwo.size();
		if (iCmbnTranCnt == 2) {
			iEndTran = iStartTran + 1000;
			if (iEndTran > lMatchingDataTwo.size()) iEndTran = lMatchingDataTwo.size();
		}
		if (iCmbnTranCnt > 2) {
			iEndTran = iStartTran + 100;
			if (iEndTran > lMatchingDataTwo.size()) iEndTran = lMatchingDataTwo.size();
		}
		
    	StatisticsUtil.reculsion(lMatchingDataOne, compResult, iStartOwn, iEndOwn, iCmbnOwnCnt, llMatchingDataOne);
    	StatisticsUtil.reculsion(lMatchingDataTwo, compResult, iStartTran, iEndTran, iCmbnTranCnt, llMatchingDataTwo);
    	
    	//----------------------------------------------------------------------------
    	//자기법인 데이타를 맵으로 처리한다.
    	Map<BigDecimal, List<List<Map>>> mMatchingDataOne = new HashMap<BigDecimal, List<List<Map>>>();
    	for (List<Map> curlMap : llMatchingDataOne) {
    		BigDecimal bdKey = BigDecimal.ZERO;
    		for(Map curMap : curlMap) {
    			bdKey = bdKey.add((BigDecimal) curMap.get("delng_amt"));
    		}
    		List<List<Map>> curllMap = new ArrayList<List<Map>>();
    		if (mMatchingDataOne.containsKey(bdKey)) curllMap = mMatchingDataOne.get(bdKey);
    		curllMap.add(curlMap);
    		mMatchingDataOne.put(bdKey, curllMap);
    	}

    	//상대법인 데이타를 맵으로 처리한다.
    	Map<BigDecimal, List<List<Map>>> mMatchingDataTwo = new HashMap<BigDecimal, List<List<Map>>>();
    	for (List<Map> curlMap : llMatchingDataTwo) {
    		BigDecimal bdKey = BigDecimal.ZERO;
    		for(Map curMap : curlMap) {
    			bdKey = bdKey.add((BigDecimal) curMap.get("delng_amt"));
    		}
    		List<List<Map>> curllMap = new ArrayList<List<Map>>();
    		if (mMatchingDataTwo.containsKey(bdKey)) curllMap = mMatchingDataTwo.get(bdKey);
    		curllMap.add(curlMap);
    		mMatchingDataTwo.put(bdKey, curllMap);
    	}
    	
    	//여기서 매칭 비교
    	//비교 lMatchingDataOne vs mMatchingDataTwo
    	List<Map> lMatchingResultUpdate = new ArrayList<Map>(); //업데이트할 대상

    	int mtchNumber = 0;
    	String mtchSys = "AUTO";
    	String mtchType = "EX_" + iCmbnOwnCnt + "_" + iCmbnTranCnt;
    	String mtchTypeName = "자기(" + iCmbnOwnCnt + "건Sum), 상대(" + iCmbnTranCnt + "건Sum), 비교(금액)";
    	for (BigDecimal curKey : mMatchingDataOne.keySet()) {
    		if (mMatchingDataTwo.containsKey(curKey)) {
    			List<List<Map>> llMapOne = mMatchingDataOne.get(curKey);
    			List<List<Map>> llMapTwo = mMatchingDataTwo.get(curKey);
    			
				//위와 관련된 동일 레코드가 있는 리스트 삭제
				for (int i=llMapOne.size()-1; i>=0;i--) {
					List<Map> curlMap = llMapOne.get(i);    					
					for (Map curMap : lMatchingResultUpdate) {
						int curIdx = curlMap.indexOf(curMap);
						if (curIdx != -1) {
							llMapOne.remove(i);
							break;
						}    						
					}
				}
				for (int i=llMapTwo.size()-1; i>=0;i--) {
					List<Map> curlMap = llMapTwo.get(i);    					
					for (Map curMap : lMatchingResultUpdate) {
						int curIdx = curlMap.indexOf(curMap);
						if (curIdx != -1) {
							llMapTwo.remove(i);
							break;
						}    						
					}
				}
    			
    			
    			int iMin = Math.min(llMapOne.size(), llMapTwo.size());
    			if (iMin > 0) mtchNumber++;
    			while (iMin > 0) {
    				List<Map> curlMapOne = llMapOne.get(0);
    				for (Map curMap : curlMapOne) {
    					curMap.put("mtch_sys", mtchSys);
    					curMap.put("mtch_ty", mtchType);
    					curMap.put("mtch_ty_nm", mtchTypeName);
    					curMap.put("mtch_ky", mtchNumber);
    					lMatchingResultUpdate.add(curMap);
    				}
					llMapOne.remove(0);

					List<Map> curlMapTwo = llMapTwo.get(0);
    				for (Map curMap : curlMapTwo) {
    					curMap.put("mtch_sys", mtchSys);
    					curMap.put("mtch_ty", mtchType);
    					curMap.put("mtch_ty_nm", mtchTypeName);
    					curMap.put("mtch_ky", mtchNumber);
    					lMatchingResultUpdate.add(curMap);
    				}
					llMapTwo.remove(0);

					//위와 관련된 동일 레코드가 있는 리스트 삭제(추가 업데이트 목록)
    				for (int i=llMapOne.size()-1; i>=0;i--) {
    					List<Map> curlMap = llMapOne.get(i);    					
    					for (Map curMap : curlMapOne) {
    						int curIdx = curlMap.indexOf(curMap);
    						if (curIdx != -1) {
    							llMapOne.remove(i);
    							break;
    						}    						
    					}
    				}
    				for (int i=llMapTwo.size()-1; i>=0;i--) {
    					List<Map> curlMap = llMapTwo.get(i);    					
    					for (Map curMap : curlMapTwo) {
    						int curIdx = curlMap.indexOf(curMap);
    						if (curIdx != -1) {
    							llMapTwo.remove(i);
    							break;
    						}    						
    					}
    				}
        			iMin = Math.min(llMapOne.size(), llMapTwo.size());
    			}
    		}
    	}
    	
    	//----------------------------------------------------------------------------
    	//여기서 결과 업데이트
    	iUpdated = 0;
    	int limit = 1000; //1000건씩 batch
    	List<Map> lUpdated = new ArrayList<Map>();
    	for (Map curMap : lMatchingResultUpdate) {
    		lUpdated.add(curMap);
    		if (lUpdated.size() == limit) {
    			matchingInnerDelingMapper.setExtraResult(Map.of("itemList", lUpdated));
    			iUpdated = iUpdated + lUpdated.size();
    			lUpdated.clear();
    		}
    	}
    	if (lUpdated.size() > 0) {
    		matchingInnerDelingMapper.setExtraResult(Map.of("itemList", lUpdated));
    		iUpdated = iUpdated + lUpdated.size();    		
    	}
    	log.debug("Updated OrgData : " + iUpdated + "건");
    	
    	//작업종료에 대한 로그 업데이트
    	paramLog.put("exit_code", "0");
    	paramLog.put("exit_message", "");    	
    	matchingInnerDelingMapper.finishUserJob(paramLog);
    	
    }
    
}