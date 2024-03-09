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

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import argus.domain.ChargeProductivity;
import argus.domain.User;
import argus.repo.chargeBatchProcessing.ChargeBatchProcessingDao;
import argus.repo.chargeProductivity.ChargeProdRejectDao;
import argus.repo.chargeProductivity.ChargeProductivityDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.ChargeProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.ChargeProductivityValidator;

@Controller
@RequestMapping(value = "/chargeproductivity")
@SessionAttributes(value = Constants.CHARGE_PRODUCTIVITY)
public class ChargeProductivityController {

	private static final Log LOGGER = LogFactory
			.getLog(ChargeProductivityController.class);

	private static final String USER_LIST = "userList";

	@Autowired
	private ChargeBatchProcessingDao chargeBatchProcessingDao;

	@Autowired
	private ChargeProductivityDao chargeProductivityDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ChargeProductivityValidator chargeProductivityValidator;

	@Resource(name = "chargeBatchTypeProperties")
	private Properties chargeBatchtype;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ChargeProdRejectDao chargeProdRejectDao;

	private int page = Constants.ONE;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		/*
		 * "permissions" is the name of the property that we want to register a
		 * custom editor to you can set property name null and it means you want
		 * to register this editor for occurrences of List class in * command
		 * object
		 */
		binder.registerCustomEditor(Long.class, "ticketNumber.id",
				new PropertyEditorSupport() {
					@Override
					public void setAsText(String value) {
						try {
							if (value != null
									&& value.trim().length() > Constants.ZERO) {
								LOGGER.info("coming ticket number is : "
										+ value);
								setValue((Long.parseLong(value)));
							} else {
								LOGGER.info("ticket number is: " + value);
								setValue(null);
							}
						} catch (Exception e) {
							LOGGER.error(Constants.EXCEPTION, e);
							setValue(null);
						}
					}

					@Override
					public String getAsText() {
						if (getValue() != null) {
							return getValue().toString();
						}
						return "";
					}

				});
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					if (value != null && value.trim().length() > Constants.ZERO) {
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

			@Override
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

	@RequestMapping(method = RequestMethod.GET)
	public String getProductivity(WebRequest request, Map<String, Object> map) {
		if (authenticationCheck().equals(Constants.ERROR)) {
			return "redirect:error403";
		}

		ChargeProductivity chargeProductivity = null;
		if (request != null
				&& request.getParameter(Constants.PRODUCTIVITY_ID) != null) {
			try {
				chargeProductivity = chargeProductivityDao
						.getChargeProductivityById(Long.valueOf(request
								.getParameter(Constants.PRODUCTIVITY_ID)));
				chargeProductivity.getTicketNumber().setTempBatchType(
						chargeBatchtype.getProperty(chargeProductivity
								.getTicketNumber().getType()));
				if (chargeProductivity.getWorkFlow().equalsIgnoreCase("reject")) {
					if (chargeProdRejectDao.hasRejectionDone(chargeProductivity
							.getTicketNumber().getId()) > 0) {
						map.put("rejection", "1");
					}
				}
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.HIDE, Constants.HIDE);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
		} else {
			chargeProductivity = new ChargeProductivity();
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.HIDE, "");
		}

		map.put(Constants.CHARGE_PRODUCTIVITY, chargeProductivity);
		return Constants.CHARGE_PRODUCTIVITY;
	}

	@RequestMapping(value = "/json", method = RequestMethod.POST)
	@ResponseBody
	public Object[] getDetail(@RequestParam(Constants.BATCH_ID) String batchId,
			WebRequest request) {
		Object[] obj = null;
		try {
			if (batchId != null && !batchId.trim().isEmpty()) {
				long id = Long.parseLong(batchId);

				LOGGER.info("coming Batch id = " + id);

				ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
						.getChargeBatchProcessingShortDetailById(id);
				if (chargeBatchProcessing.getPostedBy() != null) {
					LOGGER.info("Already Posted Batch");
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = Constants.ERR;
					obj[Constants.ONE] = messageSource.getMessage(
							"chargeProductivity.ticket.alreadyPosted", null,
							Locale.ENGLISH);
					return obj;
				} else if (chargeBatchProcessing.getReceviedBy() == null) {
					LOGGER.info("Batch Have not received yet");
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = Constants.ERR;
					obj[Constants.ONE] = messageSource.getMessage(
							"chargeProductivity.ticket.notReceived", null,
							Locale.ENGLISH);
					return obj;
				} else {
					obj = new Object[Constants.FIVE];
					obj[Constants.ZERO] = chargeBatchProcessing.getId();
					obj[Constants.ONE] = new SimpleDateFormat(
							Constants.DATE_FORMAT)
							.format(chargeBatchProcessing.getDosFrom());
					obj[Constants.TWO] = new SimpleDateFormat(
							Constants.DATE_FORMAT)
							.format(chargeBatchProcessing.getDosTo());

					obj[Constants.THREE] = chargeBatchtype
							.getProperty(chargeBatchProcessing
							.getType());

					LOGGER.info("Doctor name = "
							+ chargeBatchProcessing.getDoctor().getName());
					obj[Constants.FOUR] = chargeBatchProcessing.getDoctor()
							.getName();
				}
				return obj;
			} else {
				obj = new Object[Constants.TWO];
				obj[Constants.ZERO] = Constants.ERR;
				obj[Constants.ONE] = messageSource.getMessage(
						"chargeProductivity.ticket.missing", null,
						Locale.ENGLISH);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			LOGGER.info("going to send error message on exception");
			obj = new Object[Constants.TWO];
			obj[Constants.ZERO] = Constants.ERR;
			obj[Constants.ONE] = messageSource.getMessage(
					"chargeProductivity.ticket.noBatch", null, Locale.ENGLISH);
		}
		return obj;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveProductivity(
			@ModelAttribute(Constants.CHARGE_PRODUCTIVITY) ChargeProductivity chargeProductivity,
			BindingResult result, Map<String, Object> map, Model model,
			WebRequest request, HttpSession session) {
		try {
			chargeProductivityValidator.validate(chargeProductivity, result);
			if (!result.hasErrors()) {
				Object[] ticketNumber = new Object[1];
				if (chargeProductivity.getId() != null) {
					LOGGER.info("going to update");
					if (chargeProductivity.getProductivityType()
							.equalsIgnoreCase("Charge Entry")) {
						chargeProductivity.setDemoAndVerification(null);
						chargeProductivity.setDemoReviewAndVerification(null);
						chargeProductivity.setDemoReviewOnly(null);
						chargeProductivity
								.setProductivityUnitsAccountAndCodes(null);
					} else if (chargeProductivity.getProductivityType()
							.equalsIgnoreCase("Demo")) {
						chargeProductivity
								.setProductivityUnitsAccountAndCodes(null);
					} else if (chargeProductivity.getProductivityType()
							.equalsIgnoreCase("Coding")) {
						chargeProductivity.setDemoAndVerification(null);
						chargeProductivity.setDemoReviewAndVerification(null);
						chargeProductivity.setDemoReviewOnly(null);
					}

					chargeProductivityDao
							.updateProductivity(chargeProductivity);

					LOGGER.info("going to update  charge batch process");
					ChargeBatchProcessing chargeBatchProcessing = chargeProductivity
							.getTicketNumber();
					LOGGER.info("chargeProductivity.getTicketNumber().getDateBatchPosted()"
							+ chargeProductivity.getTicketNumber()
									.getDateBatchPosted());
					// chargeBatchProcessing.setDateBatchPosted(chargeProductivity
					// .getTicketNumber().getDateBatchPosted());
					chargeBatchProcessing.setPostedBy(AkpmsUtil
							.getLoggedInUser());
					chargeBatchProcessing.setPostedOn(new Date());
					chargeBatchProcessingDao.update(chargeBatchProcessing);

					ticketNumber[0] = chargeProductivity.getTicketNumber()
							.getId();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"chargeBatchProductivity.updatedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());

					return "redirect:/chargeproductivity/list";
				} else {
					LOGGER.info("going to save charge productivity");
					ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
							.getChargeBatchProcessingById(chargeProductivity
									.getTicketNumber().getId(), false);

					// chargeProductivity.setTicketNumber(chargeBatchProcessingDao
					// .getChargeBatchProcessingById(chargeProductivity
					// .getTicketNumber().getId(), false));
					chargeProductivityDao.saveProductivity(chargeProductivity);

					LOGGER.info("going to update  charge batch process");
					// ChargeBatchProcessing chargeBatchProcessing =
					// chargeProductivity
					// .getTicketNumber();
					LOGGER.info("chargeProductivity.getTicketNumber().getDateBatchPosted()"
							+ chargeProductivity.getTicketNumber()
									.getDateBatchPosted());
					chargeBatchProcessing.setDateBatchPosted(chargeProductivity
							.getTicketNumber().getDateBatchPosted());
					chargeBatchProcessing.setPostedBy(AkpmsUtil
							.getLoggedInUser());
					chargeBatchProcessing.setPostedOn(new Date());
					chargeBatchProcessingDao.update(chargeBatchProcessing);

					String workFlow = chargeProductivity.getWorkFlow();
					if (workFlow == null || workFlow.length() < Constants.ONE) {
						LOGGER.info("work flow coming null or empty");
					} else {
						LOGGER.info(" found work flow ");
						if (workFlow
								.equalsIgnoreCase(Constants.CHARGE_BATCH_WORK_FLOW_REJECT)) {
							LOGGER.info("going for Reject work flow and id  = "
									+ chargeProductivity.getId());
							return "redirect:/chargeproductivityreject/add?"
									+ Constants.BATCH_ID + "="
									+ chargeBatchProcessing.getId();
						}
					}
					ticketNumber[0] = chargeProductivity.getTicketNumber()
							.getId();
					session.setAttribute(Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"chargeBatchProductivity.addedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
					return "redirect:/chargeproductivity/list";
				}
			} else {
				List<ObjectError> oeList = result.getAllErrors();
				for (ObjectError oe : oeList) {
					LOGGER.info("Errors:" + oe.getDefaultMessage());
				}
				if (chargeProductivity.getId() != null) {
					if (chargeProductivity.getWorkFlow().equalsIgnoreCase(
							"reject")) {
						if (chargeProdRejectDao
								.hasRejectionDone(chargeProductivity
										.getTicketNumber().getId()) > 0) {
							map.put("rejection", "1");
						}
					}
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					map.put(Constants.HIDE, Constants.HIDE);
				} else {
					try {
						ChargeBatchProcessing chargeBatchProcessing = chargeBatchProcessingDao
								.getChargeBatchProcessingById(
										chargeProductivity.getTicketNumber()
												.getId(), false);
						chargeBatchProcessing.setTempBatchType(chargeBatchtype
								.getProperty(chargeBatchProcessing.getType()));
						chargeProductivity
								.setTicketNumber(chargeBatchProcessing);
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
					}
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					map.put(Constants.HIDE, "");
				}
				model.addAttribute(Constants.CHARGE_PRODUCTIVITY,
						chargeProductivity);
				model.addAttribute("validationError", "yes");
				return Constants.CHARGE_PRODUCTIVITY;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
			model.addAttribute(Constants.CHARGE_PRODUCTIVITY,
					chargeProductivity);
			return Constants.CHARGE_PRODUCTIVITY;
		}

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getProductivityList(WebRequest request,
			Map<String, Object> map, HttpSession session) {
		if (authenticationCheck().equals("error")) {
			return "redirect:error403";
		}
		if (session.getAttribute(Constants.SUCCESS_UPDATE) != null) {
			String success = (String) session
					.getAttribute(Constants.SUCCESS_UPDATE);
			if (success != null) {
				map.put("success", success);
			}
			session.removeAttribute(Constants.SUCCESS_UPDATE);
		}

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
					Constants.CODING_CHARGE_POSTING);
			whereClause.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			List<User> userList = userDao.findAll(null, whereClause);

			map.put(USER_LIST, userList);
		} catch (Exception e) {
			LOGGER.info("EXception while getting USER_LIST list");
			LOGGER.info(e.getMessage());
			map.put(USER_LIST, null);
		}

		if (request.getParameter(Constants.WORKFLOW) != null) {
			LOGGER.info("workflow = "
					+ request.getParameter(Constants.WORKFLOW));
			map.put(Constants.WORKFLOW,
					request.getParameter(Constants.WORKFLOW));
		}

		return "chargeProductivityList";
	}

	@RequestMapping(value = "/unhold", method = RequestMethod.GET)
	public String unHoldProductivity(WebRequest request, Map<String, Object> map) {
		if (authenticationCheck().equals("error")) {
			return "redirect:error403";
		}
		ChargeProductivity chargeProductivity = null;
		if (request != null
				&& request.getParameter(Constants.PRODUCTIVITY_ID) != null) {
			try {
				chargeProductivity = chargeProductivityDao
						.getChargeProductivityById(Long.valueOf(request
								.getParameter(Constants.PRODUCTIVITY_ID)));

				String readOnly = "readOnly";
				if (request.getParameter("canUnhold") != null
						&& !Boolean.parseBoolean(request
								.getParameter("canUnhold"))) {
					map.put("readOnly", readOnly);
				}
				if (chargeProductivity.getUnholdRemarks() != null) {
					User onHoldBy = userDao.findById(chargeProductivity
							.getOnHoldBy().getId(), false);
					map.put("onHoldBy", onHoldBy);
				}
				map.put(Constants.PRODUCTIVITY_ID,
						request.getParameter(Constants.PRODUCTIVITY_ID));
				map.put(Constants.CHARGE_PRODUCTIVITY, chargeProductivity);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
		}

		return "unHoldAdd";
	}

	@RequestMapping(value = "/unhold", method = RequestMethod.POST)
	public String unHoldProductivity(
			@Valid @ModelAttribute(Constants.CHARGE_PRODUCTIVITY) ChargeProductivity chargeProductivity,
			BindingResult result, Map<String, Object> map, Model model,
			WebRequest request) {

		ChargeProductivity chargeProductivityUnhold = null;
		chargeProductivityValidator.validate(chargeProductivity, result);
		if (!result.hasErrors()) {
			if (request != null
					&& request.getParameter(Constants.PRODUCTIVITY_ID) != null) {
                 try{
					chargeProductivityUnhold = chargeProductivityDao
							.getChargeProductivityById(Long.valueOf(request
									.getParameter(Constants.PRODUCTIVITY_ID)));
					chargeProductivityUnhold
							.setUnholdRemarks(chargeProductivity
									.getUnholdRemarks());
					chargeProductivityUnhold.setOnHoldOn(new Date());
					chargeProductivityUnhold.setOnHoldBy(AkpmsUtil
							.getLoggedInUser());
				chargeProductivityDao
						.updateProductivity(chargeProductivityUnhold);
			} catch (Exception e) {
                	LOGGER.error(Constants.EXCEPTION, e);
                }
			}
			return "chargeProductivityList";

		} else {
			return "unHoldAdd";
		}
	}


	@RequestMapping(value = "list/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ChargeProductivityJsonData> listAllChargeProductivities(
			WebRequest request) {
		LOGGER.info("in json method");
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			orderClauses = getOrderClause(request);
			whereClauses = getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		try {
			int totalRows = chargeProductivityDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<ChargeProductivity> rows = chargeProductivityDao.findAll(
						orderClauses, whereClauses, true);

				List<ChargeProductivityJsonData> djd = getJsonData(rows);
				LOGGER.info("received json data list");
				JsonDataWrapper<ChargeProductivityJsonData> jdw = new JsonDataWrapper<ChargeProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<ChargeProductivityJsonData> getJsonData(
			List<ChargeProductivity> chargeProductivityList) {
		LOGGER.info("IN [getJsonData] method");
		List<ChargeProductivityJsonData> chargeProductivityJsonDataList = new ArrayList<ChargeProductivityJsonData>();
		if (chargeProductivityList != null && !chargeProductivityList.isEmpty()) {
			for (ChargeProductivity chargeProductivity : chargeProductivityList) {
				ChargeProductivityJsonData chargeProductivityJsonData = new ChargeProductivityJsonData();
				chargeProductivityJsonData.setProductivityId(chargeProductivity
						.getId());
				chargeProductivityJsonData
						.setNumberOfTransactions(chargeProductivity
								.getNumberOfTransactions());
				chargeProductivityJsonData
						.setProductivityType(chargeProductivity
								.getProductivityType());
				chargeProductivityJsonData.setTicketNumber(chargeProductivity
						.getTicketNumber().getId());
				chargeProductivityJsonData
						.setTime(chargeProductivity.getTime());
				chargeProductivityJsonData.setWorkFlow(chargeProductivity
						.getWorkFlow());
				if (chargeProductivity.getUnholdRemarks() != null
						&& chargeProductivity.getUnholdRemarks().trim()
								.length() != Constants.ZERO) {
					chargeProductivityJsonData
							.setUnholdRemarks(chargeProductivity
									.getUnholdRemarks());
				}
				if (chargeProductivity.getCreatedOn() != null) {
					chargeProductivityJsonData.setCreatedOn(AkpmsUtil
							.akpmsDateFormat(chargeProductivity.getCreatedOn(),
									Constants.DATE_FORMAT));
				}
				chargeProductivityJsonDataList.add(chargeProductivityJsonData);
			}
		}
		return chargeProductivityJsonDataList;
	}

	private Map<String, String> getOrderClause(WebRequest request) {
		Map<String, String> orderClauses = new HashMap<String, String>();

		int rp = Constants.ZERO;

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
		LOGGER.info("in where clause");
		try {
			// set created by id
			if (request.getParameter(Constants.CREATED_BY) != null
					&& request.getParameter(Constants.CREATED_BY).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("CREATED_BY = "
						+ request.getParameter(Constants.CREATED_BY));
				whereClauses.put(Constants.CREATED_BY,
						request.getParameter(Constants.CREATED_BY));
			}

			// set created from date
			if (request.getParameter(Constants.DATE_CREATED_FROM) != null
					&& request.getParameter(Constants.DATE_CREATED_FROM).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_CREATED_FROM = "
						+ request.getParameter(Constants.DATE_CREATED_FROM));
				whereClauses.put(Constants.DATE_CREATED_FROM,
						request.getParameter(Constants.DATE_CREATED_FROM));
			}

			// set posted to date
			if (request.getParameter(Constants.DATE_CREATED_TO) != null
					&& request.getParameter(Constants.DATE_CREATED_TO).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("DATE_CREATED_TO = "
						+ request.getParameter(Constants.DATE_CREATED_TO));
				whereClauses.put(Constants.DATE_CREATED_TO,
						request.getParameter(Constants.DATE_CREATED_TO));
			}

			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("TICKET_NUMBER = "
						+ request.getParameter(Constants.TICKET_NUMBER));
				whereClauses.put(Constants.TICKET_NUMBER,
						request.getParameter(Constants.TICKET_NUMBER));
			}

			if (request.getParameter(Constants.PROD_TYPE) != null
					&& request.getParameter(Constants.PROD_TYPE).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("PROD_TYPE = "
						+ request.getParameter(Constants.PROD_TYPE));
				whereClauses.put(Constants.PROD_TYPE,
						request.getParameter(Constants.PROD_TYPE));
			}

			if (request.getParameter(Constants.WORKFLOW) != null
					&& request.getParameter(Constants.WORKFLOW).trim().length() > Constants.ZERO) {
				LOGGER.info("WORKFLOW = "
						+ request.getParameter(Constants.WORKFLOW));
				whereClauses.put(Constants.WORKFLOW,
						request.getParameter(Constants.WORKFLOW));
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return whereClauses;
	}
}
