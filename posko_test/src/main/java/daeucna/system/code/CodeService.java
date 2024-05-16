package daeucna.system.code;

import java.util.List;
import java.util.Map;

public interface CodeService {
	
	@SuppressWarnings("rawtypes")	
    public List<CodeDto> getCmmnCode(CodeDto param);

	@SuppressWarnings("rawtypes")	
    public List<CodeDto> saveCmmnCode(CodeSaveDto params);
	
}