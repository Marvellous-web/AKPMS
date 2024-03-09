/**
 * Time converter
 */

package argus.utility;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;

public class TimeConverter {

	public static String ipAdd;

	/**
	 * Method to get the time on particular IP by passing the server time.
	 * @param time
	 * @return date
	 */
	public static Date convertTime(Date time) {

		Date date = time;

		try {
			URL urlLongLat = new URL("https://ipinfo.io/" + ipAdd + "/loc");
			String longLat = IOUtils.toString(urlLongLat.openConnection()
					.getInputStream(), StandardCharsets.UTF_8.name());

			String string = "https://maps.googleapis.com/maps/api/timezone/json?location="
					+ Double.parseDouble(longLat.split(",")[0])
					+ ","
					+ Double.parseDouble(longLat.split(",")[1])
					+ "&timestamp="+ Calendar.getInstance().getTimeInMillis() +"&key=AIzaSyC5TVl5FGLsfvjWxciGMbuhfqrM9nN7wdQ";
			

			URL url = new URL(string);
			String response = IOUtils.toString(url.openConnection()
					.getInputStream(), StandardCharsets.UTF_8.name());

			String zoneTo = response.substring(response.indexOf("timeZoneId"));
			if (zoneTo != null && zoneTo.length() > 0) {
				zoneTo = zoneTo.substring(zoneTo.indexOf('"') + 5);
				zoneTo = zoneTo.substring(0, zoneTo.indexOf('"'));
			}

			date = changeTime(time, "IST", zoneTo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return date;
	}
	
	/**
	 * Method to get the converted date
	 * @param date
	 * @param zoneIdFrom
	 * @param zoneIdTo
	 * @return date
	 */
	private static Date changeTime(Date date, String zoneIdFrom, String zoneIdTo) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		TimeZone tzFrom = TimeZone.getTimeZone(zoneIdFrom);
		TimeZone tzTo = TimeZone.getTimeZone(zoneIdTo);
		long timeDifference = tzFrom.getRawOffset() - tzTo.getRawOffset()
				+ tzFrom.getDSTSavings() - tzTo.getDSTSavings();
		Long timestamp = cl.getTimeInMillis() - timeDifference;
		cl.setTimeInMillis(timestamp);
		return cl.getTime();
	}

}
