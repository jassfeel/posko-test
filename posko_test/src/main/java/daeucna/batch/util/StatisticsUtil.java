package daeucna.batch.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatisticsUtil {

//	public static void main(String[] args) {
//
//		List<Map> arr = new ArrayList<Map>();
//		arr.add(Map.of("col", 100));
//		arr.add(Map.of("col", 200));
//		arr.add(Map.of("col", 300));
//		arr.add(Map.of("col", 400));
//		arr.add(Map.of("col", 500));
//
//		List<Map> result = new ArrayList<Map>();
//		List<List<Map>> resultAll = new ArrayList<List<Map>>();
//		reculsion(arr, result, 3, arr.size(), 2, resultAll);
//		
//		log.info("resultAll=" + resultAll.toString());
//	}
	
	/**
	 * 조합 구하기
	 * 
	 * @param arr    : 기준 리스트
	 * @param result : 결과를 담아줄 리스트
	 * @param index  : 반복문 시작 인덱스
	 * @param n      : 전체 갯수
	 * @param r      : 뽑을 갯수
	 */
	public static void reculsion(List<Map> arr, List<Map> result, int index, int n, int r, List<List<Map>> resultAll) {

		if (r == 0) {

			log.debug("Reuslt=" + result.toString());
			List<Map> lmConfirmList = new ArrayList<Map>();
			lmConfirmList.addAll(result);
			resultAll.add(lmConfirmList);

			return;
		}

		for (int i = index; i < n; i++) {

			result.add(arr.get(i));
			reculsion(arr, result, i + 1, n, r - 1, resultAll);
			result.remove(result.size() - 1);
		}
		
	}

}
