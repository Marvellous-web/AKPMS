package argus.mvc.chargeProductivity;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.ChargeBatchProcessing;
import argus.domain.ChargeProdReject;
import argus.exception.ArgusException;
import argus.repo.chargeBatchProcessing.ChargeBatchProcessingDao;
import argus.repo.chargeProductivity.ChargeProdRejectDao;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.util.AkpmsUtil;
import argus.util.ChargeBatchRejectJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.ChargeProdRejectReUpdateValidator;
import argus.validator.ChargeProdRejectValidator;

@Controller
@RequestMapping(value = "/chargeproductivityreject")
@SessionAttributes(value = Constants.CHARGE_PROD_REJECT)
public class ChargeProdRejectController {

	private static final Log LOGGER = LogFactory
			.getLog(ChargeProdRejectController.class);

	private static final String CHARGE_PROD_REJECT_VIEW_PATH = "chargeProductivity/chargeProdRejection";

	@Autowired
	private ChargeBatchProcessingDao chargeBatchProcessingDao;

	@Autowired
	private ChargeProductivityDao chargeProductivityDao;

	@Autowired
	private ChargeProdRejectDao chargeProdRejectDao;

	@Autowired
	private ChargeProdRejectValidator chargeProdRejectValidator;

	@Autowired
	private ChargeProdRejectReUpdateValidator chargeProdRejectReUpdateValidator;

	@Resource(name = "chargeBatchInsuranceTypeProperties")
	private Properties chargeBatchInsuranceType;

	private List<String> reasonsToReject = new ArrayList<String>();

	private int page = 1;

	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void init() {
		reasonsToReject.add("Coding");
		reasonsToReject.add("No CPT");
		reasonsToReject.add("Demo");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					if (value != null && value.trim().length() > 0) {
						LOGGER.info("comng date value is : " + value);
						setValue(new SimpleDateFormat(Constants.DATE_FORMAT)
								.parse(value));
					} else {
						LOGGER.info("Date value is: " + value);
						setValue(null);
					}
				} catch (ParseException e) {
					LOGGER.error(Constants.EXCEPTION, e);
					setValue(null);
				}
			}

			public String getAsText() {
				if (getValue() != null) {
					return new SimpleDateFormat(Constants.DATE_FORMAT)
							.format((Date) getValue());
				}
				return "";
			}
		});

	}

	private String authenticationCheck() {
		if (!AkpmsUtil.checkDepartment(Long
				.valueOf(Constants.CODING_CHARGE_POSTING))
				|| (AkpmsUtil.getLoggedInUser().getRole().getId() != Constants.STANDART_USER_ROLE_ID)) {
			return Constants.ERROR;
		} else {
			return "";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getRejectedBatch(Model model, WebRequest request,
			Map<String, Object> map) {
		try {
			if (authenticationCheck().equals(Constants.ERROR)) {
				return "redirect:error403";
			}
			if (null != request
					&& null != request.getParameter(Constants.BATCH_ID)) {
				try {
					ChargeProdReject chargeProdReject = new ChargeProdReject();
					long id = Long.parseLong(request
							.getParameter(Constants.BATCH_ID));
					ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
							.getChargeBatchProcessingById(id, false);
					if (chargeBatchProcessing != null) {
						LOGGER.info("found chargeBatchProcess object");
						chargeProdReject
								.setChargeBatchProcessing(chargeBatchProcessing);
						map.put(Constants.HIDE, Constants.HIDE);
					} else {
						LOGGER.info("no charge batch process object found corresponding batch id = "
								+ id);
					}
					model.addAttribute(Constants.CHARGE_PROD_REJECT,
							chargeProdReject);
					model.addAttribute("insuranceTypes",
							chargeBatchInsuranceType);
					model.addAttribute("reasonsToReject", reasonsToReject);
					map.put(Constants.OPERATION_TYPE, Constants.ADD);

				} catch (Exception e) {
					map.put("warning", "Batch ID does not exist");
					LOGGER.error(Constants.EXCEPTION, e);
				}
			} else {
				LOGGER.info("Batch id is coming null");
				if (null != request
						&& null != request.getParameter(Constants.REJECTION_ID)) {
					try {
						long id = Long.parseLong(request
								.getParameter(Constants.REJECTION_ID));
						ChargeProdReject chargeProdReject = chargeProdRejectDao
								.getChargeProdRejectById(id, true);
						model.addAttribute(Constants.CHARGE_PROD_REJECT,
								chargeProdReject);
						map.put(Constants.OPERATION_TYPE, Constants.EDIT);
						map.put(Constants.HIDE, Constants.HIDE);
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
					}
				} else {
					model.addAttribute(Constants.CHARGE_PROD_REJECT,
							new ChargeProdReject());
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
				}

				model.addAttribute("insuranceTypes", chargeBatchInsuranceType);
				model.addAttribute("reasonsToReject", reasonsToReject);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return CHARGE_PROD_REJECT_VIEW_PATH;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postRejectedBatch(
			@ModelAttribute(value = Constants.CHARGE_PROD_REJECT) ChargeProdReject chargeProdReject,
			Model model, WebRequest request, BindingResult result,
			Map<String, String> map, HttpSession session) {
		LOGGER.info("insurance = " + chargeProdReject.getInsuranceType());
		try {
			chargeProdRejectValidator.validate(chargeProdReject, result);

			if (!result.hasErrors()) {
				try {
					if (chargeProdReject.getId() != null) {
						LOGGER.info("going to update");
						chargeProdRejectDao.update(chargeProdReject);
						session.setAttribute(Constants.SUCCESS_UPDATE,
								"reject.updatedSuccessfully");
					} else {
						LOGGER.info("going to save");
						chargeProdRejectDao.save(chargeProdReject);
						session.setAttribute(Constants.SUCCESS_UPDATE,
								"reject.addedSuccessfully");
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}

				return "redirect:/chargeproductivityreject";
			} else {
				List<ObjectError> oeList = result.getAllErrors();
				for (ObjectError oe : oeList) {
					LOGGER.info("Errors:" + "field name = "
							+ oe.getObjectName() + " , value ="
							+ oe.getDefaultMessage());
				}

				if (chargeProdReject.getId() != null) {
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.HIDE, Constants.HIDE);
				} else {
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					if (chargeProdReject.getChargeBatchProcessing() != null
							&& chargeProdReject.getChargeBatchProcessing()
									.getId() != null) {
						try {
							ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
									.getChargeBatchProcessingById(
											chargeProdReject
													.getChargeBatchProcessing()
													.getId(), false);
							chargeProdReject
									.setChargeBatchProcessing(chargeBatchProcessing);
						} catch (Exception e) {
							LOGGER.error(Constants.EXCEPTION, e);
						}
					}
					if (null != request
							&& null != request.getParameter(Constants.BATCH_ID)) {
						map.put(Constants.HIDE, Constants.HIDE);
					}
				}
				model.addAttribute(Constants.CHARGE_PROD_REJECT,
						chargeProdReject);
				model.addAttribute("insuranceTypes", chargeBatchInsuranceType);
				model.addAttribute("reasonsToReject", reasonsToReject);
				model.addAttribute("validationError", "yes");

				return CHARGE_PROD_REJECT_VIEW_PATH;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			return CHARGE_PROD_REJECT_VIEW_PATH;
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getListPage(WebRequest request, Map<String, Object> map,
			HttpSession session) {
		if (authenticationCheck().equals(Constants.ERROR)) {
			return "redirect:error403";
		}
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);

		if (request != null && request.getParameter("fetchType") != null) {
			map.put("fetchType", request.getParameter("fetchType"));
		}
		return "chargeProductivity/chargeProdRejectionList";
	}

	private Map<String, String> getOrderClause(WebRequest request) {
		Map<String, String> orderClauses = new HashMap<String, String>();

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

	private Map<String, String> getWhereClause(WebRequest request) {
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
		if (request.getParameter("dummyCpt") != null) {
			whereClauses.put("dummyCpt", request.getParameter("dummyCpt"));
		}
		if (request.getParameter(Constants.TICKET_NUMBER) != null
				&& request.getParameter(Constants.TICKET_NUMBER).trim()
						.length() > 0) {
			whereClauses.put(Constants.TICKET_NUMBER,
					request.getParameter(Constants.TICKET_NUMBER));
		}

		if (request.getParameter("newRejections") != null) {
			whereClauses.put("newRejections",
					request.getParameter("newRejections"));
		}
		if (request.getParameter("resolved") != null) {
			whereClauses.put("resolved", request.getParameter("resolved"));
		}

		if (request.getParameter("requestRecord") != null) {
			whereClauses.put("requestRecord",
					request.getParameter("requestRecord"));
		}
		if (request.getParameter("requestDueRecord") != null) {
			whereClauses.put("requestDueRecord",
					request.getParameter("requestDueRecord"));
		}
		return whereClauses;
	}

	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ChargeBatchRejectJsonData> getRejctionList(
			WebRequest request) {
		LOGGER.info("in json method");
		try {
			Map<String, String> orderClauses = new HashMap<String, String>();
			Map<String, String> whereClauses = new HashMap<String, String>();

			if (request != null) {

				orderClauses = getOrderClause(request);
				whereClauses = getWhereClause(request);

			} else {
				LOGGER.info("request object is coming null");
			}
			int totalRows = 0;
			try {
				totalRows = chargeProdRejectDao.totalRecords(whereClauses);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			if (totalRows > 0) {
				List<ChargeProdReject> rows = chargeProdRejectDao.findAll(
						orderClauses, whereClauses, true);
				List<ChargeBatchRejectJsonData> chargeBatchRejectJsonDataList = getJsonData(rows);
				JsonDataWrapper<ChargeBatchRejectJsonData> jdw = new JsonDataWrapper<ChargeBatchRejectJsonData>(
						page, totalRows, chargeBatchRejectJsonDataList);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return null;
	}

	private List<ChargeBatchRejectJsonData> getJsonData(
			List<ChargeProdReject> rows) {
		List<ChargeBatchRejectJsonData> chargeBatchRejectJsonDataList = new ArrayList<ChargeBatchRejectJsonData>();

		if (rows != null && !rows.isEmpty()) {
			for (ChargeProdReject chargeProdReject : rows) {
				ChargeBatchRejectJsonData chargeBatchRejectJsonData = new ChargeBatchRejectJsonData();
				chargeBatchRejectJsonData.setId(chargeProdReject.getId());
				chargeBatchRejectJsonData.setPatientName(chargeProdReject
						.getPatientName());
				chargeBatchRejectJsonData.setAccount(chargeProdReject
						.getAccount());
				chargeBatchRejectJsonData.setSequence(chargeProdReject
						.getSequence());
				chargeBatchRejectJsonData.setReasonToReject(chargeProdReject
						.getReasonToReject());
				if (chargeProdReject.getDob() != null) {
					chargeBatchRejectJsonData.setDob(new SimpleDateFormat(
							Constants.DATE_FORMAT).format(chargeProdReject
							.getDob()));
				}

				if (chargeProdReject.getDateOfFirstRequestToDoctorOffice() != null) {
					chargeBatchRejectJsonData
							.setDateOfFirstRequestToDoctorOffice(new SimpleDateFormat(
									Constants.DATE_FORMAT).format(chargeProdReject
									.getDateOfFirstRequestToDoctorOffice()));
				}

				if (chargeProdReject.getDateOfSecondRequestToDoctorOffice() != null) {
					chargeBatchRejectJsonData
							.setDateOfSecondRequestToDoctorOffice(new SimpleDateFormat(
									Constants.DATE_FORMAT).format(chargeProdReject
									.getDateOfSecondRequestToDoctorOffice()));
				}
				chargeBatchRejectJsonData
						.setDateOfService(new SimpleDateFormat(
								Constants.DATE_FORMAT).format(chargeProdReject
								.getDateOfService()));
				chargeBatchRejectJsonData
						.setInsuranceType(chargeBatchInsuranceType
								.getProperty(chargeProdReject
										.getInsuranceType()));
				chargeBatchRejectJsonData
						.setChargeBatchProcessing(chargeProdReject
								.getChargeBatchProcessing().getId());

				chargeBatchRejectJsonDataList.add(chargeBatchRejectJsonData);
			}
		}
		return chargeBatchRejectJsonDataList;
	}

	@RequestMapping(value = "batch/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object[] getDetail(@RequestParam(Constants.BATCH_ID) String batchId,
			WebRequest request) {
		Object[] obj = null;
		try {
			if (batchId != null && !batchId.trim().isEmpty()) {
				long id = Long.parseLong(batchId);

				LOGGER.info("coming Batch id = " + id);

				try {
					long count = chargeProductivityDao
							.checkProductivityWithRejectWorkFlowByTicketNumber(id);
					if (count < Constants.ONE) {
						obj = new Object[2];
						obj[Constants.ZERO] = Constants.ERR;
						obj[Constants.ONE] = messageSource.getMessage(
								"productivity.noProductivity", null,
								Locale.ENGLISH);
						return obj;
					}
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = Constants.ERR;
					obj[Constants.ONE] = messageSource
							.getMessage("productivity.noProductivity", null,
									Locale.ENGLISH);
					return obj;
				}
				ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
						.getChargeBatchProcessingShortDetailById(id);
				obj = new Object[Constants.TWO];
				obj[Constants.ZERO] = chargeBatchProcessing.getId();
				LOGGER.info("Doctor name = "
						+ chargeBatchProcessing.getDoctor().getName());
				obj[Constants.ONE] = chargeBatchProcessing.getDoctor()
						.getName();
			} else {
				LOGGER.info("coming Batch id is null or empty ");
				obj = new Object[Constants.TWO];
				obj[Constants.ZERO] = Constants.ERR;
				obj[Constants.ONE] = messageSource.getMessage(
						"chargeProdReject.ticketNumber.required", null,
						Locale.ENGLISH);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			obj = new Object[Constants.TWO];
			obj[Constants.ZERO] = Constants.ERR;
			obj[Constants.ONE] = messageSource.getMessage(
					"productivity.noProductivity", null, Locale.ENGLISH);
		}
		return obj;
	}

	@RequestMapping(value = "/reupdate", method = RequestMethod.GET)
	public String getReUpdate(WebRequest request, Map<String, Object> map) {
		if (authenticationCheck().equals(Constants.ERROR)) {
			return "redirect:error403";
		}

		if (null != request
				&& null != request.getParameter(Constants.REJECTION_ID)) {
			try {
				long id = Long.parseLong(request
						.getParameter(Constants.REJECTION_ID));
				ChargeProdReject chargeProdReject = chargeProdRejectDao
						.getChargeProdRejectById(id, true);
				map.put(Constants.CHARGE_PROD_REJECT, chargeProdReject);
				map.put("batchId", request.getParameter("batchId"));
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			LOGGER.warn(Constants.REJECTION_ID + " parmeter is coming null");
			map.put(Constants.CHARGE_PROD_REJECT, new ChargeProdReject());
		}
		return "chargeProductivity/chargeProdRejectReUpdate";
	}

	@RequestMapping(value = "/reupdate", method = RequestMethod.POST)
	public String setReUpdate(
			@ModelAttribute(Constants.CHARGE_PROD_REJECT) ChargeProdReject chargeProdReject,
			BindingResult result, Map<String, Object> map, WebRequest request,
			HttpSession session) {

		if (request.getParameter("operation") != null
				&& request.getParameter("operation").trim().length() > 0) {
			if (request.getParameter("operation").equals("resolved")) {
				try {
					LOGGER.info("going set Resolved");
					chargeProdReject.setResolved(true);
					chargeProdRejectDao.update(chargeProdReject);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"resolution.updatedSuccessfully");
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				return "redirect:/chargeproductivityreject";
			}
		}
		chargeProdRejectReUpdateValidator.validate(chargeProdReject, result);
		if (!result.hasErrors()) {
			try {
				LOGGER.info("NO Error found ");
				chargeProdRejectDao.update(chargeProdReject);
				session.setAttribute(Constants.SUCCESS_UPDATE,
						"resolution.updatedSuccessfully");
				return "redirect:/chargeproductivityreject";
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
				return "chargeProductivity/chargeProdRejectReUpdate";
			}
		} else {
			List<ObjectError> oeList = result.getAllErrors();
			for (ObjectError oe : oeList) {
				LOGGER.info("Errors:" + "field name = " + oe.getObjectName()
						+ " , value =" + oe.getDefaultMessage());
			}
			map.put("batchId", request.getParameter("batchId"));
			map.put(Constants.CHARGE_PROD_REJECT, chargeProdReject);
			return "chargeProductivity/chargeProdRejectReUpdate";
		}
	}
}
