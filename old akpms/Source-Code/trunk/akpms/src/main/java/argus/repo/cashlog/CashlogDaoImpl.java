/**
 *
 */
package argus.repo.cashlog;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import argus.exception.ArgusException;
import argus.util.AkpmsUtil;
import argus.util.Constants;

/**
 * @author bhupender.s
 *
 */
@Repository
@Transactional
public class CashlogDaoImpl implements CashlogDao {

	private static final Logger LOGGER = Logger.getLogger(CashlogDaoImpl.class);

	@Autowired
	private EntityManager em;


	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntryManagementFee()
	 */
	/**
	 * REPORT-1: Journal Entries Management Fee
	 */
	@Override
	public List<Object[]> journalEntryManagementFee(String month, String year)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT "
				+ "'7090' as revenueCode, "
				+ "'' as revenueName, "
				+ "doc.doctorCode as doctorCode, "
				+ "doc.name as doctorName, "
				+ "SUM(pmtBatch.deposit_amt+pmtBatch.ndba), "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.accounting + rev.operations + rev.payments) ),2) as mgmtfee, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.payments) ),2) as mgmtfeePmt, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.accounting ) ),2) as mgmtfeeAcc, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.operations ) ),2) as mgmtfeeOpt ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.doctor_id=" + Constants.PRO_HEALTH_GROUP_ID);
		sb.append(" AND pmtBatch.payment_type_id = "
				+ Constants.PAYMENT_TYPE_ADMIN_INCOME);
		sb.append(" AND pmtBatch.billing_month=" + month
				+ " AND pmtBatch.billing_year =" + year);
		sb.append(" GROUP BY doc.name WITH ROLLUP");

		// sb.append(" WHERE pmtBatch.billing_month=" + month
		// + " and pmtBatch.billing_year =" + year
		// + " and (pmtBatch.revenue_type_id <>"
		// + REVENUE_TYPE_PATIENT_COLLECTION
		// + " ||pmtBatch.revenue_type_id is null)");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	/**
	 * REPORT-2: Journal Entries Summary
	 */
	@Override
	public List<Object[]> journalEntrySummary(String month, String year)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT "
				+ "rev.code as revenueCode, "
				+ "rev.name as revenueName, "
				+ "doc.doctorCode as doctorCode, "
				+ "doc.name as doctorName, "
				+ "SUM(pmtBatch.deposit_amt+pmtBatch.ndba), "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.accounting + rev.operations + rev.payments) ),2) as mgmtfee, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.payments) ),2) as mgmtfeePmt, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.accounting ) ),2) as mgmtfeeAcc, "
				+ "ROUND(SUM((pmtBatch.deposit_amt+pmtBatch.ndba)*(rev.operations ) ),2) as mgmtfeeOpt ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.doctor_id=" + Constants.PRO_HEALTH_GROUP_ID);
		sb.append(" AND pmtBatch.payment_type_id = "
				+ Constants.PAYMENT_TYPE_ADMIN_INCOME);
		sb.append(" AND pmtBatch.billing_month=" + month
				+ " AND pmtBatch.billing_year =" + year);
		sb.append(" GROUP BY doc.name, rev.name ");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#journalEntryDetailed()
	 *
	 * REPORT-3/4
	 */
	@Override
	public List<Object[]> journalEntryDetailedWithManagementFee(String month,
			String year) throws ArgusException {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT  "
				+ "pmtBatch.id as ticketNumber, "
				+ "doc.doctorCode as doctorCode, "
				+ "doc.name as doctorName, "
				+ "DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, "
				+ "pmtBatch.billing_month, "
				+ "mny_src.name as moneySourceName, "
				+ "rev.code as revenueCode, "
				+ "rev.name as revenueName, "
				+ "SUM(pmtBatch.deposit_amt + pmtBatch.ndba), "
				+ "(ROUND((rev.accounting + rev.operations + rev.payments),2)*100) as docPercentage, "
				+ "ROUND((SUM(pmtBatch.deposit_amt + pmtBatch.ndba)*(rev.accounting + rev.operations + rev.payments) ),2) as mgmtfee ");
		sb.append(" FROM payment_batch pmtBatch ");
		sb.append(" LEFT JOIN money_source mny_src ON pmtBatch.money_source_id = mny_src.id ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" WHERE pmtBatch.doctor_id=" + Constants.PRO_HEALTH_GROUP_ID);
		sb.append(" AND pmtBatch.payment_type_id = "
				+ Constants.PAYMENT_TYPE_ADMIN_INCOME);
		sb.append(" AND pmtBatch.billing_month=" + month
				+ " AND pmtBatch.billing_year =" + year);
		sb.append(" GROUP BY doc.name,  pmtBatch.id asc WITH ROLLUP ");

		LOGGER.info(sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see argus.repo.cashlog.CashlogDao#dailyPaymentReceiptLog()
	 *
	 * REPORT-5
	 */
	@Override
	public List<Object[]> dailyPaymentReceiptLog(String month, String year,
			String depositDateFrom, String depositDateTo)
			throws ArgusException {
		StringBuilder sb = new StringBuilder();
		// sb.append("SELECT * FROM (((SELECT pmtBatch.id, rev.code as revenueCode, rev.name as revenueName, mnySrc.name as moneySourceName, doc.doctorcode as doctorCode, "
		// +
		// " doc.name as doctorName,pmtBatch.deposit_amt+pmtBatch.ndba as depositAmount ,"
		// + "DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		// //
		// sb.append(" IFNULL((select sum(amount) from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id),0) as total");
		// // for (MoneySource ms : moneySourceList) {
		// //
		// sb.append(", IFNULL((select amount from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id and "
		// // + "payment_batch_money_source.money_source="
		// // + ms.getId()
		// // + "),0) as '" + ms.getName() + "'");
		// // }
		// sb.append(" FROM payment_batch pmtBatch ");
		// sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		// sb.append(" LEFT JOIN money_source mnySrc ON pmtBatch.money_source_id = mnySrc.id ");
		// sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		// sb.append(" WHERE pmtBatch.billing_month=" + month
		// + " and pmtBatch.billing_year =" + year);
		// sb.append(" and pmtBatch.doctor_id=" + Constants.PRO_HEALTH_GROUP_ID
		// + " and (rev.code<>0000 || rev.code is null)");
		// sb.append(" order by pmtBatch.deposit_date, rev.name) ");
		// sb.append(" UNION ");
		// sb.append(" (SELECT pmtBatch.id, rev.code as revenueCode, mnySrc.name as moneySourceName, rev.name as revenueName, doc.doctorcode as doctorCode, "
		// +
		// " doc.name as doctorName,SUM(pmtBatch.deposit_amt+pmtBatch.ndba) as depositAmount ,"
		// + "DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		// //
		// sb.append(" IFNULL((select sum(amount) from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id),0) as total");
		// // for (MoneySource ms : moneySourceList) {
		// //
		// sb.append(", SUM(IFNULL((select amount from payment_batch_money_source where payment_batch_money_source.batch_id = pmtBatch.id and "
		// // + "payment_batch_money_source.money_source="
		// // + ms.getId()
		// // + "),0)) as '" + ms.getName() + "'");
		// // }
		// sb.append(" FROM payment_batch pmtBatch ");
		// sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		// sb.append(" LEFT JOIN money_source mnySrc ON pmtBatch.money_source_id = mnySrc.id ");
		// sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		// sb.append(" WHERE pmtBatch.billing_month=" + month
		// + " and pmtBatch.billing_year =" + year);
		// sb.append(" and pmtBatch.doctor_id=" +
		// Constants.PRO_HEALTH_GROUP_ID);
		// sb.append(" group by revenueCode,pmtBatch.deposit_date ");
		// sb.append(" having revenueCode = 0000 ");
		// sb.append(" order by pmtBatch.deposit_date, rev.name)) a) ");
		// sb.append(" order by a.depositDate ");

		sb.append(" SELECT ");
		sb.append(" doc.name as doctorName, ");
		sb.append(" DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		sb.append(" rev.name as revenueType, ");
		sb.append(" (SUM(pmtBatch.deposit_amt) + SUM(pmtBatch.ndba)) as depositTotal, ");
		sb.append(" SUM(pmtBatch.vault) as vault, ");
		sb.append(" SUM(pmtBatch.credit_card) as creditCard, ");
		sb.append(" SUM(pmtBatch.telecheck) as telecheck, ");
		sb.append(" pmtBatch.money_source_id, ");
		sb.append(" '0.0' as LOCKBOX, ");
		sb.append(" '0.0' as EFT, ");
		sb.append(" GROUP_CONCAT(CAST(IFNULL(pmtBatch.money_source_id,' ') as CHAR))  as moneySourceIds, GROUP_CONCAT(CAST((pmtBatch.deposit_amt + pmtBatch.ndba) as CHAR)) AS depositAmts");
		sb.append(" FROM payment_batch as pmtBatch ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" WHERE pmtBatch.doctor_id = "
				+ Constants.PRO_HEALTH_GROUP_ID);
		sb.append(" AND pmtBatch.payment_type_id = "
				+ Constants.PAYMENT_TYPE_ADMIN_INCOME);
		sb.append(" AND pmtBatch.billing_month = " + month);
		sb.append(" AND pmtBatch.billing_year = " + year);

		// date deposit from
		if (depositDateFrom != "") {
			LOGGER.info("found DATE_DEPOSIT_FROM = " + depositDateFrom);
			sb.append(" AND DATE_FORMAT(pmtBatch.deposit_date, '%Y-%m-%d') >= '"
					+ AkpmsUtil.akpmsDateFormat(
							AkpmsUtil.akpmsNewDateFormat(depositDateFrom),
							Constants.MYSQL_DATE_FORMAT) + "'");
		}

		// date deposit To
		if (depositDateTo != "") {
			LOGGER.info("found DATE_DEPOSIT_TO = " + depositDateTo);
			sb.append(" AND DATE_FORMAT(pmtBatch.deposit_date, '%Y-%m-%d') <= '"
					+ AkpmsUtil.akpmsDateFormat(
							AkpmsUtil.akpmsNewDateFormat(depositDateTo),
							Constants.MYSQL_DATE_FORMAT) + "'");
		}

		// sb.append(" GROUP BY pmtBatch.pro_health_doctor_id ,  pmtBatch.revenue_type_id,  pmtBatch.deposit_date ");
		sb.append(" GROUP BY pmtBatch.id ");

		sb.append(" UNION ALL ");

		sb.append(" SELECT ");
		sb.append(" 'ProHealth' as doctorName, ");
		sb.append(" DATE_FORMAT(pmtBatch.deposit_date, '%m/%d/%Y') as depositDate, ");
		sb.append(" 'Patient Collection' as revenueType, ");
		sb.append(" (SUM(pmtBatch.deposit_amt) + SUM(pmtBatch.ndba)) as depositTotal, ");
		sb.append(" SUM(pmtBatch.vault) as vault, ");
		sb.append(" SUM(pmtBatch.credit_card) as creditCard, ");
		sb.append(" SUM(pmtBatch.telecheck) as telecheck, ");
		sb.append(" pmtBatch.money_source_id, ");
		sb.append(" '0.0' as LOCKBOX, ");
		sb.append(" '0.0' as EFT, ");
		sb.append(" GROUP_CONCAT(CAST(IFNULL(pmtBatch.money_source_id,' ') as CHAR))  as moneySourceIds, GROUP_CONCAT(CAST((pmtBatch.deposit_amt + pmtBatch.ndba) as CHAR)) AS depositAmts");
		sb.append(" FROM payment_batch as pmtBatch ");
		sb.append(" LEFT JOIN doctor doc ON pmtBatch.pro_health_doctor_id = doc.id ");
		sb.append(" LEFT JOIN revenue_type rev ON pmtBatch.revenue_type_id = rev.id ");
		sb.append(" WHERE pmtBatch.doctor_id = "
				+ Constants.PRO_HEALTH_GROUP_ID);
		sb.append(" AND pmtBatch.payment_type_id <> "
				+ Constants.PAYMENT_TYPE_ADMIN_INCOME);
		sb.append(" AND pmtBatch.billing_month = " + month);
		sb.append(" AND pmtBatch.billing_year = " + year);

		// date deposit from
		if (depositDateFrom != "") {
			LOGGER.info("found DATE_DEPOSIT_FROM = " + depositDateFrom);
			sb.append(" AND DATE_FORMAT(pmtBatch.deposit_date, '%Y-%m-%d') >= '"
					+ AkpmsUtil.akpmsDateFormat(
							AkpmsUtil.akpmsNewDateFormat(depositDateFrom),
							Constants.MYSQL_DATE_FORMAT) + "'");
		}

		// date deposit To
		if (depositDateTo != "") {
			LOGGER.info("found DATE_DEPOSIT_TO = " + depositDateTo);
			sb.append(" AND DATE_FORMAT(pmtBatch.deposit_date, '%Y-%m-%d') <= '"
					+ AkpmsUtil.akpmsDateFormat(
							AkpmsUtil.akpmsNewDateFormat(depositDateTo),
							Constants.MYSQL_DATE_FORMAT) + "'");
		}

		sb.append(" GROUP BY pmtBatch.revenue_type_id,  pmtBatch.deposit_date");
		sb.append(" ORDER BY depositDate, revenueType, doctorName ");

		LOGGER.info("SQL QUERY:: " + sb.toString());
		return em.createNativeQuery(sb.toString()).getResultList();
	}

}
