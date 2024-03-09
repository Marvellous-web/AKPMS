package argus.repo.emailtemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.EmailTemplate;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Repository
@Transactional
public class EmailTemplateDaoImpl implements EmailTemplateDao {
	private static final Logger LOGGER = Logger.getLogger(EmailTemplateDaoImpl.class);

	private static final String CHANGE_STATUS = "UPDATE EmailTemplate as et SET et.status = :status, et.modifiedBy = :modifiedBy, et.modifiedOn = :modifiedOn WHERE et.id= :id";

	@Autowired
	private EntityManager em;

	public EmailTemplate findById(Long id) {

		EmailTemplate emailTemplate = em.find(EmailTemplate.class, id);
		if (emailTemplate != null) {
			Hibernate.initialize(emailTemplate.getUsers());
		}
		return emailTemplate;
	}

	public EmailTemplate findByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<EmailTemplate> criteria = builder
				.createQuery(EmailTemplate.class);
		Root<EmailTemplate> emailTemplate = criteria.from(EmailTemplate.class);

		criteria.select(emailTemplate).where(
				builder.equal(emailTemplate.get("name"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	private static StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT et FROM EmailTemplate AS et ");
		queryString.append(" WHERE et.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND et.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else {
					queryString.append(" AND et." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		if (orderClauses != null) {
			if (orderClauses.get("orderBy") != null) {
				queryString.append(" ORDER BY et."
						+ orderClauses.get("orderBy"));
			} else {
				queryString.append(" ORDER BY et.name");
			}
			if (orderClauses.get("sortBy") != null) {
				queryString.append(" " + orderClauses.get("sortBy"));
			} else {
				queryString.append(" ASC");
			}
		}
		return queryString;
	}

	public List<EmailTemplate> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {
		List<EmailTemplate> ret = null;

		StringBuilder queryString = getQueryFindAll(orderClauses, whereClauses);

		try {
			TypedQuery<EmailTemplate> query = em.createQuery(
					queryString.toString(), EmailTemplate.class);
			if (orderClauses != null) {
				if (orderClauses.get("offset") != null
						&& orderClauses.get("limit") != null) {
					query.setFirstResult(Integer.parseInt(orderClauses
							.get("offset")));
					query.setMaxResults(Integer.parseInt(orderClauses
							.get("limit")));
				}
			}

			LOGGER.info("SQL:: " + queryString.toString());

			ret = query.getResultList();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return ret;
	}

	public void addEmailTemplate(EmailTemplate emailTemplate) {
		em.persist(emailTemplate);
		return;
	}

	public void updateEmailTemplate(EmailTemplate emailTemplate) {
		em.merge(emailTemplate);
		return;
	}

	public int totalRecord(Map<String, String> whereClauses) {
		int ret = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM EmailTemplate AS et ");
		queryString.append(" WHERE et.deleted = 0 ");

		if (whereClauses != null && whereClauses.size() > 0) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {
					LOGGER.info("found search text value  = "
							+ whereClauses.get(field));
					queryString.append(" AND et.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else {
					queryString.append(" AND et." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}
		try {
			TypedQuery<Long> query = em.createQuery(queryString.toString(),
					Long.class);
			ret = query.getSingleResult().intValue();
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		LOGGER.info("count = " + ret);
		return ret;
	}

	@Override
	public int changeStatus(long id, boolean status) throws Exception {
		Query query = em.createQuery(CHANGE_STATUS);

		query.setParameter("status", status);
		query.setParameter("modifiedBy", AkpmsUtil.getLoggedInUser());
		query.setParameter("modifiedOn", new Date());
		query.setParameter("id", id);

		LOGGER.info("SQL:" + query.toString());
		// return updatedRowCount
		return query.executeUpdate();
	}

	@Override
	public List<EmailTemplate> findEmailTemplatesForSubscription() {
		List<EmailTemplate> ret = null;

		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT et FROM EmailTemplate AS et ");
		queryString
				.append(" WHERE et.deleted = 0 AND et.status =1 AND et.subscriptionEmail = 1");

		try {
			TypedQuery<EmailTemplate> query = em.createQuery(
					queryString.toString(), EmailTemplate.class);

			LOGGER.info("SQL:: " + queryString.toString());

			ret = query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Exception = ", e);
		}

		return ret;
	}
}
