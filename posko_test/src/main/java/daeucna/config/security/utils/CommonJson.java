package daeucna.config.security.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonJson {

    public static String objectToString(Object object) {  	
    	String rtnVal = "";
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
        	rtnVal = objectMapper.writeValueAsString(object);
    	} catch (Exception e) {
    		e.printStackTrace();
    		rtnVal = "Error";
    	}
        return rtnVal;
    }

    public static Object stringToObject(String sJson, Class<?> objClass) {  	
    	Object rtnVal;
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
        	rtnVal = objectMapper.readValue(sJson, objClass);
    	} catch (Exception e) {
    		e.printStackTrace();
    		rtnVal = "Error";
    	}
        return rtnVal;
    }

    public static Object objectToObject(Object object, Class<?> objClass) {
    	return stringToObject(objectToString(object), objClass);
    }
    
}