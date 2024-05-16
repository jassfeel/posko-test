package daeucna.utils;

import daeucna.config.security.utils.CommonJson;

public class JsonUtil {

	public static String objectToString(Object object) {
		return CommonJson.objectToString(object);
	}

	public static Object stringToObject(String sJson, Class<?> objClass) {
		return CommonJson.stringToObject(sJson, objClass);
	}

	public static Object objectToObject(Object object, Class<?> objClass) {
		return CommonJson.objectToObject(object, objClass);
	}

}