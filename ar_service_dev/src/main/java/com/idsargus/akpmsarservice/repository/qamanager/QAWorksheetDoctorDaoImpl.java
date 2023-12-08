package com.idsargus.akpmsarservice.repository.qamanager;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.domain.QAWorksheetDoctor;
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
public class QAWorksheetDoctorDaoImpl implements QAWorksheetDoctorDao {



	private static final int ZERO = 0;

	private static final String DELETE_QAWORKSHEET_DOCTOR = "DELETE FROM QAWorksheetDoctor doctor WHERE doctor.id = :id";

	private static final String DELETE_QAWORKSHEET_DOCTOR_BY_QAWORKSHEET_ID = "DELETE FROM QAWorksheetDoctor WHERE qaWorksheet.id = :id";

	@Autowired
	private EntityManager em;

	@Override
	public QAWorksheetDoctor findById(Long id) throws ArgusException {
		return em.find(QAWorksheetDoctor.class, id);
	}

	@Override
	public List<QAWorksheetDoctor> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependencies)
			throws ArgusException {
		List<QAWorksheetDoctor> qaworksheetList = null;

		StringBuilder queryString = new StringBuilder();

		queryString
				.append("SELECT qaworksheetDoctor FROM QAWorksheetDoctor AS qaworksheetDoctor WHERE 1=1 ");

		queryString.append(getQueryFindAll(orderClauses, whereClauses)
				.toString());

		TypedQuery<QAWorksheetDoctor> query = em.createQuery(
				queryString.toString(), QAWorksheetDoctor.class);
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
			Iterator<QAWorksheetDoctor> departmentIterator = qaworksheetList
					.iterator();

			while (departmentIterator.hasNext()) {
				QAWorksheetDoctor qaworksheetDoctor = departmentIterator.next();
				Hibernate.initialize(qaworksheetDoctor.getDoctor());

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

					queryString.append(" AND qaworksheetDoctor.name LIKE '%"
							+ whereClauses.get(Constants.KEYWORD) + "%' ");
				} else if (field.equalsIgnoreCase(Constants.QAWORKSHEET_ID)) {
					queryString.append(" AND qaworksheetDoctor." + field);
					queryString.append("="
							+ whereClauses.get(Constants.QAWORKSHEET_ID));
				} else if (field.equalsIgnoreCase(Constants.DOCTOR)) {
					queryString.append(" AND qaworksheetDoctor." + field);
					queryString
							.append("=" + whereClauses.get(Constants.DOCTOR));
				} else {
					queryString.append(" AND qaworksheetDoctor." + field);
					queryString.append(" LIKE '%" + whereClauses.get(field)
							+ "%' ");
				}
			}
		}

		return queryString;
	}

	@Override
	public void save(QAWorksheetDoctor qaworksheetDoctor) throws ArgusException {
		em.persist(qaworksheetDoctor);
	}

	@Override
	public void update(QAWorksheetDoctor qaworksheetDoctor)
			throws ArgusException {
		em.merge(qaworksheetDoctor);
	}

	@Override
	public int delete(Long id) throws ArgusException {
		return em.createQuery(DELETE_QAWORKSHEET_DOCTOR)
				.setParameter(Constants.ID, id).executeUpdate();
	}

	@Override
	public int deleteQAWorksheetDoctorByQAWorsheetId(Long qaworksheetId)
			throws ArgusException {
		return em.createQuery(DELETE_QAWORKSHEET_DOCTOR_BY_QAWORKSHEET_ID)
				.setParameter(Constants.ID, qaworksheetId).executeUpdate();
	}

	@Override
	public int totalRecord(Map<String, String> conditionMap)
			throws ArgusException {
		int totalRecords = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT COUNT(*) FROM QAWorksheetDoctor AS sheet ");

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
