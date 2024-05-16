package daeucna.mapper.primary.batch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchingInnerDelingMapper {
	
	/*
	 * 작업리스트
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getCustomItemReadData(Map param);

	
	/**
	 * 작업키 업데이트 (파라미터 : 자기데이타/상대데이타)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int setDataMakeCompareKy(Map param);

	
	/**
	 * 매칭작업할 데이타 가져오기(파라미터 : 자기데이타/상대데이타)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMatchingData(Map param);
	
	/**
	 * 업데이트할 데이타 가져오기(파라미터 : 자기데이타/상대데이타)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMatchingResult(Map param);

	/**
	 * 결과 업데이트
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int setResult(Map param);
	

	
	
	/**
	 * Original Data 삭제
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int deleteOriginalData(Map param);
	
	
	/**
	 * Original Data 생성
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int insertOriginalData(Map param);
	

	/**
	 * 작업영역 데이타 삭제
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int deleteData(Map param);

	/**
	 * 작업영역 데이타 생성(From Original Data)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int insertDataFromOriginal(Map param);

	/**
	 * 작업영역 ai데이타 삭제
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int deleteDataAi(Map param);

	/**
	 * 작업영역 데이타 생성(From Original Data)
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int insertDataAiFromOriginal(Map param);
	
	/**
	 * 값을 돌려주기전 월별 새로운 일치키 생성
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateNewMatchKey(Map param);

	/**
	 * Extra matching을 위한 자료 조회
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMatchingExtraDataOne(Map param);

	/**
	 * Extra matching을 위한 자료 조회
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getMatchingExtraDataTwo(Map param);

	/**
	 * Extra matching 데이타 적용
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int setExtraResult(Map param);
	
	/**
	 * User Job Start
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int createUserJob(Map param);

	/**
	 * User Job End
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int finishUserJob(Map param);

	/*
	 * ai 작업리스트
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getAiReadData(Map param);

	/*
	 * JOB Instance
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getJobInstance(Map param);

	/*
	 * JOB Status
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getJobStatus(Map param);

	/*
	 * JOB Parameter
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getJobParameter(Map param);

	/*
	 * Step Status
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getStepStatus(Map param);

	/*
	 * User Job Status
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getUserJobStatus(Map param);
	
}