package daeucna.mapper.secondary.batch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OracleMapper {
	
	/*
	 * Original Data 조회
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getOriginalData(Map param);

	/*
	 * Original Data 조회
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMatchingInfo(Map param);
	
	/**
	 * 원 테이블에 결과값 업데이트
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateMatchingResult(Map param);
	
	
}