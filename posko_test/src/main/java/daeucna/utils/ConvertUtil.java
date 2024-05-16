package daeucna.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConvertUtil {

	/**
	 *
	 * Timestamp 형식의 날짜를 입력한 Format 과 Locale 에 따라 Formatting 한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * formatTimestamp("2004-02-25 15:45:31.156","yyyy년MM월dd일",Locale) ===> 2004년02월25일
	 *
	 * </pre>
	 *
	 * @param timestamp Timestamp 형식의 날짜
	 * @param format    SimpleDateFormat 에 적용할 format
	 * @param locale    국가별 LOCALE
	 * @return java.lang.String
	 */
	public static String formatTimestamp(Timestamp timestamp, String format, Locale locale) {

		SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
		return formatter.format(timestamp);

	}

}