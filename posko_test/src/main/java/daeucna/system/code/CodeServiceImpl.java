package daeucna.system.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import daeucna.config.security.utils.CommonJson;
import daeucna.mapper.primary.system.CodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor={Exception.class})
public class CodeServiceImpl implements CodeService {
	@Autowired
	private CodeMapper codeMapper;
	
	
	@Override
    public List<CodeDto> getCmmnCode(CodeDto param) {
		List<CodeDto> lCodeDto = new ArrayList<CodeDto>();
		
		lCodeDto = codeMapper.getCmmnCode(param);
		
		log.info("getCmmnCode");
		return lCodeDto;
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<CodeDto> saveCmmnCode(CodeSaveDto params) {
		
    	//삭제처리먼저
    	for (CodeDto curRec : params.getSaveData()) {
    		String sRowStatus = curRec.getRowStatus();
    		if ("D".indexOf(sRowStatus) > -1) {
    			//코드
    			codeMapper.deleteCmmnCode(curRec);
    			//코드Nls
    			codeMapper.deleteCmmnCodeNls(curRec);
    		}
    	}
    	//신규및 업데이트 처리
    	for (CodeDto curRec : params.getSaveData()) {
    		String sRowStatus = curRec.getRowStatus();
    		if ("N,U".indexOf(sRowStatus) > -1) {
    			//코드
    			codeMapper.saveCmmnCode(curRec);
    			//코드Nls
    			codeMapper.saveCmmnCodeNls(curRec);
    		}
    	}
    	
    	List<CodeDto>lCodeDto = codeMapper.getCmmnCode(params.getSearchCond());
		
		log.info("saveCmmnCode");
		return lCodeDto;
	}

	
}