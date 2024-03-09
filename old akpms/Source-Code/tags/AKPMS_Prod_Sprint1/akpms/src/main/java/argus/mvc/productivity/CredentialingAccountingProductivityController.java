/**
 *
 */
package argus.mvc.productivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import argus.domain.paymentproductivity.CredentialingAccountingProductivity;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.paymentproductivity.CredentialingAccountingProductivityDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.CredentialingAccountingProductivityJsonData;
import argus.util.JsonDataWrapper;
import argus.validator.CredentialingAccountingProductivityValidator;

/**
 * @author rajiv.k
 *
 */
@Controller
@RequestMapping(value = "/credentialingaccountingproductivity")
@SessionAttributes({ Constants.CREDENTIALING_ACCOUNTING })
public class CredentialingAccountingProductivityController {
	private static final Log LOGGER = LogFactory
			.getLog(CredentialingAccountingProductivityController.class);

	@Autowired
	private CredentialingAccountingProductivityDao credentialingAccountingDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CredentialingAccountingProductivityValidator credentialValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
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

	/**
	 * This function just load jsp "Payment Productivity List", the list will
	 * load using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String credentialingAccountingList(Map<String, Object> map,
			WebRequest request,
			HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success",
					messageSource.getMessage(success, null, Locale.ENGLISH)
							.trim());
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {

			map.put("postedBy", userDao.getCredentialingAccountingDeptUser());

		} catch (ArgusException ar) {
			LOGGER.error(Constants.EXCEPTION, ar);
		}

		return "credentialingAccountingList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String credentialingAccountingLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session) {
		LOGGER.info("in [credentialingAccountingLoad] method");

		if (null != request & null != request.getParameter(Constants.ID)) {
			CredentialingAccountingProductivity credentialingAccountingProductivity = null;
			try {

				credentialingAccountingProductivity = credentialingAccountingDao
						.findById(Long.parseLong(request
								.getParameter(Constants.ID)));
				if (credentialingAccountingProductivity == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/paymentproductivitynonera";
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			model.addAttribute("credentialingAccounting",
					credentialingAccountingProductivity);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);

		} else {
			CredentialingAccountingProductivity credentialingAccountingProductivity = new CredentialingAccountingProductivity();
			model.addAttribute("credentialingAccounting",
					credentialingAccountingProductivity);
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.ADD);


		}

		return "credentialingAccountingAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveCredentialingAccounting(
			@Valid @ModelAttribute(Constants.CREDENTIALING_ACCOUNTING) CredentialingAccountingProductivity credentialingAccountingProductivity,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveCredentialingAccounting] method : ");
		credentialValidator.validate(credentialingAccountingProductivity,
				result);
		if (!result.hasErrors()) {
			LOGGER.info(" :: NO ERROR :: ");
			if (credentialingAccountingProductivity.getId() != null) {
				try {
					credentialingAccountingDao
							.updateCredentialingAccountingProductivity(credentialingAccountingProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.updatedSuccessfully");
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");

				}

			} else {
				try {
					credentialingAccountingDao
							.addCredentialingAccountingProductivity(credentialingAccountingProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.addedSuccessfully");
				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return "redirect:/credentialingaccountingproductivity";

		} else {
			LOGGER.info(" :: ERROR :: ");
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError objectError : list) {
				LOGGER.info(" :: ERROR :: " + objectError.getDefaultMessage());
			}
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.ADD);
			}

		return "credentialingAccountingAdd";
		}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<CredentialingAccountingProductivityJsonData> listAllPayment(
			WebRequest request) {
		LOGGER.info("payment productivity::in json method");

		int page = Constants.ONE; /* default 1 for page number in json wrapper */
		int rp = Constants.ZERO;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

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
					page = Integer.parseInt(request
							.getParameter(Constants.PAGE));
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
			whereClauses = getWhereClause(request);

		} else {
			LOGGER.info("request object is coming null");
		}

		int totalRows = 0;
		try {
			totalRows = credentialingAccountingDao.totalRecord(whereClauses);
			if (totalRows > Constants.ZERO) {
				List<CredentialingAccountingProductivity> rows = credentialingAccountingDao
						.findAll(orderClauses, whereClauses, true);
				List<CredentialingAccountingProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<CredentialingAccountingProductivityJsonData> jdw = new JsonDataWrapper<CredentialingAccountingProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<CredentialingAccountingProductivityJsonData> getJsonData(
			List<CredentialingAccountingProductivity> rows) {
		List<CredentialingAccountingProductivityJsonData> deptJsonData = new ArrayList<CredentialingAccountingProductivityJsonData>();

		if (rows != null && rows.size() > Constants.ZERO) {

			for (CredentialingAccountingProductivity paymentType : rows) {
				CredentialingAccountingProductivityJsonData djd = new CredentialingAccountingProductivityJsonData();
				djd.setId(paymentType.getId());
				djd.setDateRecd(AkpmsUtil.akpmsDateFormat(
						paymentType.getDateRecd(), Constants.DATE_FORMAT));
				djd.setTaskCompleted(AkpmsUtil.akpmsDateFormat(
						paymentType.getTaskCompleted(), Constants.DATE_FORMAT));
				djd.setCredentialingTask(paymentType.getCredentialingTask());
				djd.setTime(paymentType.getTime());
				djd.setRemark(paymentType.getRemark());
				djd.setPostedBy(paymentType.getCreatedBy().getFirstName() + " "
						+ paymentType.getCreatedBy().getLastName());
				deptJsonData.add(djd);
			}

		}

		return deptJsonData;
	}

	@RequestMapping(value = "/printreport", method = RequestMethod.GET)
	public String printReport(WebRequest request, Map<String, Object> map) {

		Map<String, String> whereClauses = new HashMap<String, String>();
		whereClauses = getWhereClause(request);
		try {

			List<CredentialingAccountingProductivity> rows = credentialingAccountingDao
					.findAll(null, whereClauses, true);
			map.put("rows", rows);

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return "credentialAccProdReport";

	}

	private Map<String, String> getWhereClause(WebRequest request) {
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request.getParameter(Constants.QTYPE) != null
				&& request.getParameter(Constants.QTYPE) != null
				&& !request.getParameter(Constants.QTYPE).isEmpty()) {
			whereClauses.put(request.getParameter(Constants.QTYPE),
					request.getParameter(Constants.QTYPE));
		}

		if (request.getParameter(Constants.POSTED_BY_ID) != null
				&& request.getParameter(Constants.POSTED_BY_ID).trim().length() > 0) {
			String postedBy = request.getParameter(Constants.POSTED_BY_ID);
			whereClauses.put(Constants.POSTED_BY_ID, postedBy);
		}
		// set Date Recd from.
		if (request.getParameter(Constants.DATE_POSTED_FROM) != null
				&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
						.length() > 0) {
			LOGGER.info("DATE_POSTED_FROM= "
					+ request.getParameter(Constants.DATE_POSTED_FROM));
			whereClauses.put(Constants.DATE_POSTED_FROM,
					request.getParameter(Constants.DATE_POSTED_FROM));
		}

		// set Date Recd to
		if (request.getParameter(Constants.DATE_POSTED_TO) != null
				&& request.getParameter(Constants.DATE_POSTED_TO).trim()
						.length() > 0) {
			LOGGER.info("DATE_POSTED_TO= "
					+ request.getParameter(Constants.DATE_POSTED_TO));
			whereClauses.put(Constants.DATE_POSTED_TO,
					request.getParameter(Constants.DATE_POSTED_TO));
		}

		// set Task completed from date
		if (request.getParameter(Constants.DATE_RECEIVED_FROM) != null
				&& request.getParameter(Constants.DATE_RECEIVED_FROM).trim()
						.length() > 0) {
			LOGGER.info("DATE_RECEIVED_FROM= "
					+ request.getParameter(Constants.DATE_RECEIVED_FROM));
			whereClauses.put(Constants.DATE_RECEIVED_FROM,
					request.getParameter(Constants.DATE_RECEIVED_FROM));
		}

		// set Task completed to date
		if (request.getParameter(Constants.DATE_RECEIVED_TO) != null
				&& request.getParameter(Constants.DATE_RECEIVED_TO).trim()
						.length() > 0) {
			LOGGER.info("DATE_RECEIVED_TO= "
					+ request.getParameter(Constants.DATE_RECEIVED_TO));
			whereClauses.put(Constants.DATE_RECEIVED_TO,
					request.getParameter(Constants.DATE_RECEIVED_TO));
		}
		return whereClauses;
	}

	}



