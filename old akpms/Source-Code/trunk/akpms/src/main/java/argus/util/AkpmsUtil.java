/**
 *
 */
package argus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import argus.domain.Department;
import argus.domain.Files;
import argus.domain.Permission;
import argus.domain.User;
import argus.repo.file.FileDao;

/**
 * @author bhupender.s
 *
 */

public final class AkpmsUtil {

	private static final Logger LOGGER = Logger.getLogger(AkpmsUtil.class);

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
	
	public static Date akpmsNewDateFormat(String date, String format) {
		DateFormat formatter = null; 
		if (format == null) {
			formatter = new SimpleDateFormat(Constants.DATE_FORMAT,
					Locale.US);
		} else {
			formatter = new SimpleDateFormat(format,
					Locale.US);
		}
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
	
	public static String akpmsFormatDateSQL(String strDate) {
		DateFormat df = new SimpleDateFormat(Constants.MYSQL_DATE_FORMAT);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error(e.toString());
		}   // convert string to date
		df=new SimpleDateFormat("yyyy-MM-dd");
		// date formatted in whatever format you want. 
		return df.format(date);
	}
	
	public static String removeHTML(String htmlString) {
		// Remove HTML tag from java String
		String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

		// Remove Carriage return from java String
		noHTMLString = noHTMLString.replaceAll("\r", " ");

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
		String[] months = dfs.getShortMonths();

		LOGGER.info("months length: " + months.length);
		for (int i = Constants.ONE; i < months.length; i++) {
			/** in Calendar class month starts from 0 up to 11 **/
			monthMap.put(i, months[i - 1]);
		}
		LOGGER.info("months map length: " + monthMap.size());

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

	public static Map<String, String> getPropertyMap(String fileName)
			throws Exception {
		LOGGER.debug("in getPropertyMap " + fileName);
		Properties properties = new Properties();
		TreeMap<String, String> propMap = null;

		try {
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
			in.close();
			// propMap.putAll((Map) properties);
			propMap = new TreeMap<String, String>((Map) properties);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}

		LOGGER.debug("out getPropertyMap " + propMap.size());
		return propMap;
	}

	/**
	 *
	 * @param inputStr
	 *            = "1,4,5-10,34,2-4,7"
	 * @return comma seprated string
	 */
	/*
	 * public static String commanSepString(String inputStr) {
	 * LOGGER.debug("in commanSepString " + inputStr); HashSet<Long>
	 * ticketNumberSet = new HashSet<Long>();
	 *
	 * if (inputStr != "") { List<String> commaSepArr = Arrays.asList(inputStr
	 * .split("\\s*,\\s*"));
	 *
	 * if (commaSepArr.size() > 0) { for (int counter = 0; counter <
	 * commaSepArr.size(); counter++) { // console.log(commaSepArr[counter]);
	 * String element = commaSepArr.get(counter); if (element.indexOf("-") > -1)
	 * { List<String> hypenSepArr = Arrays.asList(element .split("\\s*-\\s*"));
	 *
	 * if (hypenSepArr.size() == 2) { if (Long.valueOf(hypenSepArr.get(1)) >
	 * Long .valueOf(hypenSepArr.get(0))) { long upperLt =
	 * Long.valueOf(hypenSepArr.get(1)); long lowerLt =
	 * Long.valueOf(hypenSepArr.get(0)); for (Long i = lowerLt; i <= upperLt;
	 * i++) { ticketNumberSet.add(i); } } else if
	 * (Long.valueOf(hypenSepArr.get(1)) < Long .valueOf(hypenSepArr.get(0))) {
	 *
	 * long upperLt = Long.valueOf(hypenSepArr.get(0)); long lowerLt =
	 * Long.valueOf(hypenSepArr.get(1));
	 *
	 * for (Long i = lowerLt; i <= upperLt; i++) { ticketNumberSet.add(i); } }
	 * else { ticketNumberSet.add(Long.valueOf(hypenSepArr .get(0))); } } } else
	 * { ticketNumberSet.add(Long.valueOf(element)); } } } }
	 *
	 * String outputString = ""; if (ticketNumberSet.size() > 0) { outputString
	 * = ticketNumberSet.toString();
	 *
	 * outputString = outputString.substring(1, outputString.length() - 1); }
	 *
	 * LOGGER.debug("out commanSepString " + outputString); return outputString;
	 * }
	 */

	/*
	 * public static Double formatDoubleNumber(Double input) { // throws //
	 * NumberFormatException // {
	 *
	 * DecimalFormat decimalFormat = new DecimalFormat("#.00");
	 *
	 * Double output = 0D;
	 *
	 * try { output = Double.valueOf(decimalFormat.format(input)); } catch
	 * (NumberFormatException e) { // TODO: handle exception
	 * LOGGER.info(e.getMessage(), e); }
	 *
	 * return output; }
	 */

	public static String formatDoubleNumber(Double input) { // throws
		// NumberFormatException
		// {

		DecimalFormat decimalFormat = new DecimalFormat("#.00");

		String output = "";

		try {
			output = decimalFormat.format(input);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage(), e);
		}

		return output;
	}

	public static String roundValue(double myVal) {
		double pp = 10;

		double i = (2.0 / pp);

		String format = "%10.2f";
		return String.format(format, (Math.round((myVal + i) * pp) / pp) - i)
				.trim();
	}

	public static Map<String, String> getOrderClause(WebRequest request,
			int page) {
		Map<String, String> orderClauses = new HashMap<String, String>();

		int rp = 0;

		if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
			rp = Integer.parseInt(request
					.getParameter(Constants.RECORD_PRE_PAGE));
			try {
				orderClauses.put(Constants.LIMIT, "" + rp);
			} catch (Exception e) {
				LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
			}
		}

		if (request.getParameter(Constants.PAGE) != null) {
			try {
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
				orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
			} catch (Exception e) {
				LOGGER.debug("Exception during parsing");
			}
		}

		if (request.getParameter(Constants.SORT_ORDER) != null) {
			orderClauses.put(Constants.SORT_BY,
					request.getParameter(Constants.SORT_ORDER));
		}

		if (request.getParameter(Constants.SORT_NAME) != null) {
			orderClauses.put(Constants.ORDER_BY,
					request.getParameter(Constants.SORT_NAME));
		}

		return orderClauses;
	}

	/**
	 * function for date filter in lists (PBS List, CBS List and producitivity
	 * lists)
	 *
	 * @param days
	 * @return
	 */
	public static Date getDateBeforeNDays(int days) {
		long sevenDayBeforNowMillis = 60 * 60 * 24 * 1000 * days;
		long currentDateMaxTimeMillis = AkpmsUtil.getFormattedDate(new Date()).getTime();
		Date dateSevenDaysBefore =new Date();
		dateSevenDaysBefore.setTime(currentDateMaxTimeMillis- sevenDayBeforNowMillis);
		return dateSevenDaysBefore;
	}

	public static void fileUpload(Files attachedFile,FileDao fileDoa,MessageSource messageSource) {

		try {
//			Files attachedFile = attachedFile;
			if (attachedFile.getAttachedFile().getSize() != 0) {

				MultipartFile file = attachedFile.getAttachedFile();
				if (null != file && file.getSize() > 0) {

					byte[] fileData = file.getBytes();
					String originalFileName = file.getOriginalFilename();
					Long timeMili = System.currentTimeMillis();

					StringBuilder systemName = new StringBuilder();
					if (attachedFile.getProcessManual() != null) {

						systemName.append(attachedFile.getProcessManual().getId());
						systemName.append("_");
						if (attachedFile.getSubProcessManualId() != null) {
							systemName.append(attachedFile.getSubProcessManualId());
						} else {
							systemName.append("0");
						}
						systemName.append("_");
					} else {
						LOGGER.info(" Process Manual not found in files object");
					}

					systemName.append(timeMili);
					systemName.append("_");
					systemName.append(originalFileName);

					String realPath = messageSource.getMessage(
							"attachments.storage.space.arProductivity", null,
							Locale.ENGLISH).trim();

					LOGGER.info("real Path = " + realPath);

					File dir = new File(realPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File fileNamePath = new File(dir, systemName.toString());
					OutputStream outputStream = new FileOutputStream(
							fileNamePath);
					outputStream.write(fileData);
					outputStream.close();

					attachedFile.setName(originalFileName);
					attachedFile.setSystemName(systemName.toString());
					if(attachedFile.getId()!=null){
						fileDoa.deleteAttachedFile(attachedFile.getId());
						attachedFile.setId(null);
					}
					/*if (paymentPostingWorkFlow.getId() != null) {
						paymentPostingWorkflowDao.updateAttachement(attachedFile);
					} else {
						paymentPostingWorkflowDao.saveAttachement(attachedFile);
					}*/
					// set message here
				} else {
					LOGGER.info("there is no attachment coming in file object");
				}
			} else {
				LOGGER.info("Attached file Object is coming null");
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION,e);
		}
	}

}
