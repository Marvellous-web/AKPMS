package com.idsargus.akpmsarservice.repository.qamanager;

import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheetStaff;
import com.idsargus.akpmsarservice.util.Constants;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@Transactional
public class QAWorksheetStaffDaoImpl implements QAWorksheetStaffDao {



	private static final int ZERO = 0;

	private static final String DELETE_QAWORKSHEET_STAFF = "DELETE FROM QAWorksheetStaff staff WHERE staff.id = :id";
	
	private static final String DELETE_QAWORKSHEET_STAFF_BY_QAWORKSHEET_ID = "DELETE FROM QAWorksheetStaff WHERE qaWorksheet.id = :id";

	@Autowired
	private EntityManager em;

	@Override
	public QAWorksheetStaff findById(Long id) throws ArgusException {
		return em.find(QAWorksheetStaff.class, id);
	}

	@Override
	public List<QAWorksheetStaff> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependencies)
			throws ArgusException {
		List<QAWorksheetStaff> qaworksheetList = null;

		StringBuilder queryString = new StringBuilder();

		queryString
				.append("SELECT qaworksheetStaff FROM QAWorksheetStaff AS qaworksheetStaff WHERE 1=1 ");

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		TypedQuery<QAWorksheetStaff> query = em.createQuery(
				queryString.toString(), QAWorksheetStaff.class);
		if (orderClauses != null && orderClauses.get(Constants.OFFSET) != null
				&& orderClauses.get(Constants.LIMIT) != null) {
			query.setFirstResult(Integer.parseInt(orderClauses
					.get(Constants.OFFSET)));
			query.setMaxResults(Integer.parseInt(orderClauses
					.get(Constants.LIMIT)));
		}


		qaworksheetList = query.getResultList();

		if (dependencies && qaworksheetList != null
				&& qaworksheetList.size() > ZERO) {
			Iterator<QAWorksheetStaff> departmentIterator = qaworksheetList
					.iterator();

			while (departmentIterator.hasNext()) {
				QAWorksheetStaff qaworksheetStaff = departmentIterator.next();
				Hibernate.initialize(qaworksheetStaff.getUser());

			}
		}

		return qaworksheetList;
	}

	private StringBuilder getQueryFindAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) {

		StringBuilder queryString = new StringBuilder();

		if (whereClauses != null && whereClauses.size() > ZERO) {
			Set<String> key = whereClauses.keySet();
			for (String field : key) {
				if (field.equalsIgnoreCase(Constants.KEYWORD)) {

					queryString.append(" AND qaworksheetStaff.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.QAWORKSHEET_ID)) {
					queryString.append(" AND qaworksheetStaff." + field);
					queryString.append("="
							+ whereClauses.get(Constants.QAWORKSHEET_ID));
				} else if (field.equalsIgnoreCase(Constants.USER)) {
					queryString.append(" AND qaworksheetStaff." + field);
					queryString.append("=" + whereClauses.get(Constants.USER));
				} else {
					queryString.append(" AND qaworksheetStaff." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		return queryString;
	}

	@Override
	public void save(QAWorksheetStaff qaworksheetStaff) throws ArgusException {
		em.persist(qaworksheetStaff);
	}

	@Override
	public void update(QAWorksheetStaff qaworksheetStaff) throws ArgusException {
		em.merge(qaworksheetStaff);
	}

	@Override
	public int delete(Long id) throws ArgusException {
		return em.createQuery(DELETE_QAWORKSHEET_STAFF)
				.setParameter(Constants.ID, id).executeUpdate();
	}
	
	@Override
	public int deleteQAWorksheetStaffByQAWorsheetId(Long qaworksheetId) throws ArgusException {
		return em.createQuery(DELETE_QAWORKSHEET_STAFF_BY_QAWORKSHEET_ID)
				.setParameter(Constants.ID, qaworksheetId).executeUpdate();
	}
	
	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM QAWorksheetStaff AS sheet ");

		if (conditionMap != null && conditionMap.size() > ZERO) {
			Set<String> key = conditionMap.keySet();
			for (String field : key) {
				queryString.append(" AND sheet." + field);
				queryString
						.append(" LIKE '%" + conditionMap.get(field) + "%' ");
			}
		}
		TypedQuery<Long> query = em.createQuery(queryString.toString(),
				Long.class);
		totalRecords = query.getSingleResult().intValue();


		return totalRecords;
	}

}
