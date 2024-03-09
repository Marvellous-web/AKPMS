/**
 *
 */
package argus.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import argus.domain.Department;
import argus.domain.Permission;
import argus.domain.User;

/**
 * @author bhupender.s
 *
 */
public final class AkpmsUtil {

	private static final Log LOGGER = LogFactory.getLog(AkpmsUtil.class);

	private static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz";

	private AkpmsUtil() {
		// no need to create object of this class
	}

	/**
	 * function to get logged in user details
	 *
	 * @return
	 */
	public static User getLoggedInUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	public static Date akpmsNewDateFormat(String date) {
		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT,
				Locale.US);

		LOGGER.info("in akpmsDateFormat " + date);

		try {
			Date d = (Date) formatter.parse(date);

			LOGGER.info("date formatted " + d.toString());
			return d;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.toString());
		}

		LOGGER.info("out akpmsDateFormat ");
		return null;
	}



	public static Date akpmsDateFormat(String date) {
		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

		LOGGER.info("in akpmsDateFormat " + date);

		try {
			Date d = (Date) formatter.parse(date);

			LOGGER.info("date formatted " + d.toString());
			return d;
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.toString());
		}

		LOGGER.info("out akpmsDateFormat ");
		return null;
	}

	public static String akpmsDateFormat(Date date, String format) {
		LOGGER.info("in akpmsDateFormat " + date);

		DateFormat formatter = null;
		String dateAsString = null;

		if (null == format) {
			formatter = new SimpleDateFormat("MMM,dd yyyy HH:mm");
		} else {
			formatter = new SimpleDateFormat(format);
		}

		try {
			dateAsString = formatter.format(date);
			LOGGER.info("date formatted " + dateAsString);

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.toString());
		}

		LOGGER.info("out akpmsDateFormat ");
		return dateAsString;
	}

	public static String removeHTML(String htmlString) {
		// Remove HTML tag from java String
		String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

		// Remove Carriage return from java String
		noHTMLString = noHTMLString.replaceAll("\r", "<br/>");

		// Remove New line from java string and replace html break
		noHTMLString = noHTMLString.replaceAll("\n", " ");
		noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
		noHTMLString = noHTMLString.replaceAll("\"", "&quot;");

		noHTMLString = noHTMLString.replaceAll("<", "");
		noHTMLString = noHTMLString.replaceAll(">", "");
		return noHTMLString;
	}

	public static String getRandomString(int length) {
		Random rand = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int pos = rand.nextInt(CHARSET.length());
			sb.append(CHARSET.charAt(pos));
		}
		return sb.toString();
	}

	/**
	 * This method will return a list of years( from now to previous 10 years)
	 *
	 * @return List<Integer>
	 */
	public static List<Integer> getYears() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		LOGGER.info("current year  = " + currentYear);
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = Constants.ZERO; i <= Constants.TEN; i++) {
			yearList.add(currentYear - i);
		}
		return yearList;
	}

	/**
	 * This method will return a list of months( in numeric form)
	 *
	 * @return List<Integer>
	 */
	public static Map<Integer, String> getMonths() {
		Map<Integer, String> monthMap = new HashMap<Integer, String>();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		LOGGER.info("months length: "+months.length);
		for (int i = Constants.ONE; i < months.length; i++) {
			/** in Calendar class month starts from 0 up to 11 **/
			monthMap.put(i, months[i - 1]);
		}
		LOGGER.info("months map length: "+monthMap.size());

		return monthMap;
	}

	public static boolean checkDepartment(Long departmentId) {

		List<Department> departments = getLoggedInUser().getDepartments();

		for (Department department : departments) {
			if (department.getId().longValue() == departmentId.longValue()) {
				return true;
			}
		}

		return false;
	}

	public static boolean checkPermission(String permissionId) {
		List<Permission> permissions = getLoggedInUser().getPermissions();
		for (Permission permission : permissions) {
			if (permission.getId().equals(permissionId)) {
				return true;
			}
		}
		return false;
	}

	public static Date getFormattedDate(String fromDate) {
		Date date = akpmsNewDateFormat(fromDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,
				cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		date = cal.getTime();
		return date;
	}

	public static Date getFormattedDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,
				cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		date = cal.getTime();
		return date;
	}

	public static Double amountCheck(Double amount) {
		if (amount == null) {
			return 0.0;
		}
		return amount;
	}

}
