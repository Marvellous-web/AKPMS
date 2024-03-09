package argus.mvc.notification;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import argus.domain.Notifications;
import argus.repo.notification.NotificationDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Controller
@RequestMapping(value = "/notifications")
public class NotificationController {

	private static final Log LOGGER = LogFactory
			.getLog(NotificationController.class);

	@Autowired
	private NotificationDao notificationDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getNotifications(Model model) {
		return "notifications";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public @ResponseBody
	List<Notifications> getNotificationsJson(@RequestParam(value="recordCount") int recordCount) {
		LOGGER.info("in getNotificationsJson");
		List<Notifications> notificationList = null;
		try {
			Long loggedInUserId = AkpmsUtil.getLoggedInUser().getId();
			LOGGER.info("record count" + recordCount);
			if(recordCount == -1){
				notificationList = notificationDao
						.getAllNotifications(loggedInUserId,recordCount);
			}else{
				notificationList = notificationDao
					.getAllNotifications(loggedInUserId, Constants.DASHBOARD_NOTIFICATION_COUNT);
			}
			if(null != notificationList){
				for (Notifications notifications : notificationList) {
					notifications.setContent(AkpmsUtil.removeHTML(notifications.getContent()));
				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		LOGGER.info("out getNotificationsJson");
		return notificationList;
	}

	@RequestMapping(value = "/json/hide", method = RequestMethod.GET)
	@ResponseBody
	public boolean hideNotificationsJson(WebRequest request){
		try {
			Long loggedInUserId = AkpmsUtil.getLoggedInUser().getId();

			return notificationDao.hideNotification(loggedInUserId, new Long(request
					.getParameter("notificationId")));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return false;
		}
	}
}
