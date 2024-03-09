package argus.mvc.productivity.workflow.paymentposting;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import argus.domain.ArProductivity;
import argus.domain.Department;
import argus.domain.Insurance;
import argus.domain.PaymentPostingWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.department.DepartmentDao;
import argus.repo.doctor.DoctorDao;
import argus.repo.file.FileDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.paymentposting.PaymentPostingWorkflowDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.PaymentPostingJsonData;
import argus.validator.PaymentPostingWorkFlowValidator;

@Controller
@RequestMapping(value = "/flows/paymentpostingworkflow")
@SessionAttributes({ Constants.PAYMENT_POSTING_WORKFLOW })
@Scope("session")
public class PaymentPostingWorkFlowController {

	private static final Logger LOGGER = Logger
			.getLogger(PaymentPostingWorkFlowController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private PaymentPostingWorkflowDao paymentPostingWorkflowDao;

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private FileDao fileDoa;

	@Autowired
	private PaymentPostingWorkFlowValidator paymentPostingWorkFlowValidator;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private DepartmentDao departmentDao;

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	private Map<String, String> printReportCriteria;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

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

	@RequestMapping(method = RequestMethod.GET)
	public String paymentPostingWorkFlowList(Model model, HttpSession session,
			WebRequest request) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			// model.addAttribute("success",
			// messageSource.getMessage(success, null, Locale.ENGLISH));
			model.addAttribute("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute("insuranceList", insuranceList);
			model.addAttribute("doctorList",
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list:");
			model.addAttribute("insuranceList", null);
			model.addAttribute("doctorList", null);
		}

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
			model.addAttribute("prodId",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
		}

		if (request.getParameter(Constants.OFFSET) != null) {
			model.addAttribute(Constants.OFFSET,
					request.getParameter(Constants.OFFSET));
		}

		if (request.getParameter(Constants.STATUS) != null) {
			model.addAttribute(Constants.STATUS,
					request.getParameter(Constants.STATUS));
		}

		try {
			Department dept = departmentDao.findById(
					Long.parseLong(Constants.AR_DEPARTMENT_ID), true);
			model.addAttribute(Constants.TEAM_LIST, dept.getDepartments());
		} catch (Exception e) {
			model.addAttribute(Constants.TEAM_LIST, null);
		}

		return "flows/arProductivity/paymentPostingWorkFlowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String paymentPostingWorkFlowLoad(WebRequest request,
			HttpSession session, Model model) {
		PaymentPostingWorkFlow paymentPostingWorkFlow = null;

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			model.addAttribute("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);

		if (request.getParameter(Constants.ID) != null
				&& !request.getParameter(Constants.ID).equals("")) {
			model.addAttribute(Constants.OPERATION_TYPE, Constants.EDIT);
			try {
				paymentPostingWorkFlow = paymentPostingWorkflowDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ID)));
				// map.put("readOnly", true);
			} catch (Exception e) {
				// no entry founf for coding correction corresponding to AR
				// productivity.
				paymentPostingWorkFlow = new PaymentPostingWorkFlow();

				try {
					paymentPostingWorkFlow
							.setArProductivity(arProductivityDao.findById(Long.parseLong(request
									.getParameter(Constants.ARPRODUCTIVITY_ID))));
					// model.addAttribute("AR_PRODUCTIVITY_ID",
					// request.getParameter(Constants.ARPRODUCTIVITY_ID));
				} catch (Exception ex) {
					LOGGER.info(Constants.EXCEPTION, ex);
				}

				// map.put("readOnly", false);
				LOGGER.info("Exception occured while fetching Payment Posting WorkFlow");
				LOGGER.info(Constants.EXCEPTION, e);
			}

			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
		} else {
			try {

				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
						&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
								.equals("")) {
					// paymentPostingWorkFlow =
					// paymentPostingWorkflowDao.findByProductivityId(Long.valueOf(request
					// .getParameter(Constants.ARPRODUCTIVITY_ID)));
					// model.addAttribute(Constants.OPERATION_TYPE,
					// Constants.EDIT);

					paymentPostingWorkFlow = new PaymentPostingWorkFlow();
					ArProductivity arProductivity = arProductivityDao
							.findById(Long.parseLong(request
									.getParameter(Constants.ARPRODUCTIVITY_ID)));
					paymentPostingWorkFlow.setArProductivity(arProductivity);
					model.addAttribute(Constants.OPERATION_TYPE, Constants.ADD);

					// set values from productivity in case of add
					paymentPostingWorkFlow
							.setDoctor(arProductivity.getDoctor());
					paymentPostingWorkFlow.setInsurance(arProductivity
							.getInsurance());
					paymentPostingWorkFlow
							.setRemark(arProductivity.getRemark());
					paymentPostingWorkFlow.setCpt(arProductivity.getCpt());
					paymentPostingWorkFlow.setBalanceAmt(arProductivity
							.getBalanceAmt());
					paymentPostingWorkFlow.setDos(arProductivity.getDos());
				}

				paymentPostingWorkFlow.setArProductivity(arProductivityDao
						.findById(Long.parseLong(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				// model.addAttribute("AR_PRODUCTIVITY_ID",
				// request.getParameter(Constants.ARPRODUCTIVITY_ID));
			} catch (Exception e) {
				// no entry founf for coding correction corresponding to AR
				// productivity.
				paymentPostingWorkFlow = new PaymentPostingWorkFlow();
				model.addAttribute(Constants.OPERATION_TYPE, Constants.ADD);
				try {
					paymentPostingWorkFlow
							.setArProductivity(arProductivityDao.findById(Long.parseLong(request
									.getParameter(Constants.ARPRODUCTIVITY_ID))));
					// model.addAttribute("AR_PRODUCTIVITY_ID",
					// request.getParameter(Constants.ARPRODUCTIVITY_ID));
				} catch (Exception ex) {
					LOGGER.info(Constants.EXCEPTION, ex);
				}

				// map.put("readOnly", false);
				LOGGER.info("Exception occured while fetching Payment Posting WorkFlow");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		populateValuesOnUI(model);

		model.addAttribute("paymentPostingWorkFlow", paymentPostingWorkFlow);

		if (null != request.getParameter(Constants.POPUP)) {
			// map.put(Constants.POPUP, true);
			return "flows/arProductivity/paymentPostingWorkFlow"
					+ Constants.POPUP;
		}

		// map.put(Constants.POPUP, false);
		return "flows/arproductivity/paymentPostingWorkFlow";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String paymentPostingWorkFlowProcess(
			@ModelAttribute("paymentPostingWorkFlow") PaymentPostingWorkFlow paymentPostingWorkFlow,
			BindingResult result, WebRequest request, Model model,
			HttpSession session) {

		if (paymentPostingWorkFlow.isOffSet()) {
			paymentPostingWorkFlow.setBilledAmount(null);
			paymentPostingWorkFlow.setPrimaryAmount(null);
			paymentPostingWorkFlow.setSecondaryAmount(null);
			paymentPostingWorkFlow.setCheckCashedDate(null);
			paymentPostingWorkFlow.setCheckIssueDate(null);
			paymentPostingWorkFlow.setContractualAdj(null);
			paymentPostingWorkFlow.setCpt(null);
			paymentPostingWorkFlow.setPatientResponse(null);
			paymentPostingWorkFlow.setCopyCancelCheck(null);
			paymentPostingWorkFlow.setAddressCheckSend(null);
			paymentPostingWorkFlow.setBulkPaymentAmount(null);
			paymentPostingWorkFlow.setCheckNo(null);
			paymentPostingWorkFlow.setEobAvailable(false);
		} else if (paymentPostingWorkFlow.isEobAvailable()) {
			paymentPostingWorkFlow.setBilledAmount(null);
			paymentPostingWorkFlow.setPrimaryAmount(null);
			paymentPostingWorkFlow.setSecondaryAmount(null);
			paymentPostingWorkFlow.setCheckCashedDate(null);
			paymentPostingWorkFlow.setCheckIssueDate(null);
			paymentPostingWorkFlow.setContractualAdj(null);
			paymentPostingWorkFlow.setCpt(null);
			paymentPostingWorkFlow.setPatientResponse(null);
			paymentPostingWorkFlow.setOffSet(false);
		} else {
			paymentPostingWorkFlow.setOffSet(false);
			paymentPostingWorkFlow.setEob(null);
			paymentPostingWorkFlow.setCopyCancelCheck(null);
		}

		String copyOfCheckDeleted = request.getParameter("copyOfCheckDeleted");
		String eobDeleted = request.getParameter("eobDeleted");
		boolean uploadEobFile = false;
		boolean deleteEobFile = false;
		boolean uploadCheckFile = false;
		boolean deleteCheckFile = false;
		
		// paymentPostingWorkFlow.setStatus(request.getParameter("operation"));

		try {
			if (paymentPostingWorkFlow.getCopyCancelCheck() != null
					&& paymentPostingWorkFlow.getCopyCancelCheck()
							.getAttachedFile().getSize() != 0) {

				// AkpmsUtil.fileUpload(
				// paymentPostingWorkFlow.getCopyCancelCheck(), fileDoa,
				// messageSource);
				uploadCheckFile = true;
			} else if (copyOfCheckDeleted != null
					&& !copyOfCheckDeleted.trim().isEmpty()
					&& copyOfCheckDeleted.equals("true")) {

				// fileDoa.deleteAttachedFile(paymentPostingWorkFlow
				// .getCopyCancelCheck().getId());
				deleteCheckFile = true;
				paymentPostingWorkFlow.setCopyCancelCheck(null);

			} else if (paymentPostingWorkFlow.getCopyCancelCheck() == null
					|| paymentPostingWorkFlow.getCopyCancelCheck().getId() == null) {
				paymentPostingWorkFlow.setCopyCancelCheck(null);
			}

			if (paymentPostingWorkFlow.getEob() != null
					&& paymentPostingWorkFlow.getEob().getAttachedFile()
							.getSize() != 0) {
				// AkpmsUtil.fileUpload(paymentPostingWorkFlow.getEob(),
				// fileDoa,
				// messageSource);
				uploadEobFile = true;
			} else if (eobDeleted != null && !eobDeleted.trim().isEmpty()
					&& eobDeleted.equals("true")) {
				// fileDoa.deleteAttachedFile(paymentPostingWorkFlow.getEob()
				// .getId());
				deleteEobFile = true;
				paymentPostingWorkFlow.setEob(null);
			} else if (paymentPostingWorkFlow.getEob() == null
					|| paymentPostingWorkFlow.getEob().getId() == null) {
				paymentPostingWorkFlow.setEob(null);
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		paymentPostingWorkFlowValidator
				.validate(paymentPostingWorkFlow, result);

		if (result.hasErrors()) {
			model.addAttribute("AR_PRODUCTIVITY_ID",
					request.getParameter(Constants.ARPRODUCTIVITY_ID));

			if (paymentPostingWorkFlow.getId() != null) {
				model.addAttribute(Constants.OPERATION_TYPE, Constants.EDIT);
			} else {
				model.addAttribute(Constants.OPERATION_TYPE, Constants.ADD);
			}

			populateValuesOnUI(model);

			if (null != request.getParameter(Constants.POPUP)) {
				return "flows/arProductivity/paymentPostingWorkFlow"
						+ Constants.POPUP;
			}

			return "flows/arproductivity/paymentPostingWorkFlow";
		}

		LOGGER.info("In Payment Posting Workflow method");

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
			LOGGER.info("push AR productivity into coding correction");
			try {
				paymentPostingWorkFlow.setArProductivity(arProductivityDao
						.findById(Long.parseLong(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				// model.addAttribute("AR_PRODUCTIVITY_ID",
				// request.getParameter(Constants.ARPRODUCTIVITY_ID));
			} catch (Exception ex) {
				LOGGER.info(Constants.EXCEPTION, ex);
			}
		}

		if (paymentPostingWorkFlow.getId() != null) {
			LOGGER.info("in edit mode");

			try {
				if (uploadCheckFile) {
					AkpmsUtil.fileUpload(
							paymentPostingWorkFlow.getCopyCancelCheck(),
							fileDoa, messageSource);
				}

				// if (deleteCheckFile) {
				// fileDoa.deleteAttachedFile(paymentPostingWorkFlow
				// .getCopyCancelCheck().getId());
				// }

				if (uploadEobFile) {
					AkpmsUtil.fileUpload(paymentPostingWorkFlow.getEob(),
							fileDoa, messageSource);
				}

				// if (deleteEobFile) {
				// fileDoa.deleteAttachedFile(paymentPostingWorkFlow.getEob()
				// .getId());
				// }
				
				paymentPostingWorkflowDao
						.updatePaymentPostingWorkFlow(paymentPostingWorkFlow);
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.paymentPostingWorkflow.updatedSuccessfully",
										null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			LOGGER.info("in add mode");
			try {
				if (paymentPostingWorkFlow.getCopyCancelCheck() != null
						&& paymentPostingWorkFlow.getCopyCancelCheck()
								.getAttachedFile().getSize() != 0) {
					AkpmsUtil.fileUpload(
							paymentPostingWorkFlow.getCopyCancelCheck(),
							fileDoa, messageSource);
				} else {
					paymentPostingWorkFlow.setCopyCancelCheck(null);
				}

				if (paymentPostingWorkFlow.getEob() != null
						&& paymentPostingWorkFlow.getEob().getAttachedFile()
								.getSize() != 0) {
					AkpmsUtil.fileUpload(paymentPostingWorkFlow.getEob(),
							fileDoa, messageSource);
				} else {
					paymentPostingWorkFlow.setEob(null);
				}
		
				paymentPostingWorkFlow.setStatus("Pending");
				paymentPostingWorkflowDao
						.addPaymentPostingWorkFlow(paymentPostingWorkFlow);
				session.setAttribute(
						Constants.SUCCESS_UPDATE,
						messageSource
								.getMessage(
										"arProductivity.paymentPostingWorkflow.addedSuccessfully",
										null, Locale.ENGLISH));
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}

		if (paymentPostingWorkFlow.isAddMore()) {
			return "redirect:/flows/paymentpostingworkflow/add?arProductivity.id="
					+ paymentPostingWorkFlow.getArProductivity().getId();
		}

		LOGGER.info("Out codingCorrectionLogWorkFlowProcess method");

		if (null != request.getParameter(Constants.POPUP)) {
			return "redirect:/" + Constants.CLOSE_POPUP;
		}

		return "redirect:/flows/paymentpostingworkflow";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<PaymentPostingJsonData> listAllCodingCorrectionLog(
			WebRequest request, @RequestParam(required = false) String arProdId) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();
		if (request != null) {
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = ArProductivityHelper.getOrderClause(request);
			whereClauses = ArProductivityHelper.getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		if (request.getParameter(Constants.AR_PROD_PATIENT_NAME) != null
				& request.getParameter(Constants.AR_PROD_PATIENT_NAME).trim()
						.length() > 0) {
			String patientName = request
					.getParameter(Constants.AR_PROD_PATIENT_NAME);
			whereClauses.put(Constants.AR_PROD_PATIENT_NAME, patientName);
		}
		if (request.getParameter(Constants.AR_PROD_PATIENT_ACC_NO) != null
				& request.getParameter(Constants.AR_PROD_PATIENT_ACC_NO).trim()
						.length() > 0) {
			String patientAccNo = request
					.getParameter("arProductivity.patientAccNo");
			whereClauses.put("arProductivity.patientAccNo", patientAccNo);
		}
		if (request.getParameter(Constants.DATE_CREATED_FROM) != null
				& request.getParameter(Constants.DATE_CREATED_FROM).trim()
						.length() > 0) {
			String dosFrom = request.getParameter(Constants.DATE_CREATED_FROM);
			whereClauses.put(Constants.DATE_CREATED_FROM, dosFrom);
		}
		if (request.getParameter(Constants.DATE_CREATED_TO) != null
				& request.getParameter(Constants.DATE_CREATED_TO).trim()
						.length() > 0) {
			String dosTo = request.getParameter(Constants.DATE_CREATED_TO);
			whereClauses.put(Constants.DATE_CREATED_TO, dosTo);
		}
		if (request.getParameter(Constants.DOCTOR_ID) != null
				&& request.getParameter(Constants.DOCTOR_ID) != null
				&& !request.getParameter(Constants.DOCTOR_ID).isEmpty()) {
			whereClauses.put(Constants.DOCTOR_ID,
					request.getParameter(Constants.DOCTOR_ID));
		}
		if (request.getParameter(Constants.INSURANCE_ID) != null
				&& request.getParameter(Constants.INSURANCE_ID) != null
				&& !request.getParameter(Constants.INSURANCE_ID).isEmpty()) {
			whereClauses.put(Constants.INSURANCE_ID,
					request.getParameter(Constants.INSURANCE_ID));
		}

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& request.getParameter(Constants.ARPRODUCTIVITY_ID).trim()
						.length() > 0) {
			whereClauses.put(Constants.ARPRODUCTIVITY_ID,
					request.getParameter(Constants.ARPRODUCTIVITY_ID));
		}

		if (request.getParameter(Constants.ISOFFSET) != null
				&& request.getParameter(Constants.ISOFFSET).equalsIgnoreCase(
						"1")) {
			whereClauses.put(Constants.ISOFFSET,
					request.getParameter(Constants.ISOFFSET));
		}

		String status[] = request.getParameterValues(Constants.STATUS);

		if (status != null && status.length > 0) {
			int counter = 0;
			String statusString = "";
			for (String stat : status) {
				if (counter == 0) {
					statusString = statusString + "'" + stat + "'";
				} else {
					statusString = statusString + ",'" + stat + "'";
				}
				counter = 1;
			}
			whereClauses.put(Constants.STATUS, statusString);
		}
		// printReportCriteria = whereClauses;

		if (arProdId != null) {
			whereClauses.put(Constants.ARPRODUCTIVITY_ID, arProdId);
		}
		printReportCriteria = whereClauses;
		try {
			int totalRows = paymentPostingWorkflowDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<PaymentPostingWorkFlow> rows = paymentPostingWorkflowDao
						.findAll(orderClauses, whereClauses, true);
				LOGGER.info("row size : " + rows.size());
				List<PaymentPostingJsonData> ccjd = getJsonData(rows);
				JsonDataWrapper<PaymentPostingJsonData> jdw = new JsonDataWrapper<PaymentPostingJsonData>(
						page, totalRows, ccjd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<PaymentPostingJsonData> getJsonData(
			List<PaymentPostingWorkFlow> rows) {
		LOGGER.info("in getJsonData");
		List<PaymentPostingJsonData> paymentPostingJsonData = new ArrayList<PaymentPostingJsonData>();

		if (rows != null && rows.size() > 0) {
			for (PaymentPostingWorkFlow eTemp : rows) {
				PaymentPostingJsonData ppwjd = new PaymentPostingJsonData();

				ppwjd.setId(eTemp.getId());
				LOGGER.info("eTemp.getArProductivity() : "
						+ eTemp.getArProductivity());
				if (null != eTemp.getArProductivity()) {
					ppwjd.setPatientAccNo(eTemp.getArProductivity()
							.getPatientAccNo());
					ppwjd.setPatientName(eTemp.getArProductivity()
							.getPatientName());
					ppwjd.setProvider(eTemp.getArProductivity().getDoctor()
							.getName());
					ppwjd.setInsurance(eTemp.getArProductivity().getInsurance()
							.getName());

					if (eTemp.getArProductivity().getTeam() != null) {
						ppwjd.setTeam(eTemp.getArProductivity().getTeam()
								.getName());
					}

					ppwjd.setCpt(eTemp.getCpt());
					ppwjd.setArProdId(eTemp.getArProductivity().getId());
				}

				ppwjd.setDos(eTemp.getDos());
				ppwjd.setBilledAmount(eTemp.getBilledAmount());
				ppwjd.setPrimaryAmount(eTemp.getPrimaryAmount());
				ppwjd.setSecondaryAmount(eTemp.getSecondaryAmount());
				ppwjd.setContractualAdj(eTemp.getContractualAdj());
				ppwjd.setBulkPaymentAmount(eTemp.getBulkPaymentAmount());
				if(eTemp.getModifiedBy() != null){
					ppwjd.setModifiedBy(eTemp.getModifiedBy().getFirstName() + " " +eTemp.getModifiedBy().getLastName());
				}
				if(eTemp.getModifiedOn() != null){
					ppwjd.setModifiedOn(eTemp.getModifiedOn().toString());
				}
				ppwjd.setPatientResponse(eTemp.getPatientResponse());
				ppwjd.setStatus(eTemp.getStatus());
				if (eTemp.getCheckCashedDate() != null) {
					ppwjd.setCheckCashedDate(AkpmsUtil.akpmsDateFormat(
							eTemp.getCheckCashedDate(), Constants.DATE_FORMAT));
				}
				if (eTemp.getCheckIssueDate() != null) {
					ppwjd.setCheckIssueDate(AkpmsUtil.akpmsDateFormat(
							eTemp.getCheckIssueDate(), Constants.DATE_FORMAT));
				}

				ppwjd.setCheckNo(eTemp.getCheckNo());

				if (eTemp.getEob() != null && eTemp.getEob().getId() != null) {
					ppwjd.setEobAttId(eTemp.getEob().getId());
					ppwjd.setEobAttName(eTemp.getEob().getName());
				}

				if (eTemp.getCopyCancelCheck() != null
						&& eTemp.getCopyCancelCheck().getId() != null) {
					ppwjd.setCopyCancelCheckAttId(eTemp.getCopyCancelCheck()
							.getId());
					ppwjd.setCopyCancelCheckAttName(eTemp.getCopyCancelCheck()
							.getName());
				}
				ppwjd.setAddressCheckSend(eTemp.getAddressCheckSend());
				paymentPostingJsonData.add(ppwjd);
			}
		}
		LOGGER.info("out getJsonData");
		return paymentPostingJsonData;
	}

	/*
	 * @RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	 * public void downloadAttachment(HttpServletRequest request,
	 * HttpServletResponse response, @RequestParam("id") Long id, HttpSession
	 * session) { LOGGER.info("In download method. Id = " + id); try { if (id !=
	 * null) { Files file = processManualDao.getAttachedFile(id);
	 *
	 * String realPath = messageSource.getMessage(
	 * "attachments.storage.space.arProductivity", null, Locale.ENGLISH).trim();
	 *
	 * File systemFile = new File(realPath, file.getSystemName()); InputStream
	 * is = new FileInputStream(systemFile);
	 *
	 * response.setHeader("Content-Disposition", "attachment; filename=\"" +
	 * file.getName() + "\""); IOUtils.copy(is, response.getOutputStream());
	 * response.flushBuffer(); } } catch (Exception e) {
	 * LOGGER.error(Constants.EXCEPTION, e); } }
	 */

	/**
	 * This function changes the status of the payment posting to approved or
	 * reject of the selected Id's in the flexi grid
	 *
	 * @param request
	 * @param response
	 * @param ids
	 * @param ticketNumber
	 * @param status
	 * @param session
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void changeStatus(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "ticketNumber", required = false) String ticketNumber,
			@RequestParam(value = "status", required = false) String status,
			HttpSession session) {
		LOGGER.info("In change status method ");
		String[] splittArray = null;
		try {

			if (ids != null && !ids.equalsIgnoreCase("")) {
				splittArray = ids.split(",");
				LOGGER.info("Split Array" + ids + " " + splittArray.length);
				paymentPostingWorkflowDao.updateStatus(ids, ticketNumber,
						status);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		try {
			List<PaymentPostingWorkFlow> rows = paymentPostingWorkflowDao
					.findAll(null, printReportCriteria, true);

			map.put("paymentPostingWorkFlowList", rows);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);

		return "paymentPostingWorkFlowListToPrint";
	}

	private void populateValuesOnUI(Model model) {
		LOGGER.debug("in populateValuesOnUI");

		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute(INSURANCE_LIST, insuranceList);
			model.addAttribute(DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.error(e);
		}

		LOGGER.debug("out populateValuesOnUI");
	}

}
