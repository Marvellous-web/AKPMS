package com.idsargus.akpmsarservice.controller.notification;



import com.idsargus.akpmsarservice.dto.Notifications;
import com.idsargus.akpmsarservice.service.notification.NotificationDao;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/notifications")
public class NotificationController {

	private static final Logger LOGGER = LogManager.getLogger(NotificationController.class);


	@Autowired
	private NotificationDao notificationDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getNotifications(Model model) {
		return "notifications";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<?> getNotificationsJson(@RequestParam(value="recordCount") int recordCount,
											 @RequestParam(value = "loggedInUserId") Long loggedInUserId) {
		LOGGER.info("in getNotificationsJson");
		List<Notifications> notificationList = new ArrayList<>();
		try {

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
		if(notificationList.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(notificationList);
		}
		return ResponseEntity.status(HttpStatus.OK).body("No Notification found");
		//return notificationList;
	}

	@GetMapping(value = "/json/hide")
	public ResponseEntity<?> hideNotificationsJson(@RequestParam(value = "loggedInUserId") Long loggedInUserId,
										 @RequestParam(value = "notificationId") Long notificationId){
		try {
			boolean isHidden =  notificationDao.hideNotification(loggedInUserId, notificationId);
			if(isHidden) {
				return ResponseEntity.status(HttpStatus.OK).body(true);
			} else{
				return ResponseEntity.status(HttpStatus.OK).body("Notification is already hidden");
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return ResponseEntity.status(HttpStatus.OK).body("false");
		}
	}
}
