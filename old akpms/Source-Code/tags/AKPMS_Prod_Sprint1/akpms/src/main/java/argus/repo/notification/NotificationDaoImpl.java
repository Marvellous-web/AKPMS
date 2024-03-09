package argus.repo.notification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Notifications;
import argus.exception.ArgusException;

@Repository
@Transactional
public class NotificationDaoImpl implements NotificationDao {

	private static final Log LOGGER = LogFactory
			.getLog(NotificationDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public List<Notifications> getAllNotifications(long loggedInUserId,int maxLimit)
 throws ArgusException {
		LOGGER.info("IN getAllNotifications method");

		List<Notifications> notificationsList = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT notifications.id, notifications.content, notifications.type FROM notifications ");
		sb.append(" WHERE notifications.id NOT IN (SELECT user_notification_rel.notification_id FROM user_notification_rel WHERE user_notification_rel.user_id = "+loggedInUserId+" )");
		sb.append(" ORDER BY created_on DESC");

		if(maxLimit > 0){
			sb.append(" LIMIT 0," + maxLimit);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> objList = em.createNativeQuery(sb.toString()).getResultList();

		if (objList != null && !objList.isEmpty()) {
			notificationsList = new ArrayList<Notifications>();
			for (Object[] obj : objList) {
				Notifications notification = new Notifications();
				if (obj[0] instanceof java.math.BigInteger) {
					notification.setId(((java.math.BigInteger) obj[0])
							.longValue());
				} else {
					notification.setId((Long) obj[0]);
				}
				notification.setContent((String) obj[1]);
				notification.setType((String) obj[2]);
				notificationsList.add(notification);
			}
		}

		return notificationsList;
	}

	@Override
	public boolean hideNotification(long loggedInUserId, long notifictionId)
			throws ArgusException {
		LOGGER.info("IN hideNotification method");
		boolean isHide = false;
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO user_notification_rel(notification_id, user_id) VALUES(:notification_id, :user_id)");
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("user_id", loggedInUserId);
		query.setParameter("notification_id", notifictionId);

		LOGGER.info("OUT hideNotification method");

		if(query.executeUpdate() >0){
			isHide = true;
		}
		return isHide;
	}

}
