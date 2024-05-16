package daeucna.config.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import daeucna.mapper.primary.batch.MatchingInnerDelingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingItemWriter<T> {
	
    @Autowired
    MatchingInnerDelingMapper matchingInnerDelingMapper;
	
    @SuppressWarnings("unchecked")
	public void customWrite(T item){
    	Map<String, String> params = (Map<String, String>) ((Map) item).get("jobParameters");

    	log.debug("customWrite.params : " + params.toString());
    	
//    	matchingInnerDelingMapper.setTest((Map) item);
        log.debug("item test = " + item);
    }
	
}
