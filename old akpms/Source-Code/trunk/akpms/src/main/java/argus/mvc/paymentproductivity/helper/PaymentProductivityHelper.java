/**
 *
 */
package argus.mvc.paymentproductivity.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.WebRequest;

import argus.domain.PaymentBatch;
import argus.exception.ArgusException;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.PaymentBatchJsonData;

/**
 * @author rajiv.k
 *
 */
public final class PaymentProductivityHelper {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentProductivityHelper.class);

	private static final int ONE = 1;

	private static final int TWO = 2;

	/**
	 *
	 */
	private PaymentProductivityHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * @param request
	 * @return Map<String, String>
	 */
	public static Map<String, String> getOrderClause(WebRequest request) {
		LOGGER.info("in [getOrderClause]");
		Map<String, String> orderClauses = new HashMap<String, String>();
		int page = 1;
		int rp = 0;
		if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
			rp = Integer.parseInt(request
					.getParameter(Constants.RECORD_PRE_PAGE));
			try {
				orderClauses.put(Constants.LIMIT, "" + rp);
			} catch (Exception e) {
				LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
			}
		}

		if (request.getParameter(Constants.PAGE) != null) {
			try {
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
				orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
			} catch (Exception e) {
				LOGGER.debug("Exception during parsing");
			}
		}

		if (request.getParameter(Constants.SORT_ORDER) != null) {
			orderClauses.put(Constants.SORT_BY,
					request.getParameter(Constants.SORT_ORDER));
		}

		if (request.getParameter(Constants.SORT_NAME) != null) {
			orderClauses.put(Constants.ORDER_BY,
					request.getParameter(Constants.SORT_NAME));
		}

		return orderClauses;
	}

	/**
	 *
	 * @param request
	 * @return Map<String, String>
	 */
	public static Map<String, String> getWhereClause(WebRequest request) {
		LOGGER.info("in [getWhereClause]");
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter(Constants.QTYPE) != null
				&& request.getParameter(Constants.QUERY) != null
				&& !request.getParameter(Constants.QUERY).isEmpty()) {
			whereClauses.put(request.getParameter(Constants.QTYPE),
					request.getParameter(Constants.QUERY));
		}
		if (request.getParameter(Constants.KEYWORD) != null
				&& request.getParameter(Constants.KEYWORD).trim().length() > 0) {
			String keyword = request.getParameter(Constants.KEYWORD);
			if (keyword.contains("%")) {
				return null;
			} else {
				whereClauses.put(Constants.KEYWORD, keyword);
			}
		}

		return whereClauses;
	}

	/**
	 *
	 * @param value
	 * @return
	 * @throws ArgusException
	 */
	public static Date initBinder(String value) throws ArgusException {
		try {
			if (value != null && value.trim().length() > 0) {
				LOGGER.info("comng date value is : " + value);
				return new SimpleDateFormat(Constants.DATE_FORMAT).parse(value);
			} else {
				LOGGER.info("Date value is: " + value);
				return null;
			}
		} catch (ParseException e) {
			LOGGER.error(Constants.EXCEPTION, e);
			throw new ArgusException(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param workFlowId
	 * @return
	 * @throws ArgusException
	 */
	public static String getFlow(int workFlowId) throws ArgusException {
		LOGGER.info("int [getFlow()] method");
		String workFlow = "";
		switch (workFlowId) {
		case Constants.ERA_WORK_FLOW:
			workFlow = "redirect:/paymentproductivityERA/add";
			break;
		case Constants.NON_ERA_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera/add?prodType="
					+ Constants.NON_ERA_WORK_FLOW;
			break;
		case Constants.CAP_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera/add?prodType="
					+ Constants.CAP_WORK_FLOW;
			break;
		case Constants.REFUND_REQUEST_WORK_FLOW:
			workFlow = "redirect:/paymentproductivityrefundrequest/add";
			break;
		case Constants.HOURLY_WORK_FLOW:
			workFlow = "redirect:/paymentproductivityhourly/add";
			break;
		default:
			break;
		}

		return workFlow;
	}

	/**
	 *
	 * @param workFlowId
	 * @return
	 * @throws ArgusException
	 */

	public static String getWorkFlowName(int workFlowId) throws ArgusException {
		LOGGER.info("int [getWorkFlowName()] method : workFlowId ="
				+ workFlowId);
		String workFlowName = "";
		switch (workFlowId) {
		case Constants.TO_AR_IPA_FFS_HMO_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_IPA_FFS_HMO;
			break;
		case Constants.TO_AR_FFS_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_FFS;
			break;
		case Constants.TO_AR_CEP_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_CEP;
			break;
		case Constants.TO_AR_MCL_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_TO_AR_MCL;
			break;
		case Constants.OFFSET_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_OFFSET;
			break;
		case Constants.QUERY_TO_TL_WORK_FLOW:
			workFlowName = Constants.WORK_FLOW_QUERY_TO_TL;
			break;
		default:
			break;
		}

		return workFlowName;
	}

	/**
	 *
	 * @param workFlowId
	 * @return
	 * @throws ArgusException
	 */
	public static String getWorkFlow(int workFlowId, long prodId, boolean edit) {
		LOGGER.info("int [getWorkFlow()] method : workFlowId = " + workFlowId);
		String workFlow = "";
		switch (workFlowId) {
		case Constants.TO_AR_IPA_FFS_HMO_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera";
			break;
		case Constants.TO_AR_FFS_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera";
			break;
		case Constants.TO_AR_CEP_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera";
			break;
		case Constants.TO_AR_MCL_WORK_FLOW:
			workFlow = "redirect:/paymentproductivitynonera";
			break;
		case Constants.OFFSET_WORK_FLOW:
			if (edit) {
				workFlow = "redirect:/paymentproderaoffset/add?id=" + prodId;
			} else {
				workFlow = "redirect:/paymentproderaoffset/add";
			}
			break;
		case Constants.QUERY_TO_TL_WORK_FLOW:
			if (edit) {
				workFlow = "redirect:/paymentprodquerytotl/add?id=" + prodId;
			} else {
				workFlow = "redirect:/paymentprodquerytotl/add";
			}
			break;
		default:
			workFlow = "redirect:/paymentproductivitynonera";
			break;
		}

		return workFlow;
	}

	public static Object[] getPaymentBatch(Object[] obj, String batchId,
			PaymentBatch paymentBatch, PaymentBatchDao batchDao,
			MessageSource messageSource) {

		try {
			if (batchId != null && !batchId.trim().isEmpty()) {
				paymentBatch = batchDao.findById(Long.valueOf(batchId), true);
			}

			if (paymentBatch != null) {
				if (paymentBatch.getPostedBy() != null) {
					LOGGER.info("Already Posted Batch");
					obj = new Object[TWO];
					obj[Constants.ZERO] = Constants.ERR;

					obj[ONE] = messageSource.getMessage("batch.posted", null,
							Locale.ENGLISH).trim();
					return obj;
				} else {
					PaymentBatchJsonData paymentBatchJsonData = new PaymentBatchJsonData();
					paymentBatchJsonData.setId(paymentBatch.getId());
					paymentBatchJsonData.setCreatedBy(paymentBatch
							.getCreatedBy().getFirstName()
							+ " "
							+ paymentBatch.getCreatedBy().getLastName());
					paymentBatchJsonData.setDepositDate(AkpmsUtil
							.akpmsDateFormat(paymentBatch.getDepositDate(),
									Constants.DATE_FORMAT));
					paymentBatchJsonData.setBillingMonth(paymentBatch
							.getBillingMonth().toString()
							+ "/"
							+ paymentBatch.getBillingYear());
					if (paymentBatch.getDepositAmount() != null
							&& paymentBatch.getDepositAmount() != Constants.ZERO) {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getDepositAmount());
					} else {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getNdba());
					}
					if(paymentBatch.getDoctor().isNonDeposit()) {


					paymentBatchJsonData.setDoctor(paymentBatch.getDoctor()
							.getName()+" (Non-Deposit)");
					}
					else{
						paymentBatchJsonData.setDoctor(paymentBatch.getDoctor()
								.getName());
					}
					if (null != paymentBatch.getPhDoctor()) {
						paymentBatchJsonData.setParentDoctor(paymentBatch
								.getPhDoctor().getName());
					} else {
						paymentBatchJsonData.setParentDoctor("");
					}

					if (null != paymentBatch.getInsurance()) {
						paymentBatchJsonData.setInsurance(paymentBatch
								.getInsurance().getName());
					} else {
						paymentBatchJsonData.setInsurance("");
					}

					paymentBatchJsonData.setPaymentType(paymentBatch
							.getPaymentType().getName());
					paymentBatchJsonData.setComment(paymentBatch.getComment());
					obj = new Object[TWO];
					obj[Constants.ZERO] = paymentBatchJsonData;
					obj[ONE] = paymentBatch;
					return obj;
				}
			} else {
				obj = new Object[TWO];
				obj[Constants.ZERO] = Constants.ERR;
				obj[ONE] = messageSource.getMessage("batch.notAvailable", null,
						Locale.ENGLISH).trim();
				return obj;
			}
		}

		catch (Exception e) {

			LOGGER.error(Constants.EXCEPTION, e);
			LOGGER.info("going to send error message on exception");
			obj = new Object[TWO];
			obj[Constants.ZERO] = Constants.ERR;
			obj[ONE] = messageSource.getMessage("batch.notAvailable", null,
					Locale.ENGLISH).trim();
			return obj;

		}

	}

	public static Map<String, String> getWhereClauseForOffset(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter(Constants.QTYPE) != null
				&& request.getParameter(Constants.QUERY) != null
				&& !request.getParameter(Constants.QUERY).isEmpty()) {
			whereClauses.put(request.getParameter(Constants.QTYPE),
					request.getParameter(Constants.QUERY));
		}

		if (request.getParameter(Constants.POSTED_BY_ID) != null
				& request.getParameter(Constants.POSTED_BY_ID).trim().length() > Constants.ZERO) {
			String postedBy = request.getParameter(Constants.POSTED_BY_ID);
			whereClauses.put(Constants.POSTED_BY_ID, postedBy);
		}

		/* set posted from date */
		if (request.getParameter(Constants.DATE_POSTED_FROM) != null
				&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
						.length() > 0) {
			LOGGER.info("DATE_POSTED_FROM= "
					+ request.getParameter(Constants.DATE_POSTED_FROM));
			whereClauses.put(Constants.DATE_POSTED_FROM,
					request.getParameter(Constants.DATE_POSTED_FROM));
		}

		/* set posted to date */
		if (request.getParameter(Constants.DATE_POSTED_TO) != null
				&& request.getParameter(Constants.DATE_POSTED_TO).trim()
						.length() > 0) {
			LOGGER.info("DATE_POSTED_TO= "
					+ request.getParameter(Constants.DATE_POSTED_TO));
			whereClauses.put(Constants.DATE_POSTED_TO,
					request.getParameter(Constants.DATE_POSTED_TO));
		}

		if (request.getParameter(Constants.TICKET_NUMBER) != null
				&& request.getParameter(Constants.TICKET_NUMBER).trim()
						.length() > 0) {
			String ticketNumber = request.getParameter(Constants.TICKET_NUMBER);

			whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
		}
		if (request.getParameter(Constants.ACCOUNT_NUMBER) != null
				&& request.getParameter(Constants.ACCOUNT_NUMBER).trim()
						.length() > 0) {
			String accountNo = request.getParameter(Constants.ACCOUNT_NUMBER);

			whereClauses.put(Constants.ACCOUNT_NUMBER, accountNo);
		}

		if (request.getParameter(Constants.PATIENT_NAME) != null
				&& request.getParameter(Constants.PATIENT_NAME).trim().length() > Constants.ZERO) {
			String patientName = request.getParameter(Constants.PATIENT_NAME);

			whereClauses.put(Constants.PATIENT_NAME, patientName);
		}
		if (request.getParameter(Constants.CHECK_NUMBER) != null
				&& request.getParameter(Constants.CHECK_NUMBER).trim().length() > Constants.ZERO) {
			String checkNo = request.getParameter(Constants.CHECK_NUMBER);

			whereClauses.put(Constants.CHECK_NUMBER, checkNo);
		}
		return whereClauses;
	}

}
