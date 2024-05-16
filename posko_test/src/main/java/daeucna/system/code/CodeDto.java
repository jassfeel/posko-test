package daeucna.system.code;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CodeDto {	
	private String rowStatus;

    private String codeTy;
    private String cmmnCode;

    private String mngIem1;
    private String mngIem2;
    private String mngIem3;
    private String mngIem4;
    private BigDecimal ordr;
    private String useAt;

    private String lcal;
    private String cmmnCodeNm;
    
    private String register;
    private String inputDe;
    private String updusr;
    private String updtDe;
}
