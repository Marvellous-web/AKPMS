/**
 *
 */
package argus.repo.cashlog;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.MoneySource;
import argus.exception.ArgusException;
import argus.repo.moneysource.MoneySourceDao;

/**
 * @author bhupender.s
 *
 */
@Repository
@Transactional
public class CashlogDaoImpl implements CashlogDao {

	private static final Log LOGGER = LogFactory.getLog(CashlogDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Autowired
	private MoneySourceDao moneySourceDao;

	private static final int REVENUE_TYPE_PATIENT_COLLECTION = 1;
	private static final int PRO_HEALTH_GROUP_ID = 1;

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntryManagementFee()
	 */
	@Override
	public List<Object[]> journalEntryManagementFee(String month, String year)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rev.code as revenueCode, rev.name as revenueName, doc.doctorcode as doctorCode, doc.name as doctorName, "
				+ "sum(pmtBatch.deposit_amt+pmtBatch.ndba), round(sum((pmtBatch.deposit_amt+pmtBatch.ndba)*doc.percentage),2) as mgmtfee ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year
				+ " and (pmtBatch.revenue_type_id <>"
				+ REVENUE_TYPE_PATIENT_COLLECTION
				+ " ||pmtBatch.revenue_type_id is null)");
		sb.append(" and pmtBatch.doctor_id=" + PRO_HEALTH_GROUP_ID);
		sb.append(" group by doc.doctorcode WITH ROLLUP");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntrySummary()
	 */
	@Override
	public List<Object[]> journalEntrySummary(String month, String year)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rev.code as revenueCode, rev.name as revenueName, doc.doctorcode as doctorCode, doc.name as doctorName, "
				+ "sum(pmtBatch.deposit_amt+pmtBatch.ndba)");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year
				+ " and (pmtBatch.revenue_type_id <>"
				+ REVENUE_TYPE_PATIENT_COLLECTION
				+ " ||pmtBatch.revenue_type_id is null)");
		sb.append(" and pmtBatch.doctor_id=" + PRO_HEALTH_GROUP_ID);
		sb.append(" group by doc.doctorcode asc WITH ROLLUP ");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntryDetailed()
	 */
	@Override
	public List<Object[]> journalEntryDetailed(String month, String year)
			throws ArgusException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rev.code as revenueCode, doc.doctorcode as doctorCode, doc.name as doctorName, "
				+ "pmtBatch.billing_month,rev.name as revenueName,sum(pmtBatch.deposit_amt+pmtBatch.ndba) ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year
				+ " and (pmtBatch.revenue_type_id <>"
				+ REVENUE_TYPE_PATIENT_COLLECTION
				+ " ||pmtBatch.revenue_type_id is null)");
		sb.append(" group by doc.doctorcode asc WITH ROLLUP ");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntryWithManagementFee()
	 */
	@Override
	public List<Object[]> journalEntryWithManagementFee(String month,
			String year) throws ArgusException {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT rev.code as revenueCode, doc.doctorcode as doctorCode, doc.name as doctorName, "
				+ "pmtBatch.billing_month,rev.name as revenueName,sum(pmtBatch.deposit_amt+pmtBatch.ndba), "
				+ " round(sum((pmtBatch.deposit_amt+pmtBatch.ndba)*doc.percentage),2) as mgmtfee ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year
				+ " and (pmtBatch.revenue_type_id <>"
				+ REVENUE_TYPE_PATIENT_COLLECTION
				+ " ||pmtBatch.revenue_type_id is null)");
		sb.append(" group by doc.doctorcode asc WITH ROLLUP ");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#dailyPaymentReceiptLog()
	 */
	@Override
	public List<Object[]> dailyPaymentReceiptLog(String month, String year)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();
		List<MoneySource> moneySourceList = moneySourceDao.findAll(true);
		sb.append("SELECT * FROM (((SELECT pmtBatch.id, rev.code as revenueCode, rev.name as revenueName, doc.doctorcode as doctorCode, "
				+ " doc.name as doctorName,pmtBatch.deposit_amt+pmtBatch.ndba as depositAmount ,"
				+ "DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		sb.append(" IFNULL((select sum(amount) from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id),0) as total");
		for (MoneySource ms : moneySourceList) {
			sb.append(", IFNULL((select amount from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id and "
					+ "payment_batch_money_source.money_source="
					+ ms.getId()
					+ "),0) as '" + ms.getName() + "'");
		}
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year);
		sb.append(" and pmtBatch.doctor_id=" + PRO_HEALTH_GROUP_ID
				+ " and (rev.code<>0000 || rev.code is null)");
		sb.append(" order by pmtBatch.deposit_date, rev.name) ");
		sb.append(" UNION ");
		sb.append(" (SELECT pmtBatch.id, rev.code as revenueCode, rev.name as revenueName, doc.doctorcode as doctorCode, "
				+ " doc.name as doctorName,SUM(pmtBatch.deposit_amt+pmtBatch.ndba) as depositAmount ,"
				+ "DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		sb.append(" IFNULL((select sum(amount) from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id),0) as total");
		for (MoneySource ms : moneySourceList) {
			sb.append(", SUM(IFNULL((select amount from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id and "
					+ "payment_batch_money_source.money_source="
					+ ms.getId()
					+ "),0)) as '" + ms.getName() + "'");
		}
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.billing_month=" + month
				+ " and pmtBatch.billing_year =" + year);
		sb.append(" and pmtBatch.doctor_id=" + PRO_HEALTH_GROUP_ID);
		sb.append(" group by revenueCode,pmtBatch.deposit_date ");
		sb.append(" having revenueCode = 0000 ");
		sb.append(" order by pmtBatch.deposit_date, rev.name)) a) ");
		sb.append(" order by a.depositDate ");
		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

}
