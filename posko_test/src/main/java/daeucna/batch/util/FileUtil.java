package daeucna.batch.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileUtil {

	public static StringBuffer readFileToString(String resourceName) {
        StringBuffer sb = new StringBuffer();
        try {
			ClassPathResource resource = new ClassPathResource(resourceName);
	        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
	
	        // br.readLine() 이 null 인지 검사할 때 한번 사용되므로 String 에 먼저 저장해둬야한다.
	        String s = "";
            while((s = br.readLine()) != null){
            	sb.append(s);
            }		
        } catch (Exception e) {
        	sb.append(e.getStackTrace());
        }
        return sb;
	}

	public static JsonObject strToJsonObj(String jsonString) {
		JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		return jsonObject;
	}
	
	public static Object strToObj(String jsonString, Class<?> anyClass) {
		Gson gson = new Gson();
		Object convertedObject = gson.fromJson(jsonString, anyClass);
		return convertedObject;
	}

	public static String objToStr(Object obj) {
		Gson gson = new Gson();
		String stringObject = gson.toJson(obj);
		return stringObject;
	}
	
}
