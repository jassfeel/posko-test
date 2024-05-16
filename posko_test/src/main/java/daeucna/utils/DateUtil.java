package daeucna.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	public static java.util.Date formatValidDate(String date, String format, Locale lcal) throws Exception {

		if (date == null || format == null)
			return null;
		if (lcal == null)
			lcal = java.util.Locale.KOREA; // 디폴트는 Korea

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
		java.util.Date formattedDate = null;

		formattedDate = formatter.parse(date);

		return formattedDate;
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 날짜에 addDay 만큼 더한 날짜(yyyyMMdd)를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * addDays("20040225", 1) ===> 20040226
	 *
	 * </pre>
	 *
	 * @param date
	 * @param addDay
	 * @return String
	 * @throws ChainedException
	 */
	public static String addDays(String date, int addDay) throws Exception {
		return addDays(date, "yyyyMMdd", addDay, null);
	}

	/**
	 *
	 * 입력한 날짜를 입력한 Format으로 해석하여 addDay 만큼 더한 날짜를 Return한다.
	 *
	 * @param date
	 * @param addDay 더할 일수
	 * @param format
	 * @return String
	 * @throws ChainedException
	 */
	public static String addDays(String date, String format, int addDay, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA; // 디폴트는 Korea

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
		java.util.Date formattedDate = formatValidDate(date, format, lcal);
		formattedDate.setTime(formattedDate.getTime() + ((long) addDay * 1000 * 60 * 60 * 24));
		return formatter.format(formattedDate);
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 날짜에 addMonth 만큼 더한 날짜를 Return한다.
	 *
	 * @param date
	 * @param addMonth 더할 월수
	 * @return String
	 * @throws ChainedException
	 */
	public static String addMonths(String date, int addMonth) throws Exception {
		return addMonths(date, "yyyyMMdd", addMonth, null);
	}

	/**
	 *
	 * 입력한 날짜를 입력한 Format으로 해석하여 addMonth 만큼 더한 날짜를 Return한다.
	 *
	 * @param date
	 * @param addMonth 더할 월수
	 * @param format
	 * @return String
	 * @throws ChainedException
	 */
	public static String addMonths(String date, String format, int addMonth, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA; // 디폴트는 Korea
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
		java.util.Date formattedDate = formatValidDate(date, format, lcal);

		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", lcal);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", lcal);
		java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", lcal);

		int year = Integer.parseInt(yearFormat.format(formattedDate));
		int month = Integer.parseInt(monthFormat.format(formattedDate));
		int day = Integer.parseInt(dayFormat.format(formattedDate));
		month += addMonth;
		if (addMonth > 0) {
			while (month > 12) {
				month -= 12;
				year += 1;
			}
		} else {
			while (month <= 0) {
				month += 12;
				year -= 1;
			}
		}
		java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
		String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
				+ String.valueOf(twoDf.format(day));
		java.util.Date targetDate = null;
		try {
			targetDate = formatValidDate(tempDate, "yyyyMMdd", lcal);
		} catch (Exception e) {
			day = getLastDay(year, month);
			tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
					+ String.valueOf(twoDf.format(day));
			targetDate = formatValidDate(tempDate, "yyyyMMdd", lcal);
		}
		return formatter.format(targetDate);
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 날짜에 addYear 만큼 더한 날짜를 Return한다.
	 *
	 * @param date
	 * @param addYear 더할 년수
	 * @return String
	 * @throws ChainedException
	 */
	public static String addYears(String date, int addYear) throws Exception {
		return addYears(date, addYear, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력한 날짜를 입력한 Format으로 해석하여 addYear 만큼 더한 날짜를 Return한다.
	 *
	 * @param date
	 * @param addYear 더할 년수
	 * @param format
	 * @return String
	 * @throws ChainedException
	 */
	public static String addYears(String date, int addYear, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA; // 디폴트는 Korea

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
		java.util.Date formattedDate = formatValidDate(date, format, lcal);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(formattedDate);
		calendar.add(Calendar.YEAR, addYear);
		formattedDate = calendar.getTime();

		return formatter.format(formattedDate);

	}

	/**
	 *
	 * 현재 시각을 HHmmss Format 의 String 으로 Return한다.
	 *
	 * @return java.lang.String
	 */
	public static String getCurrentTimeString() {
		return getCurrentDateString("HHmmss", null);
	}

	/**
	 *
	 * 현재 날짜와 시각을 yyyy-MM-dd hh:mm:ss.fffffffff Format의 Timestamp 로 Return한다.
	 *
	 * @return Timestamp
	 */
	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(new GregorianCalendar().getTime().getTime());
	}

	/**
	 *
	 * 현재 날짜를 yyyyMMdd Format 의 String 으로 Return한다.
	 *
	 * @return java.lang.String
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyyMMdd", null);
	}

	/**
	 *
	 * 현재 날짜를 주어진 Format 의 String 으로 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * CDateUtil.getCurrentDateString("yyyy/MM/dd") ===> 2004/02/24
	 * CDateUtil.getCurrentDateString("HH:mm:ss")); ===> 13:40:05
	 * CDateUtil.getCurrentDateString("hh:mm:ss")); ===> 01:40:05
	 *
	 * format :  h        hour in am/pm (1~12)  
	 * format :  H        hour in day (0~23)
	 *
	 * </pre>
	 *
	 * @param format
	 * @return java.lang.String
	 */
	public static String getCurrentDateString(String format, Locale lcal) {
		if (lcal == null) lcal = java.util.Locale.KOREA;
		return ConvertUtil.formatTimestamp(getCurrentTimeStamp(), format, lcal);
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 날짜가 유효한 날짜인지 확인한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * CDateUtil.isValidDate("20050225") ===> true
	 *
	 * </pre>
	 *
	 * @param date
	 * @return boolean
	 * @throws ChainedException
	 */
	public static boolean isValidDate(String date) throws Exception {
		return DateUtil.isValidDate(date, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력된 날짜와 입력된 Format 으로 해석하여 입력된 날짜가 유효한 날짜인지 확인한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * CDateUtil.isValidDate("2004-02-99","yyyy-MM-dd") ===> false
	 *
	 * </pre>
	 *
	 * @param date
	 * @return boolean
	 * @throws ChainedException
	 */
	public static boolean isValidDate(String date, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA;
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
			java.util.Date formattedDate = null;

			try {

				formattedDate = formatter.parse(date);

			} catch (java.text.ParseException e) {
				return false;
			}

			if (!formatter.format(formattedDate).equals(date))
				return false;

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 from 날짜와 to 날짜 사이의 일수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * daysBetween("20040225", "20040301") ===> 5
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @return int
	 * @throws ChainedException
	 */
	public static int getDaysBetween(String from, String to) throws Exception {
		return getDaysBetween(from, to, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력된 from 날짜와 to 날짜를 입력된 Format으로 해석하여 날짜 사이의 일수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * daysBetween("2004-02-25", "2004-03-01", "yyyy-MM-dd") ===> 5
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @param format
	 * @return int
	 * @throws ChainedException
	 */
	public static int getDaysBetween(String from, String to, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA;
		java.util.Date d1 = formatValidDate(from, format, lcal);
		java.util.Date d2 = formatValidDate(to, format, lcal);
		long duration = d2.getTime() - d1.getTime();
		return (int) (duration / (1000 * 60 * 60 * 24));
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 from 날짜와 to 날짜 사이의 월수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * monthsBetween("20040225", "20041001") ===> 8
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @return int
	 * @throws ChainedException
	 */
	public static int getMonthsBetween(String from, String to) throws Exception {
		return getMonthsBetween(from, to, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력된 from 날짜와 to 날짜를 입력된 Format으로 해석하여 날짜 사이의 월수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * monthsBetween("2004-02-25", "2004-10-01", "yyyy-MM-dd") ===> 8
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @param format
	 * @return int
	 * @throws ChainedException
	 */
	public static int getMonthsBetween(String from, String to, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA;
		java.util.Date fromDate = formatValidDate(from, format, lcal);
		java.util.Date toDate = formatValidDate(to, format, lcal);
		// if two date are same, return 0.
		if (fromDate.compareTo(toDate) == 0)
			return 0;
		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", lcal);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", lcal);
		java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", lcal);
		int fromYear = Integer.parseInt(yearFormat.format(fromDate));
		int toYear = Integer.parseInt(yearFormat.format(toDate));
		int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
		int toMonth = Integer.parseInt(monthFormat.format(toDate));
		int fromDay = Integer.parseInt(dayFormat.format(fromDate));
		int toDay = Integer.parseInt(dayFormat.format(toDate));
		int result = 0;
		result += ((toYear - fromYear) * 12);
		result += (toMonth - fromMonth);
		// if (((toDay - fromDay) < 0) ) result += fromDate.compareTo(toDate);
		// ceil과 floor의 효과
		if (((toDay - fromDay) > 0))
			result += toDate.compareTo(fromDate);
		return result;
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 from 날짜와 to 날짜 사이의 년수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 * yearsBetween("20040225", "20071001") ===> 3
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @return int
	 * @throws ChainedException
	 */
	public static int getYearsBetween(String from, String to) throws Exception {
		return getYearsBetween(from, to, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력된 from 날짜와 to 날짜를 입력된 Format으로 해석하여 날짜 사이의 년수를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * yearsBetween("2004-02-25", "2007-10-01", "yyyy-MM-dd") ===> 3
	 *
	 * </pre>
	 *
	 * @param from
	 * @param to
	 * @param format
	 * @return int
	 * @throws ChainedException
	 */
	public static int getYearsBetween(String from, String to, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA;
		return (int) (getDaysBetween(from, to, format, lcal) / 365);
	}

	/**
	 *
	 * yyyyMMdd Format으로 입력된 날짜에서 해당 년월의 마지막 날짜를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * lastDayOfMonth("20040202") ===> 20040229
	 *
	 * </pre>
	 *
	 * @param date
	 * @return String
	 * @throws ChainedException
	 */
	public static String getLastDayOfMonth(String date) throws Exception {
		return getLastDayOfMonth(date, "yyyyMMdd", null);
	}

	/**
	 *
	 * 입력된 날짜을 입력된 Format 으로 해석하여 그 날짜에서 해당 년월의 마지막 날짜를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * lastDayOfMonth("20040202", "yyyyMMdd") ===> 20040429
	 *
	 * </pre>
	 *
	 * @param date
	 * @param format
	 * @return String
	 * @throws ChainedException
	 */
	public static String getLastDayOfMonth(String date, String format, Locale lcal) throws Exception {
		if (lcal == null)
			lcal = java.util.Locale.KOREA;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, lcal);
		java.util.Date formattedDate = formatValidDate(date, format, lcal);
		java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", lcal);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", lcal);
		int year = Integer.parseInt(yearFormat.format(formattedDate));
		int month = Integer.parseInt(monthFormat.format(formattedDate));
		int day = getLastDay(year, month);
		java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
		String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month))
				+ String.valueOf(twoDf.format(day));
		java.util.Date targetDate = formatValidDate(tempDate, "yyyyMMdd", lcal);
		return formatter.format(targetDate);
	}

	/**
	 *
	 * 입력된 년도와 입력된 월의 마지막 일를 Return한다.
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * lastDay(2004,02) ===> 29
	 *
	 * </pre>
	 *
	 * @param year
	 * @param month
	 * @return int
	 * @throws ParseException
	 */
	public static int getLastDay(int year, int month) throws java.text.ParseException {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			if ((year % 4) == 0) {
				if ((year % 100) == 0 && (year % 400) != 0) {
					day = 28;
				} else {
					day = 29;
				}
			} else {
				day = 28;
			}
			break;
		default:
			day = 30;
		}
		return day;
	}

	/**
	 *
	 * 현재 시간을 Long Type 으로 Return한다. (워크플로우 작업이력저장 용도)
	 *
	 * <pre>
	 *
	 * [사용 예제]
	 *
	 * DateUtil.getCurrentTime()
	 *
	 * </pre>
	 * 
	 * @return Long
	 * @throws Exception
	 */
	public static Long getCurrentTime() throws Exception {
		java.util.Date date = new java.util.Date();

		long ltime1 = date.getTime(); // 현재시간

		Calendar cal = Calendar.getInstance();
		cal.set(1970, 0, 1, 9, 0, 0);// 파일넷 기준시간

		long ltime2 = cal.getTimeInMillis();

		long curTime = ltime1 - ltime2;

		return new Long(curTime / 1000);
	}

}