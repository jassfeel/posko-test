package daeucna.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * <code>StringUtil</code> 은 String의 handling과 관련된 class이다.
 * <p>
 */

public class StringUtil {
	/**
	 * lPad(inStr, iSize, sPadStr)
	 * 
	 * @param inStr
	 * @param iSize
	 * @param sPadStr
	 * @return
	 */
	public static String leftPad(String inStr, int iSize, String sPadStr) {
		return StringUtils.leftPad(inStr, iSize, sPadStr);
	}

	/**
	 * rPad(inStr, iSize, sPadStr)
	 * 
	 * @param inStr
	 * @param iSize
	 * @param sPadStr
	 * @return
	 */
	public static String rightPad(String inStr, int iSize, String sPadStr) {
		return StringUtils.rightPad(inStr, iSize, sPadStr);
	}

	/**
	 * inStr이 null/""/"공백문자" 이면 default
	 * 
	 * @param inStr
	 * @param sDefault
	 * @return
	 */
	public static String defaultIfBlank(String inStr, String sDefault) {
		return StringUtils.defaultIfBlank(inStr, sDefault);
	}

	/**
	 * inStr이 null/"" 이면 default
	 * 
	 * @param inStr
	 * @param sDefault
	 * @return
	 */
	public static String defaultIfEmpty(String inStr, String sDefault) {
		return StringUtils.defaultIfEmpty(inStr, sDefault);
	}

	/**
	 * Strip 결과가 Null이나 ""이면 ""
	 * 
	 * @param inStr
	 * @param sDefault
	 * @return
	 */
	public static String stripToEmpty(String inStr) {
		return StringUtils.stripToEmpty(inStr);
	}

	/**
	 * Strip 결과가 "" 이면 NULL
	 * 
	 * @param inStr
	 * @param sDefault
	 * @return
	 */
	public static String stripToNull(String inStr) {
		return StringUtils.stripToNull(inStr);
	}

	public static String remove(String inStr, String sRemoveStr) {
		return StringUtils.remove(inStr, sRemoveStr);
	}

	public static String replace(String inStr, String sTargetStr, String sReplaceStr) {
		return StringUtils.replace(sTargetStr, inStr, sReplaceStr);
	}

	public static String trimToEmpty(String inStr) {
		return StringUtils.trimToEmpty(inStr);
	}

	public static String trimToNull(String inStr) {
		return StringUtils.trimToNull(inStr);
	}

}