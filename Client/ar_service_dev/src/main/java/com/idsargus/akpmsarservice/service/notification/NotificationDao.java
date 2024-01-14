package com.idsargus.akpmsarservice.service.notification;



import com.idsargus.akpmsarservice.dto.Notifications;
import com.idsargus.akpmsarservice.exception.ArgusException;

import java.util.List;

public interface NotificationDao {

	List<Notifications> getAllNotifications(long loggedInUserId, int maxLimit)
 throws ArgusException;

	boolean hideNotification(long loggedInUserId, long notifictionId)
			throws ArgusException;
}
