package argus.mvc.chargeBatchProcessing;

import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.ChargeBatchProcessing;
import argus.domain.Doctor;
import argus.domain.User;
import argus.repo.chargeBatchProcessing.ChargeBatchProcessingDao;
import argus.repo.chargeProductivity.ChargeProdRejectDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.ChargeBatchProcessingJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.UserXstreamConverter;
import argus.validator.ChargeBatchProcessingValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@RequestMapping(value = "/chargebatchprocess")
@SessionAttributes({ Constants.CHARGE_BATCH_PROCESS })
public class ChargeBatchProcessingController {

	private static final Log LOGGER = LogFactory
			.getLog(ChargeBatchProcessingController.class);

	@Autowired
	private ChargeBatchProcessingDao chargeBatchProcessingDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private ChargeBatchProcessingValidator chargeBatchProcessingValidator;

	@Autowired
	private ChargeProdRejectDao chargeProdRejectDao;

	@Resource(name = "chargeBatchTypeProperties")
	private Properties chargeBatchType;

	@Resource(name = "chargeBatchReportTypeProperties")
	private Properties chargeBatchReportType;

	private int page = 1;

	private String xmlString;

	private ChargeBatchProcessing chargeBatchProcessing;

	private static final String ERROR_403 = "redirect:error403";

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

		/*
		 * "permissions" is the name of the property that we want to register a
		 * custom editor to you can set property name null and it means you want
		 * to register this editor for occurrences of List class in * command
		 * object
		 */
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

	private Map<String, String> getWhereClause(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter("qtype") != null
				&& request.getParameter("query") != null
				&& !request.getParameter("query").isEmpty()) {
			whereClauses.put(request.getParameter("qtype"),
					request.getParameter("query"));
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

		if (request.getParameter(Constants.MONTH) != null
				&& Integer.parseInt(request.getParameter(Constants.MONTH)) > 0) {
			LOGGER.info("Have found month = "
					+ request.getParameter(Constants.MONTH));
			whereClauses.put(Constants.MONTH,
					request.getParameter(Constants.MONTH));
		}

		if (request.getParameter(Constants.YEAR) != null
				&& Integer.parseInt(request.getParameter(Constants.YEAR)) > 0) {
			LOGGER.info("Have found year = "
					+ request.getParameter(Constants.YEAR));
			whereClauses.put(Constants.YEAR,
					request.getParameter(Constants.YEAR));
		}

		if (request.getParameter(Constants.DOCTOR) != null
				&& Long.valueOf(request.getParameter(Constants.DOCTOR)) > 0) {
			whereClauses.put(Constants.DOCTOR,
					request.getParameter(Constants.DOCTOR));
			Doctor doctor = new Doctor();
			doctor.setId(Long.valueOf(request.getParameter(Constants.DOCTOR)));
			chargeBatchProcessing.setDoctor(doctor);
		}

		if (request.getParameter(Constants.RECIEVED_BY) != null
				&& Integer
						.parseInt(request.getParameter(Constants.RECIEVED_BY)) > 0) {
			whereClauses.put(Constants.RECIEVED_BY,
					request.getParameter(Constants.RECIEVED_BY));
			User user = new User();
			user.setId(Long.valueOf(request.getParameter(Constants.RECIEVED_BY)));
			chargeBatchProcessing.setReceviedBy(user);
		}

		if (request.getParameter(Constants.POSTED_BY) != null
				&& Integer.parseInt(request.getParameter(Constants.POSTED_BY)) > 0) {
			whereClauses.put(Constants.POSTED_BY,
					request.getParameter(Constants.POSTED_BY));
			User user = new User();
			user.setId(Long.valueOf(request.getParameter(Constants.POSTED_BY)));
			chargeBatchProcessing.setPostedBy(user);
		}

		if (request.getParameter(Constants.DOS_FROM) != null
				&& request.getParameter(Constants.DOS_FROM).trim().length() > 0) {
			whereClauses.put(Constants.DOS_FROM,
					request.getParameter(Constants.DOS_FROM));
			chargeBatchProcessing.setDosFrom(AkpmsUtil
					.akpmsNewDateFormat(request
							.getParameter(Constants.DOS_FROM)));
		}

		if (request.getParameter(Constants.DOS_TO) != null
				&& request.getParameter(Constants.DOS_TO).trim().length() > 0) {
			whereClauses.put(Constants.DOS_TO,
					request.getParameter(Constants.DOS_TO));
			chargeBatchProcessing.setDosTo(AkpmsUtil.akpmsNewDateFormat(request
					.getParameter(Constants.DOS_TO)));
		}

		if (request.getParameter(Constants.DATE_RECEIVED_FROM) != null
				&& request.getParameter(Constants.DATE_RECEIVED_FROM).trim()
						.length() > 0) {
			whereClauses.put(Constants.DATE_RECEIVED_FROM,
					request.getParameter(Constants.DATE_RECEIVED_FROM));
			chargeBatchProcessing.setDateReceivedFrom(AkpmsUtil
					.akpmsNewDateFormat(request
							.getParameter(Constants.DATE_RECEIVED_FROM)));
		}

		if (request.getParameter(Constants.DATE_RECEIVED_TO) != null
				&& request.getParameter(Constants.DATE_RECEIVED_TO).trim()
						.length() > 0) {
			whereClauses.put(Constants.DATE_RECEIVED_TO,
					request.getParameter(Constants.DATE_RECEIVED_TO));
			chargeBatchProcessing.setDateReceivedTo(AkpmsUtil
					.akpmsNewDateFormat(request
							.getParameter(Constants.DATE_RECEIVED_TO)));
		}

		if (request.getParameter(Constants.DATE_BATCH_POSTED_FROM) != null
				&& request.getParameter(Constants.DATE_BATCH_POSTED_FROM)
						.trim().length() > 0) {
			whereClauses.put(Constants.DATE_BATCH_POSTED_FROM,
					request.getParameter(Constants.DATE_BATCH_POSTED_FROM));
			chargeBatchProcessing.setDateBatchPostedFrom(AkpmsUtil
					.akpmsNewDateFormat(request
							.getParameter(Constants.DATE_BATCH_POSTED_FROM)));
		}

		if (request.getParameter(Constants.DATE_BATCH_POSTED_TO) != null
				&& request.getParameter(Constants.DATE_BATCH_POSTED_TO).trim()
						.length() > 0) {
			whereClauses.put(Constants.DATE_BATCH_POSTED_TO,
					request.getParameter(Constants.DATE_BATCH_POSTED_TO));
			chargeBatchProcessing.setDateBatchPostedTo(AkpmsUtil
					.akpmsNewDateFormat(request
							.getParameter(Constants.DATE_BATCH_POSTED_TO)));
		}

		String reportType[] = request
				.getParameterValues(Constants.CHARGE_BATCH_REPORT_TYPE);

		if (reportType != null && reportType.length > 0) {
			String reportTypes = "";
			int i = 0;
			for (String st : reportType) {
				if (i == 0) {
					reportTypes = st;
				} else {
					reportTypes = reportTypes + "," + st;
				}
				i++;
			}
			LOGGER.info("reportTypes = " + reportTypes);
			if (!reportTypes.isEmpty()) {
				whereClauses.put(Constants.CHARGE_BATCH_REPORT_TYPE,
						reportTypes);
			}
		}
		LOGGER.info("reportType = " + reportType);
		return whereClauses;
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

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ChargeBatchProcessingJsonData> listAllChargeBatchProcessing(
			WebRequest request) {
		LOGGER.info("in json method");
		chargeBatchProcessing = new ChargeBatchProcessing();
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();
		try {
			if (request != null) {
				page = Integer.parseInt(request.getParameter(Constants.PAGE));
				orderClauses = getOrderClause(request);
				whereClauses = getWhereClause(request);
			} else {
				LOGGER.info("request object is coming null");
			}

			int totalRows = chargeBatchProcessingDao.totalRecord(whereClauses,
					chargeBatchProcessing);
			if (totalRows > 0) {
				List<ChargeBatchProcessing> rows = chargeBatchProcessingDao
						.findAllForReport(chargeBatchProcessing, whereClauses,
								orderClauses);
				if (rows != null && !rows.isEmpty()) {
					for (ChargeBatchProcessing chargeBatchProcess : rows) {
						chargeBatchProcess.setTypeValue(chargeBatchType
								.getProperty(chargeBatchProcess.getType()));
					}
				}
				List<ChargeBatchProcessingJsonData> djd = getJsonData(rows);
				JsonDataWrapper<ChargeBatchProcessingJsonData> jdw = new JsonDataWrapper<ChargeBatchProcessingJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return null;
	}

	/**
	 *
	 * function used to get only relative and required data from User Objects.
	 *
	 * @param rows
	 * @return
	 */
	private List<ChargeBatchProcessingJsonData> getJsonData(
			List<ChargeBatchProcessing> rows) {
		List<ChargeBatchProcessingJsonData> chargeBatchProcessingJsonData = new ArrayList<ChargeBatchProcessingJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {
			for (ChargeBatchProcessing chargeBatchProcess : rows) {
				ChargeBatchProcessingJsonData cbp = new ChargeBatchProcessingJsonData();
				cbp.setId(chargeBatchProcess.getId());
				cbp.setType(chargeBatchProcess.getTypeValue());
				cbp.setDoctorName(chargeBatchProcess.getDoctor().getName());
				cbp.setDosFrom(AkpmsUtil.akpmsDateFormat(
						chargeBatchProcess.getDosFrom(), Constants.DATE_FORMAT));
				cbp.setDosTo(AkpmsUtil.akpmsDateFormat(
						chargeBatchProcess.getDosTo(), Constants.DATE_FORMAT));
				cbp.setNumberOfSuperbills(chargeBatchProcess
						.getNumberOfSuperbills());
				cbp.setNumberOfAttachments(chargeBatchProcess
						.getNumberOfAttachments());
				if (chargeBatchProcess.getNumberOfAttachmentsArgus() != null) {
					cbp.setNumberOfAttachmentsArgus(chargeBatchProcess
							.getNumberOfAttachmentsArgus());
				} else {
					cbp.setNumberOfAttachmentsArgus(Constants.ZERO);
				}
				if (chargeBatchProcess.getNumberOfSuperbillsArgus() != null) {
					cbp.setNumberOfSuperbillsArgus(chargeBatchProcess
							.getNumberOfSuperbillsArgus());
				} else {
					cbp.setNumberOfSuperbillsArgus(Constants.ZERO);
				}
				if (chargeBatchProcess.getDateReceived() != null) {
					cbp.setDateReceived(AkpmsUtil.akpmsDateFormat(
							chargeBatchProcess.getDateReceived(),
							Constants.DATE_FORMAT));
				}
				if (chargeBatchProcess.getReceviedBy() != null) {
					cbp.setReceivedBy(chargeBatchProcess.getReceviedBy()
							.getFirstName()
							+ " "
							+ chargeBatchProcess.getReceviedBy().getLastName());
				} else {
					cbp.setReceivedBy("");
				}

				if (chargeBatchProcess.getDateBatchPosted() != null) {
					cbp.setDateBatchPosted(AkpmsUtil.akpmsDateFormat(
							chargeBatchProcess.getDateBatchPosted(),
							Constants.DATE_FORMAT));
				}

				if (chargeBatchProcess.getPostedOn() != null) {
					cbp.setPostedOn(AkpmsUtil.akpmsDateFormat(
							chargeBatchProcess.getPostedOn(),
							Constants.DATE_FORMAT));
				}
				if (chargeBatchProcess.getPostedBy() != null) {
					cbp.setPostedBy(chargeBatchProcess.getPostedBy()
							.getFirstName()
							+ " "
							+ chargeBatchProcess.getPostedBy().getLastName());
				} else {
					cbp.setPostedBy("");
				}

				chargeBatchProcessingJsonData.add(cbp);

			}

		}
		return chargeBatchProcessingJsonData;
	}

	public Map<String, String> getChargeBatchUserOnly() {
		Map<String, String> whereClause = new HashMap<String, String>();
		whereClause.put(Constants.DEPARTMENT_WITH_CHILD, Constants.STRING_ONE);
		whereClause.put(Constants.SELECTED_ROLES_IDS,
				Constants.STANDART_USER_ROLE_ID.toString());
		whereClause.put(Constants.STATUS, Constants.STRING_ONE);
		return whereClause;
	}

	public Map<String, String> getParentDoctorOnly() {
		Map<String, String> whereClause = new HashMap<String, String>();
		whereClause.put(Constants.PARENT_ONLY, null);
		whereClause.put(Constants.STATUS, Constants.STRING_ONE);
		return whereClause;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getChargeProcessingList(Map<String, Object> map,
			WebRequest request, HttpSession session) {
		LOGGER.info("Going for list page");
		if (authenticationCheck().equals(Constants.ERROR)) {
			return ERROR_403;
		}
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		try {
			List<User> userList = userDao.findAll(null,
					getChargeBatchUserOnly());
			List<Doctor> doctorList = doctorDao.findAll(null,
					getParentDoctorOnly(), false);
			map.put(Constants.RECEIVED_BY_USERS, userList);
			map.put("postedByUsers", userList);
			map.put(Constants.DOCTOR_LIST, doctorList);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		map.put(Constants.MONTHS, AkpmsUtil.getMonths());
		map.put(Constants.YEARS, AkpmsUtil.getYears());
		map.put("reportTypeList", chargeBatchReportType);
		return "ChargeBatchSystemList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String chargeBatchProcessingAdd(WebRequest request, Model model,
			Map<String, Object> map) {

		try {
			if (authenticationCheck().equals(Constants.ERROR)) {
				return ERROR_403;
			}
			if (request.getParameter(Constants.ID) != null) {
				// It is coming for editing
				Long id = Long.parseLong(request.getParameter(Constants.ID));
				ChargeBatchProcessing chargeBatchProcess = chargeBatchProcessingDao
						.getChargeBatchProcessingById(id, true);
				chargeBatchProcess.setTypeValue(chargeBatchType
						.getProperty(chargeBatchProcess.getType()));

				chargeBatchProcess
						.setDateFrom(AkpmsUtil.akpmsDateFormat(
								chargeBatchProcess.getDosFrom(),
								Constants.DATE_FORMAT));
				chargeBatchProcess.setDateTo(AkpmsUtil.akpmsDateFormat(
						chargeBatchProcess.getDosTo(), Constants.DATE_FORMAT));
				model.addAttribute(Constants.CHARGE_BATCH_PROCESS,
						chargeBatchProcess);
				if (request.getParameter(Constants.OPERATION_TYPE) != null) {
					LOGGER.info("have found operation type");
					try {
						if (Long.parseLong(request
								.getParameter(Constants.OPERATION_TYPE)) == Constants.ZERO) {
							LOGGER.info("operation type is Add");
							map.put("operationMode", Constants.EDIT);
							map.put(Constants.OPERATION_TYPE, Constants.ADD);
							List<Doctor> doctorList = doctorDao.findAll(null,
									getParentDoctorOnly(), false);
							model.addAttribute(Constants.DOCTOR_LIST,
									doctorList);
							model.addAttribute("typeProperty", chargeBatchType);
							chargeBatchProcess.setEditDoctorSection(true);
						} else {
							LOGGER.info("operation type is edit");
							map.put("operationMode", Constants.EDIT);
							map.put(Constants.OPERATION_TYPE, Constants.EDIT);
							getChargeRejectionDetail(map, id);
						}
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
						map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					}
				} else {
					map.put("operationMode", Constants.EDIT);
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					getChargeRejectionDetail(map, id);
				}
			} else {
				// First time Add
				List<Doctor> doctorList = doctorDao.findAll(null,
						getParentDoctorOnly(), false);

				model.addAttribute(Constants.CHARGE_BATCH_PROCESS,
						new ChargeBatchProcessing());
				model.addAttribute(Constants.DOCTOR_LIST, doctorList);
				model.addAttribute("typeProperty", chargeBatchType);

				map.put("operationMode", Constants.ADD);
				map.put(Constants.OPERATION_TYPE, "Add");

			}

			List<User> userList = userDao.findAll(null,
					getChargeBatchUserOnly());
			model.addAttribute(Constants.RECEIVED_BY_USERS, userList);
			model.addAttribute("postedByUsers", userList);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "chargeBatchSystemAdd";
	}

	private void getChargeRejectionDetail(Map<String, Object> map, Long batchId) {
		try {

			map.put(Constants.FIRST_REQUEST_DUE_COUNT,
					chargeProdRejectDao.getFirstRequestDueCount(batchId));

			map.put(Constants.SECOND_REQUEST_DUE_COUNT,
					chargeProdRejectDao.getSecondRequestDueCount(batchId));

			map.put(Constants.NUMBER_OF_FIRST_REQUESTS_COUNT,
					chargeProdRejectDao.getNumberOfFirstRequestsCount(batchId));

			map.put(Constants.NUMBER_OF_SECOND_REQUESTS_COUNT,
					chargeProdRejectDao.getNumberOfSecondRequestsCount(batchId));

			map.put(Constants.REJECTION_COUNT,
					chargeProdRejectDao.getRejectionCount(batchId));

			map.put(Constants.RESOLVED_REJECTION_COUNT,
					chargeProdRejectDao.getResolvedRejectionCount(batchId));

			map.put(Constants.RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT,
					chargeProdRejectDao.getResolvedWithDummyCPTCount(batchId));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String chargeBatchProcessingAdd(
			HttpServletRequest servletRequest,
			@Valid @ModelAttribute(Constants.CHARGE_BATCH_PROCESS) ChargeBatchProcessing chargeBatchProcessing,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		chargeBatchProcessingValidator.validate(chargeBatchProcessing, result);

		if (!result.hasErrors()) {
			Object[] ticketNumber = new Object[1];
			if (chargeBatchProcessing.getId() == null) {
				/* It is for save */
				try {
					LOGGER.info("going for save");
					chargeBatchProcessing.setReceviedBy(null);
					chargeBatchProcessingDao.save(chargeBatchProcessing);
					ticketNumber[0] = chargeBatchProcessing.getId();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"chargeBatchProcessingTicket.addedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/chargebatchprocess";
				}
			} else {
				try {

					/* It is for Update */
					if (null != request.getParameter(Constants.MODE)) {
						LOGGER.info("going for update mode="
								+ request.getParameter(Constants.MODE));
						if (request.getParameter(Constants.MODE)
								.equalsIgnoreCase("update")) {
							if (chargeBatchProcessing.getReceviedBy() != null) {
								User user = userDao.findById(
										chargeBatchProcessing.getReceviedBy()
												.getId(), false);
								LOGGER.info("coming for update received by");
								chargeBatchProcessing.setReceviedBy(user);
							}
						}

						if (request.getParameter(Constants.MODE)
								.equalsIgnoreCase("reupdate")) {
							if (chargeBatchProcessing.getPostedBy() != null
									&& chargeBatchProcessing.getPostedOn() != null) {
								LOGGER.info("coming for re-modify");
								LOGGER.info("coming for update posted by");
								LOGGER.info("Got logged in postedBy id = "
										+ chargeBatchProcessing.getPostedBy()
												.getId());
								LOGGER.info("Got logged in receivedBy id = "
										+ chargeBatchProcessing.getReceviedBy()
												.getId());

								User receivedByUser = userDao.findById(Long
										.parseLong(request
												.getParameter("receivedById")),
										false);
								chargeBatchProcessing
										.setReceviedBy(receivedByUser);
								User postedByUser = userDao.findById(
										chargeBatchProcessing.getPostedBy()
												.getId(), false);
								chargeBatchProcessing.setPostedBy(postedByUser);
							}
						}
					}
					chargeBatchProcessingDao.update(chargeBatchProcessing);
					ticketNumber[0] = chargeBatchProcessing.getId();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"chargeBatchProcessingTicket.updatedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				return "redirect:/chargebatchprocess";
			}

		} else {
			List<ObjectError> oeList = result.getAllErrors();
			for (ObjectError oe : oeList) {
				LOGGER.info("Errors:" + oe.getDefaultMessage());
			}
			List<Doctor> doctorList = null;
			try {
				doctorList = doctorDao.findAll(null, getParentDoctorOnly(),
						false);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			try {
				List<User> userList = userDao.findAll(null,
						getChargeBatchUserOnly());
				model.addAttribute(Constants.RECEIVED_BY_USERS, userList);
				model.addAttribute("postedByUsers", userList);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}

			model.addAttribute(Constants.DOCTOR_LIST, doctorList);
			model.addAttribute("typeProperty", chargeBatchType);

			if (chargeBatchProcessing != null
					&& chargeBatchProcessing.getId() != null) {
				if (request.getParameter(Constants.OPERATION_TYPE) != null) {
					try {
						if (Long.parseLong(request
								.getParameter(Constants.OPERATION_TYPE)) == 0) {
							map.put("operationMode", Constants.EDIT);
							map.put(Constants.OPERATION_TYPE, Constants.ADD);
						} else {
							map.put("operationMode", Constants.EDIT);
							map.put(Constants.OPERATION_TYPE, Constants.EDIT);
						}
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
						map.put(Constants.OPERATION_TYPE, Constants.EDIT);
					}
				} else {
					map.put("operationMode", Constants.EDIT);
					map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				}
				getChargeRejectionDetail(map, chargeBatchProcessing.getId());
			} else {
				map.put("operationMode", Constants.ADD);
				map.put(Constants.OPERATION_TYPE, "Add");
			}

			return "chargeBatchSystemAdd";
		}
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getChargeBatchProcessingPrint(Model model) {
		try {
			if (authenticationCheck().equals(Constants.ERROR)) {
				return ERROR_403;
			}
			model.addAttribute(Constants.CHARGE_BATCH_PROCESS,
					new ChargeBatchProcessing());
			populatePropertiesForPrintBatchPage(model);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "chargeBatchSystemPrint";
	}

	private void populatePropertiesForPrintBatchPage(Model model) {

		try {
			List<Doctor> doctorList = null;
			try {
				doctorList = doctorDao.findAll(null, getParentDoctorOnly(),
						false);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			model.addAttribute(Constants.DOCTOR_LIST, doctorList);

			List<User> userList = userDao.findAll(null,
					getChargeBatchUserOnly());

			model.addAttribute(Constants.RECEIVED_BY_USERS, userList);
			model.addAttribute(Constants.MONTHS, AkpmsUtil.getMonths());
			model.addAttribute(Constants.YEARS, AkpmsUtil.getYears());
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/print", method = RequestMethod.POST)
	public String getChargeBatchProcessingPrint(
			@ModelAttribute(Constants.CHARGE_BATCH_PROCESS) ChargeBatchProcessing chargeBatchProcessing,
			BindingResult result, Model model, WebRequest request) {

		if (!result.hasErrors()) {
			if (chargeBatchProcessing != null) {

				Map<String, String> whereClause = new HashMap<String, String>();
				String paramTicketFrom = request.getParameter("ticketFrom");
				String paramTicketTo = request.getParameter("ticketTo");
				if (paramTicketFrom != null && !paramTicketFrom.isEmpty()) {
					LOGGER.info("Ticket from = " + paramTicketFrom);
					whereClause
							.put(Constants.CHARGE_BATCH_PROCESSING_TICKET_RANGE_FROM,
									paramTicketFrom);
				}

				if (paramTicketTo != null && !paramTicketTo.isEmpty()) {
					LOGGER.info("Ticket to = " + paramTicketTo);
					whereClause.put(
							Constants.CHARGE_BATCH_PROCESSING_TICKET_RANGE_TO,
							paramTicketTo);
				}

				if (request.getParameter(Constants.MONTH) != null
						&& Integer.parseInt(request
								.getParameter(Constants.MONTH)) > 0) {
					LOGGER.info("Have found month = "
							+ request.getParameter(Constants.MONTH));
					whereClause.put(Constants.MONTH,
							request.getParameter(Constants.MONTH));
				}

				if (request.getParameter(Constants.YEAR) != null
						&& Integer.parseInt(request
								.getParameter(Constants.YEAR)) > 0) {
					LOGGER.info("Have found year = "
							+ request.getParameter(Constants.YEAR));
					whereClause.put(Constants.YEAR,
							request.getParameter(Constants.YEAR));
				}

				List<ChargeBatchProcessing> chargeBatchProcessingList = null;
				try {
					chargeBatchProcessingList = chargeBatchProcessingDao
							.findAllForReport(chargeBatchProcessing,
									whereClause, null);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				if (chargeBatchProcessingList != null
						&& !chargeBatchProcessingList.isEmpty()) {
					for (ChargeBatchProcessing chargeBatchProcess : chargeBatchProcessingList) {
						chargeBatchProcess.setType(chargeBatchType
								.getProperty(chargeBatchProcess.getType()));

						if (chargeBatchProcess.getNumberOfSuperbills() == null) {
							chargeBatchProcess.setNumberOfSuperbills(0);
						}
						if (chargeBatchProcess.getNumberOfSuperbillsArgus() == null) {
							chargeBatchProcess.setNumberOfSuperbillsArgus(0);
						}
						if (chargeBatchProcess.getNumberOfAttachments() == null) {
							chargeBatchProcess.setNumberOfAttachments(0);
						}
						if (chargeBatchProcess.getNumberOfAttachmentsArgus() == null) {
							chargeBatchProcess.setNumberOfAttachmentsArgus(0);
						}
						int superBillDifference = chargeBatchProcess
								.getNumberOfSuperbills()
								- chargeBatchProcess
										.getNumberOfSuperbillsArgus();
						int attachmentDifference = chargeBatchProcess
								.getNumberOfAttachments()
								- chargeBatchProcess
										.getNumberOfAttachmentsArgus();
						chargeBatchProcess
								.setSuperBillDifference(superBillDifference);
						chargeBatchProcess
								.setAttachmentDifference(attachmentDifference);

					}
					LOGGER.info("List size = "
							+ chargeBatchProcessingList.size());

					try {
						xmlString = getXmlData(chargeBatchProcessingList);
					} catch (Exception e) {
						LOGGER.error(Constants.EXCEPTION, e);
					}
				} else {
					String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
					xmlString = xml + "<empty>No Record found.</empty>";
				}
			} else {
				LOGGER.info("chargeBatchProcessing object is coming null");
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xml = xml + "<empty>No Record found.</empty>";
				return "chargeBatchProcessingPrintReport";
			}

			model.addAttribute("path", "/chargebatchprocess/print_report");
			model.addAttribute("title", "Charge Batch Report");
			return "chargeBatchProcessingPrintReport";
		} else {
			List<ObjectError> oeList = result.getAllErrors();
			for (ObjectError oe : oeList) {
				LOGGER.info("Errors:" + oe.getDefaultMessage());
			}
			try {
				model.addAttribute(Constants.CHARGE_BATCH_PROCESS,
						chargeBatchProcessing);
				populatePropertiesForPrintBatchPage(model);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			return "chargeBatchSystemPrint";
		}
	}

	public String getXmlData(
			List<ChargeBatchProcessing> chargeBatchProcessingList) {

		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(ChargeBatchProcessing.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
		xml = xml + xstream.toXML(chargeBatchProcessingList);
		return xml;
	}

	@RequestMapping(value = "/print_report", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String generateXML(WebRequest request, HttpSession session) {
		LOGGER.info("IN /print_report");
		if (request != null) {
			if (request.getParameter(Constants.PARAM) != null
					&& request.getParameter(Constants.PARAM).equals("xml")) {
				LOGGER.info("got xml");
				return xmlString;
			} else if (request.getParameter(Constants.PARAM) != null
					&& request.getParameter(Constants.PARAM).equals("xsl")) {
				try {
					ServletContext context = session.getServletContext();
					String realPath = context.getRealPath("/resources/xsl");
					LOGGER.info("XSL real path = " + realPath);
					File systemFile = new File(realPath,
							"ChargeBatchStylesheet.xsl");
					InputStream is = new FileInputStream(systemFile);
					char[] cbuf = new char[is.available()];
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(is));
					bReader.read(cbuf);
					String xslData = String.valueOf(cbuf);
					LOGGER.info("got xsl");
					return xslData;
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					return "";
				}
			}
		}
		return "";
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
}
