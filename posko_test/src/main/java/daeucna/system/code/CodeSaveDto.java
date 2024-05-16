package daeucna.system.code;

import java.util.List;

import lombok.Data;

@Data
public class CodeSaveDto {	
	private CodeDto searchCond;
	private List<CodeDto> saveData;
}
