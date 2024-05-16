package daeucna.config.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import daeucna.config.batch.MatchingSetup.Cond;
import daeucna.config.batch.MatchingSetup.Matching;
import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class MatchingItemProcessorAuto implements ItemProcessor<Map, Map> {

    MatchingInnerDelingMapper matchingInnerDelingMapper;

    public void setMapper(MatchingInnerDelingMapper matchingInnerDelingMapper) {
    	this.matchingInnerDelingMapper = matchingInnerDelingMapper;
    }
    
    @SuppressWarnings("unchecked")
	public Map process(Map item) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>((Map<String, String>) item.get("jobParameters"));
    	Matching matching = (Matching) item.get("matchingType");
    	Cond condOne = matching.getCondOne();
    	Cond condTwo = matching.getCondTwo();
    	List<String> lCompareField = matching.getCompareField();
    	String mtchType = matching.getType();
    	String mtchTypeName = matching.getTypeName();
    	int mtchNumber = 0;
    	int iUpdated = 0;

    	log.debug("CustomItemProcessorA.params : " + params.toString());
    	log.debug("CustomItemProcessorA.item : " + item.get("cpr_code") + "," + item.get("partn_cpr"));
    	
    	//----------------------------------------------------------------------------
    	//자기법인 업데이트
    	params.put("cprCode", item.get("cpr_code"));
    	params.put("partnCpr", item.get("partn_cpr"));
    	
    	params.put("cond", condOne.getCond() ); //조건리스트
    	params.put("compareKey", matching.getCompareKey() );
    	params.put("makeCompareKey", "'" + mtchType + ":' || " + condOne.getMakeCompareKey() );
    	
    	iUpdated = matchingInnerDelingMapper.setDataMakeCompareKy(params);
    	log.debug("matchingInnerDelingMapper.setDataMakeCompareKy ; " + iUpdated);

    	//상대법인업데이트
    	params.put("cprCode", item.get("partn_cpr"));
    	params.put("partnCpr", item.get("cpr_code"));
    	
    	params.put("cond", condTwo.getCond() ); //조건리스트
    	params.put("compareKey", matching.getCompareKey() );
    	params.put("makeCompareKey", "'" + mtchType + ":' || " + condTwo.getMakeCompareKey() );
    	
    	iUpdated = matchingInnerDelingMapper.setDataMakeCompareKy(params);
    	log.debug("matchingInnerDelingMapper.setDataMakeCompareKy ; " + iUpdated);

    	//----------------------------------------------------------------------------
    	//자기법인 데이타 가져오기
    	params.put("cprCode", item.get("cpr_code"));
    	params.put("partnCpr", item.get("partn_cpr"));
    	params.put("amtField", matching.getAmtField());
    	params.put("cond", condOne.getCond() ); //조건리스트
    	List<Map> lMatchingDataOne = matchingInnerDelingMapper.getMatchingData(params);

    	//상대법인 데이타 가져오기
    	params.put("cprCode", item.get("partn_cpr"));
    	params.put("partnCpr", item.get("cpr_code"));
    	params.put("amtField", matching.getAmtField());
    	params.put("cond", condTwo.getCond() ); //조건리스트
    	List<Map> lMatchingDataTwo = matchingInnerDelingMapper.getMatchingData(params);
    	
    	//----------------------------------------------------------------------------
    	//자기법인 데이타를 맵으로 처리한다.
    	Map<String, List<Map>> mMatchingDataOne = new HashMap<String, List<Map>>();
    	for (Map curMap : lMatchingDataOne) {
    		StringBuffer sbKey = new StringBuffer();
    		for (String curField : lCompareField) {
    			if (sbKey.length() > 0) sbKey.append(":");
    			sbKey.append(curMap.get(curField).toString());
    		}
    		
    		List<Map> curlMap = new ArrayList<Map>();
    		String sKey = sbKey.toString();
    		if (mMatchingDataOne.containsKey(sKey)) curlMap = mMatchingDataOne.get(sKey);
    		curlMap.add(curMap);
    		mMatchingDataOne.put(sKey, curlMap);
    	}

    	//상대법인 데이타를 맵으로 처리한다.
    	Map<String, List<Map>> mMatchingDataTwo = new HashMap<String, List<Map>>();
    	for (Map curMap : lMatchingDataTwo) {
    		StringBuffer sbKey = new StringBuffer();
    		for (String curField : lCompareField) {
    			if (sbKey.length() > 0) sbKey.append(":");
    			sbKey.append(curMap.get(curField).toString());
    		}

    		List<Map> curlMap = new ArrayList<Map>();
    		String sKey = sbKey.toString();
    		if (mMatchingDataTwo.containsKey(sKey)) curlMap = mMatchingDataTwo.get(sKey);
    		curlMap.add(curMap);
    		mMatchingDataTwo.put(sKey, curlMap);
    	}
    	
    	//여기서 매칭 비교
    	//비교 lMatchingDataOne vs mMatchingDataTwo
    	for (String curKey : mMatchingDataOne.keySet()) {
    		if (mMatchingDataTwo.containsKey(curKey)) {
    			List<Map> lMapOne = mMatchingDataOne.get(curKey);
    			List<Map> lMapTwo = mMatchingDataTwo.get(curKey);
    			
    			int iMin = Math.min(lMapOne.size(), lMapTwo.size());
    			if (iMin > 0) mtchNumber++;
    			for (int i=0; i<iMin; i++) {
    				lMapOne.get(i).put("mtchType", mtchType);
    				lMapOne.get(i).put("mtchTypeName", mtchTypeName);
    				lMapOne.get(i).put("mtchNumber", mtchNumber);

    				lMapTwo.get(i).put("mtchType", mtchType);
    				lMapTwo.get(i).put("mtchTypeName", mtchTypeName);
    				lMapTwo.get(i).put("mtchNumber", mtchNumber);
    			}    			
    		}
    	}
    	
    	//----------------------------------------------------------------------------
    	//여기서 결과 업데이트
    	List<Map> lMatchingResultUpdate = new ArrayList<Map>(); //업데이트할 대상
    	String sCompareKey = matching.getCompareKey();

    	//자기법인 데이타 가져오기
    	params.put("cprCode", item.get("cpr_code"));
    	params.put("partnCpr", item.get("partn_cpr"));
    	params.put("cond", condOne.getCond() ); //조건리스트
    	List<Map> lMatchingResultOne = matchingInnerDelingMapper.getMatchingResult(params);
    	
    	Map<String, Map> mMatchingResultOne = new HashMap<String, Map>();
    	for (Map curMap : lMatchingDataOne) {
    		String curComapreKeyVal = (String)curMap.get(sCompareKey);
    		boolean curBlnMatching = curMap.get("mtchType")!=null && curMap.get("mtchNumber")!=null;
    		if (curBlnMatching) mMatchingResultOne.put(curComapreKeyVal, curMap);
    	}
    	for (Map curMap : lMatchingResultOne) {
    		String curComapreKeyVal = (String)curMap.get(sCompareKey);
    		if ( mMatchingResultOne.containsKey(curComapreKeyVal) ) {
    			Map mResult = mMatchingResultOne.get(curComapreKeyVal);
    			curMap.put(matching.getMatchingType() , mResult.get("mtchType"));
    			curMap.put(matching.getMatchingTypeName(), mResult.get("mtchTypeName"));
    			curMap.put(matching.getMatchingNumber(), mResult.get("mtchNumber"));
    			lMatchingResultUpdate.add(curMap);
    		}
    	}

    	//상대법인 데이타 가져오기
    	params.put("cprCode", item.get("partn_cpr"));
    	params.put("partnCpr", item.get("cpr_code"));
    	params.put("cond", condTwo.getCond() ); //조건리스트
    	List<Map> lMatchingResultTwo = matchingInnerDelingMapper.getMatchingResult(params);    	
    	
    	Map<String, Map> mMatchingResultTwo = new HashMap<String, Map>();
    	for (Map curMap : lMatchingDataTwo) {
    		String curComapreKeyVal = (String)curMap.get(sCompareKey);
    		boolean curBlnMatching = curMap.get("mtchType")!=null && curMap.get("mtchNumber")!=null;
    		if (curBlnMatching) mMatchingResultTwo.put(curComapreKeyVal, curMap);
    	}
    	for (Map curMap : lMatchingResultTwo) {
    		String curComapreKeyVal = (String)curMap.get(sCompareKey);
    		if ( mMatchingResultTwo.containsKey(curComapreKeyVal) ) {
    			Map mResult = mMatchingResultTwo.get(curComapreKeyVal);
    			curMap.put(matching.getMatchingType() , mResult.get("mtchType"));
    			curMap.put(matching.getMatchingTypeName(), mResult.get("mtchTypeName"));
    			curMap.put(matching.getMatchingNumber(), mResult.get("mtchNumber"));
    			lMatchingResultUpdate.add(curMap);
    		}
    	}
    	
    	iUpdated = 0;
    	int limit = 1000; //1000건씩 batch
    	List<Map> lUpdated = new ArrayList<Map>();
    	for (Map curMap : lMatchingResultUpdate) {
    		curMap.put("matchingType", matching.getMatchingType());
    		curMap.put("matchingTypeVal", curMap.get(matching.getMatchingType()));
    		curMap.put("matchingTypeName", matching.getMatchingTypeName());
    		curMap.put("matchingTypeNameVal", curMap.get(matching.getMatchingTypeName()));
    		curMap.put("matchingNumber", matching.getMatchingNumber());
    		curMap.put("matchingNumberVal", curMap.get(matching.getMatchingNumber()));
    		lUpdated.add(curMap);
    		if (lUpdated.size() == limit) {
    			matchingInnerDelingMapper.setResult(Map.of("itemList", lUpdated));
    			iUpdated = iUpdated + lUpdated.size();
    			lUpdated.clear();
    		}
    	}
    	if (lUpdated.size() > 0) {
    		matchingInnerDelingMapper.setResult(Map.of("itemList", lUpdated));
    		iUpdated = iUpdated + lUpdated.size();    		
    	}
    	log.debug("Updated OrgData : " + iUpdated + "건");
    	
    	//작업이 정상적으로 작동 되었을 경우 현재 처리하고 있는 데이타 그대로 넘겨준다.
        return item;
    }
}