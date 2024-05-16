package daeucna.system.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import daeucna.system.code.CodeDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeController {
	@Autowired
    private final CodeService codeService;

    @PostMapping("/cmmncode")
    public ResponseEntity<List<CodeDto>> getCmmnCode(@RequestBody CodeDto param) {
    	List<CodeDto> rtnVal = new ArrayList<CodeDto>();
    	
    	rtnVal = codeService.getCmmnCode(param);
    	
        return ResponseEntity.ok(rtnVal);
    }

    @PostMapping("/saveCmmncode")
    public ResponseEntity<List<CodeDto>> saveCmmnCode(@RequestBody CodeSaveDto params) {
    	
    	List<CodeDto> rtnVal = new ArrayList<CodeDto>();
    	
    	rtnVal = codeService.saveCmmnCode(params);
    	
        return ResponseEntity.ok(rtnVal);
    }
    
}