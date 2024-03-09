/**
 *
 */
package argus.mvc.productivity;

import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.ArDatabase;
import argus.domain.ArProductivity;
import argus.domain.ArProductivityWorkFlow;
import argus.domain.Department;
import argus.domain.Doctor;
import argus.domain.Insurance;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.arDatabase.ArDatabaseDao;
import argus.repo.arworkflow.ArProductivityWorkFlowDao;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.UserXstreamConverter;
import argus.validator.ArProductivityValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author vishal.joshi
 * 
 */
@Controller
@RequestMapping(value = "/flows/arProductivity")
@SessionAttributes({ Constants.ARPRODUCTIVITY, Constants.ADJUSTMENTLOG,
		Constants.CHECKTRACER })
@Scope("session")
public class ArProductivityController {

	private static final Logger LOGGER = Logger
			.getLogger(ArProductivityController.class);

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	private static final String AR_DATABASE_LIST = "arDatabaseList";
	
	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private ArDatabaseDao arDatabaseDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private ArProductivityValidator arProductivityValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ArProductivityWorkFlowDao arProductivityWorkFlowDao;

	@Autowired
	private UserDao userDao;

	private String xmlString;

	private Map<String, String> printReportCriteria;

	private static final String ERROR_403 = "redirect:error403";

	@Autowired
	private DepartmentDao departmentDao;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {

		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
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

		binder.registerCustomEditor(List.class, "workFlows",
				new CustomCollectionEditor(List.class) {
					@Override
					protected Object convertElement(Object element) {
						String workFlowId = (String) element;
						ArProductivityWorkFlow workFlow = new ArProductivityWorkFlow();
						if (workFlowId != null) {
							try {
								workFlow = arProductivityWorkFlowDao
										.findById(workFlowId);
							} catch (Exception e) {
								LOGGER.error(Constants.EXCEPTION, e);
							}
						}
						return workFlow;
					}
				});
	}

	/**
	 * function to show just list page (jsp), records will loaded with json
	 * method
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String productivityList(Map<String, Object> map, Model model,
			HttpServletRequest request, HttpSession session) {
		LOGGER.debug("in productivityList");

		if (authenticationCheck().equals(Constants.ERROR)) {
			return ERROR_403;
		}

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			model.addAttribute("success",
					messageSource.getMessage(success, null, Locale.ENGLISH));
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		populateValuesOnUI(model, request);

		boolean flagSearch = false;

		if (request != null) {
			if (request.getParameter(Constants.WORKFLOW_ID) != null) {
				model.addAttribute(Constants.WORKFLOW_ID,
						request.getParameter(Constants.WORKFLOW_ID));
				flagSearch = true;
			}

			if (request.getParameter(Constants.SUBSTATUS) != null) {
				model.addAttribute(Constants.SUBSTATUS,
						request.getParameter(Constants.SUBSTATUS));
				flagSearch = true;
			}

			if (request.getParameter(Constants.FOLLOUPS) != null) {
				model.addAttribute(Constants.FOLLOUPS,
						request.getParameter(Constants.FOLLOUPS));
				flagSearch = true;
			}
		}

		if (!flagSearch) {
			model.addAttribute(Constants.WORKFLOW_ID, Constants.ZERO);
			model.addAttribute(Constants.SUBSTATUS, "");

			map.put("createdFrom", new SimpleDateFormat(Constants.DATE_FORMAT)
					.format(AkpmsUtil.getDateBeforeNDays(Constants.ONE)));
			map.put("createdTo", new SimpleDateFormat(Constants.DATE_FORMAT)
					.format(new Date()));
			map.put("createdBy", AkpmsUtil.getLoggedInUser().getId());
		}

		return "flows/arProductivity/arProductivityList";
	}

	/**
	 * this function will populate values in dropdown from database or property
	 * files, this function will use on add/edit AR productivity and list page
	 * 
	 * @param model
	 * @param request
	 */
	private void populateValuesOnUI(Model model, HttpServletRequest request) {
		LOGGER.debug("in populateValuesOnUI");

		/* populate status code from property file */
		try {
			model.addAttribute(
					"STATUS_CODES",
					AkpmsUtil.getPropertyMap(request.getRealPath("WEB-INF"
							+ File.separator + "classes" + File.separator
							+ "arStatusCode.properties")));
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(Constants.ERROR, e);
		}

		/* populate source from property file */
		try {
			model.addAttribute(
					"SOURCES",
					AkpmsUtil.getPropertyMap(request.getRealPath("WEB-INF"
							+ File.separator + "classes" + File.separator
							+ "arSource.properties")));
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(Constants.ERROR, e);
		}

		/* populate insurance and doctor list from database */
		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<ArDatabase> arDatabaseList = arDatabaseDao.findAll(null,
					whereClause);
			
			model.addAttribute(AR_DATABASE_LIST, arDatabaseList);

			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute(INSURANCE_LIST, insuranceList);
			model.addAttribute(DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));

			whereClause.put(Constants.DEPARTMENT_WITH_CHILD,
					Constants.AR_DEPARTMENT_ID);
			whereClause.put(Constants.SELECTED_ROLES_IDS,
					Constants.STANDART_USER_ROLE_ID.toString());
			List<User> userList = userDao.findAll(null, whereClause);
			model.addAttribute(Constants.USER_LIST, userList);
			Department dept = departmentDao.findById(
					Long.parseLong(Constants.AR_DEPARTMENT_ID), true);
			model.addAttribute(Constants.TEAM_LIST, dept.getDepartments());

		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list:");
			model.addAttribute(INSURANCE_LIST, null);
			model.addAttribute(DOCTOR_LIST, null);
		}

		model.addAttribute("ArWorkFlows",
				arProductivityWorkFlowDao.findAll(null));

		LOGGER.debug("out populateValuesOnUI");
	}

	/**
	 * funciton to show add productivity page to user in add or edit mode
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @param map
	 * @param successUpdate
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityLoad(Model model, HttpServletRequest request,
			HttpSession session, Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [productivityLoad] method");

		if (authenticationCheck().equals(Constants.ERROR)) {
			return ERROR_403;
		}

		populateValuesOnUI(model, request);

		if (null != request & null != request.getParameter(Constants.ID)) {
			ArProductivity arProductivity = null;
			try {
				arProductivity = arProductivityDao.findById(Long
						.parseLong(request.getParameter(Constants.ID)));
				if (arProductivity == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/flows/arProductivity";
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			model.addAttribute(Constants.ARPRODUCTIVITY, arProductivity);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);

			try {
				map.put("workFLowCount", arProductivityDao
						.getWorkFlowCountByArId(arProductivity.getId()));
			} catch (Exception e) {
				LOGGER.error(e);
			}

		} else {
			model.addAttribute(Constants.ARPRODUCTIVITY, new ArProductivity());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			// map.put(Constants.READ_ONLY, false);
		}

		model.addAttribute("ArWorkFlows",
				arProductivityWorkFlowDao.findAll(null));

		return "flows/arProductivity/arProductivity";
	}

	/**
	 * this function will used when user click on the submit button on add/edit
	 * page then data will save in database
	 * 
	 * @param arProductivity
	 * @param result
	 * @param model
	 * @param request
	 * @param map
	 * @param session
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProductivity(
			@Valid @ModelAttribute(Constants.ARPRODUCTIVITY) ArProductivity arProductivity,
			BindingResult result, Model model, HttpServletRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivity] method");

		String workFlow = null;

		arProductivityValidator.validate(arProductivity, result);
		if (!result.hasErrors()) {

			if (arProductivity.getTeam() == null
					|| arProductivity.getTeam().getId() == null) {
				arProductivity.setTeam(null);
			}

			if (arProductivity.getId() != null) {
				try {
					arProductivityDao.updateProductivity(arProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.updatedSuccessfully");
					// workFlow = ArProductivityHelper.getFlow(arProductivity
					// .getWorkFlow())
					// + "?arProductivity.id="
					// + arProductivity.getId();
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");
					// return workFlow;
					return "redirect:/flows/arProductivity";
				}

			} else {
				try {
					arProductivityDao.addProductivity(arProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.addedSuccessfully");
					/*
					 * workFlow = ArProductivityHelper.getFlow(
					 * arProductivity.getWorkFlow(), arProductivity.getId());
					 */
				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return "redirect:/flows/arProductivity";

		} else {
			populateValuesOnUI(model, request);

			if (arProductivity != null & arProductivity.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				try {
					map.put("workFLowCount", arProductivityDao
							.getWorkFlowCountByArId(arProductivity.getId()));
				} catch (Exception e) {
					LOGGER.error(e);
				}
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}

			return "flows/arProductivity/arProductivity";
		}
	}

	/**
	 * function to use for flexigrid list
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ArProductivityJsonData> listAllArProductivity(
			WebRequest request) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = ArProductivityHelper.getOrderClause(request);
			whereClauses = ArProductivityHelper.getWhereClause(request);
			printReportCriteria = whereClauses;
		} else {
			LOGGER.info("request object is coming null");
		}
		printReportCriteria = whereClauses;
		try {
			int totalRows = arProductivityDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<ArProductivity> rows = arProductivityDao.findAll(
						orderClauses, whereClauses, true);
				List<ArProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<ArProductivityJsonData> jdw = new JsonDataWrapper<ArProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<ArProductivityJsonData> getJsonData(List<ArProductivity> rows) {
		List<ArProductivityJsonData> arProdJsonData = new ArrayList<ArProductivityJsonData>();

		if (rows != null && rows.size() > 0) {
			for (ArProductivity eTemp : rows) {
				ArProductivityJsonData djd = new ArProductivityJsonData();
				StringBuilder workFlows = new StringBuilder();
				for (ArProductivityWorkFlow arProductivityWorkFlow : eTemp
						.getWorkFlows()) {
					workFlows.append(arProductivityWorkFlow.getId() + ",");
				}
				djd.setWorkFlows(workFlows.toString());
				djd.setId(eTemp.getId());
				djd.setInsurance(eTemp.getInsurance().getName());
				djd.setBalanceAmt(eTemp.getBalanceAmt());
				djd.setDoctor(eTemp.getDoctor().getName());
				djd.setCpt(eTemp.getCpt());
				djd.setDos(eTemp.getDos());
				djd.setRemark(eTemp.getRemark());
				djd.setTlRemark(eTemp.getTlRemark());
				djd.setPatientAccNo(eTemp.getPatientAccNo());
				djd.setPatientName(eTemp.getPatientName());
				djd.setSource(eTemp.getSource());
				djd.setStatusCode(eTemp.getStatusCode());
				djd.setWorkflowId(eTemp.getWorkFlow());
				djd.setWorkFlowName(eTemp.getWorkFlowName());
				djd.setDataBas(eTemp.getArDatabase().getName());
				
				if(eTemp.getProductivityType()==1){
					djd.setProductivityType("HD");	
				}else if(eTemp.getProductivityType()==2){
					djd.setProductivityType("Non HD");
				}
				
				if (eTemp.getTeam() != null) {
					djd.setTeam(eTemp.getTeam().getName());
				}

				djd.setCreatedBy(eTemp.getCreatedBy().getFirstName() + " "
						+ eTemp.getCreatedBy().getLastName());
				djd.setCreatedOn(new SimpleDateFormat(Constants.DATE_FORMAT)
						.format(eTemp.getCreatedOn()));

				if (eTemp.getFollowUpDate() != null) {
					djd.setFollowUpDate(new SimpleDateFormat(
							Constants.DATE_FORMAT).format(eTemp
							.getFollowUpDate()));
				}
				// djd.setDataBas(eTemp.getDataBas());
				arProdJsonData.add(djd);
			}
		}
		return arProdJsonData;
	}

	/**
	 * function to use for flexigrid list
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getWFDetails", method = RequestMethod.GET,
	 * produces = "application/json")
	 * 
	 * @ResponseBody public Map<String, String> getWFDetails( WebRequest
	 * request) { LOGGER.info("in [getWFDetails]json method"); // Map<String,
	 * String> whereClauses = new HashMap<String, String>();
	 * 
	 * try {
	 * 
	 * if (request != null) { int workflowId = Integer.parseInt(request
	 * .getParameter(Constants.WORKFLOW_ID)); int arProductivityId =
	 * Integer.parseInt(request .getParameter(Constants.PRODUCTIVITY_ID));
	 * 
	 * ArProductivityDao } else { LOGGER.info("request object is coming null");
	 * }
	 * 
	 * } catch (Exception e) { LOGGER.info(Constants.EXCEPTION, e); } return
	 * null; }
	 */

	@RequestMapping(value = "/queryToTL/add", method = RequestMethod.GET)
	public String getQueryToTl(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session) {

		if (authenticationCheck().equals(Constants.ERROR)) {
			return ERROR_403;
		}

		// int arProductivityId = 0;
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {

			/*
			 * arProductivityId = Integer.valueOf(request
			 * .getParameter(Constants.ARPRODUCTIVITY_ID));
			 */

			try {

				map.put(Constants.ARPRODUCTIVITY, arProductivityDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));

				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);

				// session.setAttribute("initialStatus",
				// adjLogWorkFlow.getWorkFlowStatus());

				// map.put(Constants.READ_ONLY, true);

			} catch (ArgusException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (NoResultException ex) {
				LOGGER.error(ex.getMessage(), ex);
				map.put(Constants.ARPRODUCTIVITY, new ArProductivity());
				model.addAttribute(Constants.MODE, Constants.ADD);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
		} else {
			map.put(Constants.ARPRODUCTIVITY, new ArProductivity());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
		}

		return "flows/arproductivity/querytotl";

	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/queryToTL/add", method = RequestMethod.POST)
	public String saveQueryToTl(
			@Valid @ModelAttribute(Constants.ARPRODUCTIVITY) ArProductivity arProductivity,
			Model model, WebRequest request, Map<String, Object> map,
			HttpSession session) {
		LOGGER.info("in [save queryToTL] method");

		try {
			if (arProductivity != null) {
				LOGGER.info("going to update arProductivity by query to TL");
				arProductivityDao.updateProductivity(arProductivity);
				if (arProductivity.getId() != null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.updatedSuccessfully");
				} else {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.addedSuccessfully");
				}
			} else {
				LOGGER.error(" arProductivity object is coming null");
				return "flows/arproductivity/querytotl";
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		} finally {
			LOGGER.info("FINALLY ");
			return "redirect:/flows/arProductivity";
		}
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String getPrint(Map<String, String> map) {

		try {
			List<ArProductivity> rows = arProductivityDao.findAll(null,
					printReportCriteria, true);
			if (rows != null && !rows.isEmpty()) {
				xmlString = getXmlData(rows);
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xmlString + "<empty>No Record found.</empty>";
			}
			// LOGGER.info("XML = " + xmlString);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		map.put("title", "AR Productivity List");
		map.put("path", "/flows/arProductivity/print_report");
		return "chargeBatchProcessingPrintReport";
	}

	private String getXmlData(List<ArProductivity> arProductivityList)
			throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(ArProductivity.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		return xstream.toXML(arProductivityList);
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
							"ARProductivityStylesheet.xsl");
					InputStream is = new FileInputStream(systemFile);
					char[] cbuf = new char[is.available()];
					BufferedReader bReader = new BufferedReader(
							new InputStreamReader(is));
					bReader.read(cbuf);
					String xslData = String.valueOf(cbuf);
					LOGGER.info("got xsl");
					bReader.close();
					return xslData;
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					return "";
				}
			}
		}
		return "";
	}

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		List<ArProductivity> rows = null;
		try {
			rows = arProductivityDao.findAll(null, printReportCriteria, true);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		map.put("arProductivityList", rows);
		return "arProductivityListToPrint";
	}

	private String authenticationCheck() {
		if (!AkpmsUtil
				.checkDepartment(Long.valueOf(Constants.AR_DEPARTMENT_ID))
				|| (AkpmsUtil.getLoggedInUser().getRole().getId() != Constants.STANDART_USER_ROLE_ID)) {
			return Constants.ERROR;
		} else {
			return "";
		}
	}
	@RequestMapping(value = "/getProviders", method = RequestMethod.GET)
	@ResponseBody
	public List<Doctor> getDoctorByDatabaseId(Long databaseId){
		List<Doctor> doctorList = null;
		if(databaseId!=null){
			doctorList =  arProductivityDao.fetchDoctorsByDatabase(databaseId);
		}
		return doctorList;
	}
	
}



