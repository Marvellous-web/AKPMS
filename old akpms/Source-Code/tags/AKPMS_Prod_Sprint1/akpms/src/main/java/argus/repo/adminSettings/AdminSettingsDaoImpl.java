package argus.repo.adminSettings;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.AdminSettings;
import argus.exception.ArgusException;

@Repository
@Transactional
public class AdminSettingsDaoImpl implements AdminSettingsDao {

	@Autowired
	private EntityManager em;

	@Override

	/**
	 *  This method fetches the current admin settings
	 */
	public AdminSettings getAdminSettings() throws ArgusException {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AdminSettings> criteria = cb.createQuery(AdminSettings.class);
		Root<AdminSettings> user = criteria.from(AdminSettings.class);
		criteria.select(user);
		return em.createQuery(criteria).getSingleResult();
	}

	/**
	 *  This method updates the current admin settings
	 */
	public void updateAdminSettings(AdminSettings adminSetting)
			throws ArgusException
	{
		em.merge(adminSetting);
	}
}
