package daeucna.config.batch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MatchingSetup {
	
	List<Matching> matchingSetup;
	
	@Data
	public class Matching {
		String type;
		String typeName;
		Cond condOne;
		Cond condTwo;
		List<String> uniqueKey;
		String compareKey;
		List<String> compareField;
		String amtField;
		String matchingType;
		String matchingTypeName;
		String matchingNumber;
		Boolean active;

		public Matching() {
			this.uniqueKey = new ArrayList<String>();
			this.condOne = new Cond();
			this.condTwo = new Cond();
			this.compareField = new ArrayList<String>();
		}
		
	};
	
	@Data
	public class Cond {
		List<String> cond;
		List<String> makeCompareKey;

		public Cond() {
			this.cond = new ArrayList<String>();
			this.makeCompareKey = new ArrayList<String>();
		}
		
		public String getMakeCompareKey() {
	    	StringBuffer sbMakeCompareKey = new StringBuffer();
			
	    	for (String curStr : this.makeCompareKey) {
	    		if (sbMakeCompareKey.length() > 0) sbMakeCompareKey.append(" || ':' || ");
	    		sbMakeCompareKey.append(curStr);
	    	}
			String sRtnVal = sbMakeCompareKey.toString();
			if (sRtnVal.length() < this.makeCompareKey.size()) sRtnVal = "NULL";
			return sbMakeCompareKey.toString();
		}
	}
	
	public Matching getMatching(String sName) {
		Matching matching = null;
		
		for (Matching curObj : this.matchingSetup) {
			if (curObj.getType().equalsIgnoreCase(sName)) {
				matching = curObj;
				break;
			}
		}
		
		return matching;
	}
		
}

/*
 * 예시 데이타 
 */
/*
{
	"matchingSetup": [
	    {
	        "type": "A1",
	        "typeName": "A type",
			
	        "condOne": {
				"cond": [
					"dta_ty in ('11', '12')"
				],
				"makeCompareKey": [
					"cmpnsp_ky"
				]		
	        },
	        "condTwo": {
				"cond": [
					"dta_ty in ('21', '22')"
				],
				"makeCompareKey": [
					"cmpnsp_ky"
				]
	        },
	        "uniqueKey": [
	            "sys_se",
	            "accnut_ym",
	            "sn"
	        ],
			"compareKey": "compare_ky",
			"amtField": "delng_amt",		
			"compareField": [
				"accnut_ym",
				"compare_ky"
			]		
	    },
	]
}
*/