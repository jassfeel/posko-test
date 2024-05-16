package daeucna.config.batch;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import daeucna.batch.util.FileUtil;
import daeucna.config.batch.MatchingSetup.Matching;
import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class MatchingItemReader {

	@Value("#{jobParameters}")
	private final Map<String, String> params;

    @Autowired
    MatchingInnerDelingMapper matchingInnerDelingMapper;
	
	/*
	 * 자기 상대 법인단위로 작업할 단위를 읽는다.
	 */
    public List<Map> customRead(){
    	log.debug("customRead.params : " + this.params.toString());
    	// customRead.params : {sysSe=KUMKANG, searchCond=mtch_ky is null, run.id=1, syncDate=2024-02-02-03-52-27:50deede8-ce3d-4841-ba65-f733bdd38533, jobType=F, accnutYm=202112}
    	String sMatchType = this.params.get("jobType");
    	StringBuffer sb = FileUtil.readFileToString("matchingSetup.json");
    	MatchingSetup matchingSetup = (MatchingSetup) FileUtil.strToObj(sb.toString(), MatchingSetup.class);
    	Matching matcning = matchingSetup.getMatching(sMatchType);
    	
    	List<Map> retData = matchingInnerDelingMapper.getCustomItemReadData(this.params);
    	
    	//레코드에 jobParameters 정보 추가
    	for (Map rec : retData) {
        	rec.put("jobParameters", this.params);
        	rec.put("matchingType", matcning);
    	}
        
    	log.debug(">> customRead >> " + FileUtil.objToStr(retData));
        return retData;
    }
	
}
