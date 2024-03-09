package argus.repo.notification;

import java.util.List;

import argus.domain.Notifications;
import argus.exception.ArgusException;

public interface NotificationDao {

	List<Notifications> getAllNotifications(long loggedInUserId, int maxLimit)
 throws ArgusException;

	boolean hideNotification(long loggedInUserId, long notifictionId)
			throws ArgusException;
}
