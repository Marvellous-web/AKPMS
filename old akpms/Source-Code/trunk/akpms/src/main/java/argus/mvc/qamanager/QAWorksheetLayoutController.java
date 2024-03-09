package argus.mvc.qamanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import argus.domain.QAProductivitySampling;
import argus.domain.QAWorksheet;
import argus.domain.QAWorksheetPatientInfo;
import argus.domain.QAWorksheetStaff;
import argus.domain.QCPointChecklist;
import argus.domain.QcPoint;
import argus.domain.User;
import argus.exception.ArgusException;
import argus.repo.qamanager.QAProductivitySamplingDao;
import argus.repo.qamanager.QAWorksheetDao;
import argus.repo.qamanager.QAWorksheetPatientInfoDao;
import argus.repo.qamanager.QAWorksheetStaffDao;
import argus.repo.qamanager.QCPointChecklistDao;
import argus.repo.qcpoint.QcPointDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;

@Controller
@RequestMapping(value = "/qaworksheetlayout")
public class QAWorksheetLayoutController {

	private static Logger LOGGER = Logger
			.getLogger(QAWorksheetLayoutController.class);

	@Autowired
	private QAWorksheetStaffDao qaWorksheetStaffDao;

	@Autowired
	private QcPointDao qcPointDao;

	@Autowired
	private QAProductivitySamplingDao qaProductivitySamplingDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private QAWorksheetPatientInfoDao qaWorksheetPatientInfoDao;

	@Autowired
	private QCPointChecklistDao qcPointChecklistDao;

	@Autowired
	private QAWorksheetDao qaWorksheetDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping(method = RequestMethod.GET, value = "/payment")
	public String paymentLayout(Map<String, Object> map, HttpSession session,
			WebRequest request, Model model,
			@RequestParam(value = "qaworksheetid") Long qaWorksheetId) {
		LOGGER.info("in payment layout");

		LOGGER.info("QA Worksheet Id: " + qaWorksheetId);

		QAWorksheet qaWorksheet = null;

		try {
			qaWorksheet = qaWorksheetDao.findById(qaWorksheetId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String orderBy = null;

		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		model.addAttribute("ORDER_BY", orderBy);

		try {
			fetchQCPoints(Constants.PAYMENT_DEPARTMENT_ID, null, model);

			List<QAProductivitySampling> qaProductivitiesSampleList = fetchAllProductivitySamplingData(
					Constants.PAYMENT_DEPARTMENT_ID, qaWorksheetId, orderBy);

			if (qaWorksheet != null
					&& Constants.PAYMENT_DEPARTMENT_ID.equals(qaWorksheet
							.getDepartment().getId().toString())
					&& qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.no.record",
								new Object[] { "payment productivity" },
								Locale.ENGLISH));

			} else if (qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.error", new Object[] {
								qaWorksheetId, "payment productivity" },
								Locale.ENGLISH));

			} else {

				model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
						qaProductivitiesSampleList);
				// model.addAttribute("QA_PATIENT_INFO", listAllPatientInfo());
			}

		} catch (EntityNotFoundException efe) {
			model.addAttribute(Constants.ERROR, this.messageSource.getMessage(
					"qalayout.no.record", new Object[] { "ar productivity" },
					Locale.ENGLISH));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		model.addAttribute("QA_WORKSHEET", qaWorksheet);
		map.put("mode", "");

		return "paymentLayout";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/coding")
	public String codingLayout(Map<String, Object> map, HttpSession session,
			WebRequest request, Model model,
			@RequestParam(value = "qaworksheetid") Long qaWorksheetId) {
		LOGGER.info("in coding layout");

		QAWorksheet qaWorksheet = null;

		try {
			qaWorksheet = qaWorksheetDao.findById(qaWorksheetId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String orderBy = null;

		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		model.addAttribute("ORDER_BY", orderBy);

		try {
			if (null == qaWorksheet.getSubDepartment()) {
				fetchQCPoints(Constants.CHARGE_DEPARTMENT_ID, null, model);
			} else {
				if (qaWorksheet.getSubDepartment().getId() != null) {
					fetchQCPoints(Constants.CHARGE_DEPARTMENT_ID, qaWorksheet
							.getSubDepartment().getId().toString(), model);
				} else {
					fetchQCPoints(Constants.CHARGE_DEPARTMENT_ID, null, model);
				}
			}

			List<QAProductivitySampling> qaProductivitiesSampleList = fetchAllProductivitySamplingData(
					Constants.CHARGE_DEPARTMENT_ID, qaWorksheetId, orderBy);
			
			List<QAWorksheetStaff> qaWorksheetStaffs = qaWorksheet.getQaWorksheetStaffs();

			if(!qaProductivitiesSampleList.isEmpty()){
				for(QAProductivitySampling qaSample : qaProductivitiesSampleList ){
					if(qaSample.getChargeProductivity()!=null){
						for(QAWorksheetStaff qaStaff : qaWorksheetStaffs){
							if(qaStaff.getUser().getId().equals(qaSample.getChargeProductivity().getCreatedBy().getId())){
								qaSample.setPercentage(qaStaff.getPercentageValue());
							}
						}
					}
				}
			}

			if (qaWorksheet != null
					&& Constants.CHARGE_DEPARTMENT_ID.equals(qaWorksheet
							.getDepartment().getId().toString())
					&& qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {
				
				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.no.record",
								new Object[] { "coding productivity" },
								Locale.ENGLISH));

			} else if (qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.error", new Object[] {
								qaWorksheetId, "coding productivity" },
								Locale.ENGLISH));
			} else {
				model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
						qaProductivitiesSampleList);
			}
		} catch (EntityNotFoundException efe) {
			model.addAttribute(Constants.ERROR, this.messageSource.getMessage(
					"qalayout.no.record", new Object[] { "ar productivity" },
					Locale.ENGLISH));
		} catch (ArgusException e) {
			LOGGER.error(e);
		}
		model.addAttribute("QA_WORKSHEET", qaWorksheet);
		map.put("mode", "");

		return "codingLayout";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/account")
	public String accountLayout(Map<String, Object> map, HttpSession session,
			WebRequest request, Model model,
			@RequestParam(value = "qaworksheetid") Long qaWorksheetId) {
		LOGGER.info("in account layout");

		String orderBy = null;

		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		try {

			fetchQCPoints(Constants.ACCOUNTING_DEPARTMENT_ID, null, model);

			List<QAProductivitySampling> qaProductivitiesSampleList = fetchAllProductivitySamplingData(
					Constants.ACCOUNTING_DEPARTMENT_ID, qaWorksheetId, orderBy);

			if (qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.no.record",
								new Object[] { "accounting productivity" },
								Locale.ENGLISH));

			} else if (qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.error", new Object[] {
								qaWorksheetId, "accounting productivity" },
								Locale.ENGLISH));
			} else {
				model.addAttribute("QA_PROD_DATA", qaProductivitiesSampleList);
			}
		} catch (ArgusException e) {
			LOGGER.error(e);
		}
		map.put("mode", "");

		return "accountLayout";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ar")
	public String arLayout(Map<String, Object> map, HttpSession session,
			WebRequest request, Model model,
			@RequestParam(value = "qaworksheetid") Long qaWorksheetId) {
		LOGGER.info("in ar layout");
		QAWorksheet qaWorksheet = null;

		try {
			qaWorksheet = qaWorksheetDao.findById(qaWorksheetId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		String orderBy = null;

		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		model.addAttribute("ORDER_BY", orderBy);

		try {

			fetchQCPoints(Constants.AR_DEPARTMENT_ID, null, model);

			List<QAProductivitySampling> qaProductivitiesSampleList = fetchAllProductivitySamplingData(
					Constants.AR_DEPARTMENT_ID, qaWorksheetId, orderBy);

			if (qaWorksheet != null
					&& Constants.AR_DEPARTMENT_ID.equals(qaWorksheet
							.getDepartment().getId().toString())
					&& qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.no.record",
								new Object[] { "ar productivity" },
								Locale.ENGLISH));

			} else if (qaProductivitiesSampleList != null
					&& qaProductivitiesSampleList.size() == 0) {

				model.addAttribute(Constants.ERROR, this.messageSource
						.getMessage("qalayout.error", new Object[] {
								qaWorksheetId, "ar productivity" },
								Locale.ENGLISH));
			} else {
				model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
						qaProductivitiesSampleList);
			}
		} catch (EntityNotFoundException efe) {
			model.addAttribute(Constants.ERROR, this.messageSource.getMessage(
					"qalayout.no.record", new Object[] { "ar productivity" },
					Locale.ENGLISH));
		} catch (ArgusException e) {
			LOGGER.error(e);
		}
		model.addAttribute("QA_WORKSHEET", qaWorksheet);
		map.put("mode", "");

		return "arLayout";
	}

	// private Map<QcPoint, List<QcPoint>> fetchQCPointsWithParent(
	// String departmentId) throws ArgusException {
	// Map<String, String> whereClauses = new HashMap<String, String>();
	// Map<String, String> orderClauses = new HashMap<String, String>();
	//
	// whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
	// //orderClauses.put(Constants.ORDER_BY, "parent.id");
	//
	// List<QcPoint> qcPoints = qcPointDao.findAll(orderClauses, whereClauses,
	// false);
	//
	// LOGGER.info("TOTAL QC POINTs:: " + qcPoints.size());
	//
	// Map<QcPoint, List<QcPoint>> qcPointsMap = new HashMap<QcPoint,
	// List<QcPoint>>();
	// List<QcPoint> parents = new ArrayList<QcPoint>();
	// List<QcPoint> childQcPoints = new ArrayList<QcPoint>();
	//
	// for (QcPoint qcPoint : qcPoints) {
	// // this qc point is parent
	// if (qcPoint.getParent() == null) {
	// parents.add(qcPoint);
	// }
	// }
	//
	// for (QcPoint parent : parents) {
	// for (QcPoint qcPoint : qcPoints) {
	// if (qcPoint.getParent() != null &&
	// qcPoint.getParent().getId().equals(parent.getId())) {
	// childQcPoints.add(qcPoint);
	// }
	// }
	// qcPointsMap.put(parent, childQcPoints);
	// }
	// return qcPointsMap;
	// }

	// private Map<QcPoint, List<QcPoint>> fetchQCPoints(String departmentId)
	// throws ArgusException {
	// Map<String, String> whereClauses = new HashMap<String, String>();
	// Map<String, String> orderClauses = new HashMap<String, String>();
	//
	// whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
	// whereClauses.put(Constants.PARENT_ONLY, "true");
	// orderClauses.put(Constants.ORDER_BY, "parent.id");
	//
	// List<QcPoint> qcPointsParentsOnly = qcPointDao.findAll(orderClauses,
	// whereClauses, false);
	//
	// LOGGER.info("TOTAL QC POINTs:: " + qcPointsParentsOnly.size());
	//
	// Map<QcPoint, List<QcPoint>> qcPointsMap = new HashMap<QcPoint,
	// List<QcPoint>>();
	//
	// if (qcPointsParentsOnly != null && qcPointsParentsOnly.size() > 0) {
	// orderClauses.clear();
	// orderClauses.put(Constants.ORDER_BY, "id");
	//
	// for (QcPoint qcPoint : qcPointsParentsOnly) {
	// whereClauses.clear();
	// whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
	// whereClauses.put(Constants.PARENT_ID, qcPoint.getId()
	// .toString());
	//
	// List<QcPoint> qcPointChildren = qcPointDao.findAll(
	// orderClauses, whereClauses, false);
	//
	// qcPointsMap.put(qcPoint, qcPointChildren);
	// }
	//
	// } else {
	// whereClauses.clear();
	// whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
	// orderClauses.clear();
	// orderClauses.put(Constants.ORDER_BY, "id");
	//
	// List<QcPoint> qcPoints = qcPointDao.findAll(orderClauses,
	// whereClauses, false);
	//
	// qcPointsMap.put(new QcPoint(), qcPoints);
	// }
	//
	// LOGGER.info("NOW QC POINTs IN MAP:: " + qcPointsMap.size());
	//
	// for (Map.Entry<QcPoint, List<QcPoint>> entry : qcPointsMap.entrySet()) {
	//
	// LOGGER.info("PARENT:::::::::" + entry.getKey().getName() + "/"
	// + entry.getValue() + ":::::::ID:::::"
	// + entry.getKey().getId());
	//
	// for (QcPoint qcPoint : entry.getValue()) {
	// LOGGER.info("CHILD::::::" + qcPoint.getName()
	// + ":::::::ID::::::::::" + qcPoint.getId());
	// }
	//
	// }
	//
	// return qcPointsMap;
	// }

	private void fetchQCPoints(String departmentId, String subDepartmentId,
			Model model) throws ArgusException {

		Map<String, String> whereClauses = new HashMap<String, String>();
		Map<String, String> orderClauses = new HashMap<String, String>();

		whereClauses.put(Constants.DEPARTMENT_ID, departmentId);

		List<QcPoint> parentQCPoints = new ArrayList<QcPoint>();
		if (subDepartmentId != null) {
			whereClauses.put(Constants.SUB_DEPARTMENT_ID, subDepartmentId);

			parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
					Long.valueOf(departmentId), Long.valueOf(subDepartmentId));
		} else {
			parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
					Long.valueOf(departmentId), null);
		}

		model.addAttribute("PARENT_QC_POINTS", parentQCPoints);

		Map<Long, String> parentQCPointMap = new HashMap<Long, String>();

		if (parentQCPoints != null && parentQCPoints.size() > 0) {
			for (QcPoint qcPoint : parentQCPoints) {
				parentQCPointMap.put(qcPoint.getId(), qcPoint.getName());
			}
		}

		model.addAttribute("PARENT_QC_POINTS_MAP", parentQCPointMap);

		whereClauses.put(Constants.CHILD_ONLY, "true");

		orderClauses.put(Constants.ORDER_BY, "parent.id, d.id");

		List<QcPoint> qcPointChildrenOnly = qcPointDao.findAll(orderClauses,
				whereClauses, false);

		LOGGER.info("qcPointChildrenOnly:: " + qcPointChildrenOnly.size());

		model.addAttribute("CHILDREN_QC_POINTS", qcPointChildrenOnly);
	}

	private List<QAProductivitySampling> fetchAllProductivitySamplingData(
			String departmentId, Long qaWorksheetId, String orderBy)
			throws ArgusException {
		List<QAProductivitySampling> qaProductivities = null;

		Map<String, String> whereClauses = new HashMap<String, String>();
		whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
		whereClauses.put(Constants.QAWORKSHEET_ID, qaWorksheetId.toString());

		Map<String, String> orderClauses = new HashMap<String, String>();

		if (orderBy != null) {
			orderClauses.put(Constants.ORDER_BY, orderBy);
		}

		qaProductivities = this.qaProductivitySamplingDao.findAll(whereClauses,
				orderClauses, true);
		return qaProductivities;
	}

	/**
	 * function to set
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savepatientinfo", method = RequestMethod.POST)
	@ResponseBody
	public Long savePatientInfo(WebRequest request) {
		LOGGER.info("in savePatientInfo method");

		QAWorksheetPatientInfo qaWorksheetPatientInfo = null;
		String flag = "save"; // save or update

		try {
			if (null != request) {

				if (null != request.getParameter(Constants.ID)) {
					qaWorksheetPatientInfo = qaWorksheetPatientInfoDao
							.findById(Long.parseLong(request
									.getParameter(Constants.ID)));
					flag = "update";
					qaWorksheetPatientInfo.setModifiedBy(AkpmsUtil
							.getLoggedInUser());
					qaWorksheetPatientInfo.setModifiedOn(new Date());
				} else {
					qaWorksheetPatientInfo = new QAWorksheetPatientInfo();
					qaWorksheetPatientInfo.setCreatedBy(AkpmsUtil
							.getLoggedInUser());
					qaWorksheetPatientInfo.setCreatedOn(new Date());
				}

				if (null != request.getParameter(Constants.SAMPLEID)) {
					QAProductivitySampling qaProductivitySampling = qaProductivitySamplingDao
							.findById(Long.parseLong(request
									.getParameter(Constants.SAMPLEID)));

					qaWorksheetPatientInfo
							.setQaProductivitySampling(qaProductivitySampling);
				}

				if (null != request.getParameter(Constants.PATIENT_NAME)) {
					qaWorksheetPatientInfo.setPatientName(request
							.getParameter(Constants.PATIENT_NAME));
				}

				if (null != request.getParameter(Constants.ACCOUNT_NUMBER)) {
					qaWorksheetPatientInfo.setAccountNumber(request
							.getParameter(Constants.ACCOUNT_NUMBER));
				}

				if (null != request.getParameter(Constants.TRANSACTION)) {
					qaWorksheetPatientInfo.setTransaction(request
							.getParameter(Constants.TRANSACTION));
				}

				if (null != request.getParameter(Constants.SR_NO)) {
					qaWorksheetPatientInfo.setSrNo(request
							.getParameter(Constants.SR_NO));
				}

				if (null != request.getParameter(Constants.CHECK)) {
					qaWorksheetPatientInfo.setCheck(request
							.getParameter(Constants.CHECK));
				}

				if (null != request.getParameter(Constants.REMARK)) {
					qaWorksheetPatientInfo.setRemarks(request
							.getParameter(Constants.REMARK));
				}

				if (null != request.getParameter(Constants.CPT_CODE_DEMO)) {
					qaWorksheetPatientInfo.setCptCodesDemo(request
							.getParameter(Constants.CPT_CODE_DEMO));
				}
			}

			if (flag.equalsIgnoreCase("save")) {
				qaWorksheetPatientInfoDao.save(qaWorksheetPatientInfo);
			} else {
				qaWorksheetPatientInfoDao.update(qaWorksheetPatientInfo);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		if (null != qaWorksheetPatientInfo) {
			return qaWorksheetPatientInfo.getId();
		}

		return null;
	}

	/**
	 * function to set
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/savesampleremark", method = RequestMethod.POST)
	@ResponseBody
	public Long saveSampleRemark(WebRequest request) {
		LOGGER.info("in savePatientInfo method");

		QAProductivitySampling qaProductivitySampling = null;
		String flag = "save"; // save or update

		try {
			if (null != request) {

				if (null != request.getParameter(Constants.ID)) {
					qaProductivitySampling = qaProductivitySamplingDao
							.findById(Long.parseLong(request
									.getParameter(Constants.ID)));
					flag = "update";
					qaProductivitySampling.setModifiedBy(AkpmsUtil
							.getLoggedInUser());
					qaProductivitySampling.setModifiedOn(new Date());
				} else {
					qaProductivitySampling = new QAProductivitySampling();
					qaProductivitySampling.setCreatedBy(AkpmsUtil
							.getLoggedInUser());
					qaProductivitySampling.setCreatedOn(new Date());
				}

				if (null != request.getParameter(Constants.REMARK)) {
					qaProductivitySampling.setRemarks(request
							.getParameter(Constants.REMARK));
				}

				// if (null != request.getParameter(Constants.ACCOUNT_COUNT)
				// && request.getParameter(Constants.ACCOUNT_COUNT) != "") {
				// qaProductivitySampling.setAccountCount(Integer
				// .valueOf(request
				// .getParameter(Constants.ACCOUNT_COUNT)));
				// }

				if (null != request.getParameter(Constants.CPT_COUNT)
						&& request.getParameter(Constants.CPT_COUNT) != "") {
					qaProductivitySampling.setCptCount(Integer.valueOf(request
							.getParameter(Constants.CPT_COUNT)));
				}

				// if (null != request.getParameter(Constants.BATCH_PERCENTAGE)
				// && request.getParameter(Constants.BATCH_PERCENTAGE) != "") {
				// qaProductivitySampling.setBatchPercentage(Float
				// .valueOf(request
				// .getParameter(Constants.BATCH_PERCENTAGE)));
				// }

			}

			if (flag.equalsIgnoreCase("save")) {
				qaProductivitySamplingDao.save(qaProductivitySampling);
			} else {
				qaProductivitySamplingDao.update(qaProductivitySampling);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		if (null != qaProductivitySampling) {
			return qaProductivitySampling.getId();
		}

		return null;
	}

	/**
	 * function to set Coding patient details
	 *
	 * @param request
	 * @return
	 */
	// @RequestMapping(value = "/savecodingpatientinfo", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Long saveCodingPatientInfo(WebRequest request) {
	// LOGGER.info("in savePatientInfo method");
	//
	// QAWorksheetPatientInfo qaWorksheetPatientInfo = null;
	// String flag = "save"; // save or update
	//
	// try {
	// if (null != request) {
	//
	// if (null != request.getParameter(Constants.ID)) {
	// qaWorksheetPatientInfo = qaWorksheetPatientInfoDao
	// .findById(Long.parseLong(request
	// .getParameter(Constants.ID)));
	// flag = "update";
	// qaWorksheetPatientInfo.setModifiedBy(AkpmsUtil
	// .getLoggedInUser());
	// qaWorksheetPatientInfo.setModifiedOn(new Date());
	// } else {
	// qaWorksheetPatientInfo = new QAWorksheetPatientInfo();
	// qaWorksheetPatientInfo.setCreatedBy(AkpmsUtil
	// .getLoggedInUser());
	// qaWorksheetPatientInfo.setCreatedOn(new Date());
	// }
	//
	// if (null != request.getParameter(Constants.SAMPLEID)) {
	// QAProductivitySampling qaProductivitySampling = qaProductivitySamplingDao
	// .findById(Long.parseLong(request
	// .getParameter(Constants.SAMPLEID)));
	//
	// qaWorksheetPatientInfo
	// .setQaProductivitySampling(qaProductivitySampling);
	// }
	//
	// if (null != request.getParameter(Constants.ACCOUNT_NUMBER)) {
	// qaWorksheetPatientInfo.setAccountNumber(request
	// .getParameter(Constants.ACCOUNT_NUMBER));
	// }
	// if (null != request.getParameter(Constants.CPT_CODE_DEMO)) {
	// qaWorksheetPatientInfo.setCptCodesDemo(request
	// .getParameter(Constants.CPT_CODE_DEMO));
	// }
	//
	// if (null != request.getParameter(Constants.REMARK)) {
	// qaWorksheetPatientInfo.setRemarks(request
	// .getParameter(Constants.REMARK));
	// }
	// }
	//
	// if (flag.equalsIgnoreCase("save")) {
	// qaWorksheetPatientInfoDao.save(qaWorksheetPatientInfo);
	// } else {
	// qaWorksheetPatientInfoDao.update(qaWorksheetPatientInfo);
	// }
	//
	// } catch (Exception e) {
	// LOGGER.error(Constants.EXCEPTION, e);
	// }
	//
	// if (null != qaWorksheetPatientInfo) {
	// return qaWorksheetPatientInfo.getId();
	// }
	//
	// return null;
	// }

	/**
	 * function to save qc point check list for patient record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/saveqcpoint", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveQCPoint(WebRequest request) {
		LOGGER.info("in saveqcpoint method");

		QCPointChecklist qcPointChecklist = new QCPointChecklist();

		try {
			if (null != request) {
				// for AR where we don't have qcPatientInfo
				if (request.getParameter("qaPatientInfoId") != null
						&& !request.getParameter("qaPatientInfoId").equals("")) {
					QAWorksheetPatientInfo qaWorksheetPatientInfo = qaWorksheetPatientInfoDao
							.findById(Long.valueOf(request
									.getParameter("qaPatientInfoId")));
					qcPointChecklist
							.setQaWorksheetPatientInfo(qaWorksheetPatientInfo);
				}

				QcPoint qcPoint = qcPointDao.findById(
						Long.valueOf(request.getParameter("qcPointId")), false);

				QAProductivitySampling qaProductivitySampling = qaProductivitySamplingDao
						.findById(Long.valueOf(request.getParameter("sampleid")));

				User user = userDao.findById(
						Long.valueOf(request.getParameter("userid")), false);

				qcPointChecklist.setQcPoint(qcPoint);

				qcPointChecklist
						.setQaProductivitySampling(qaProductivitySampling);

				qcPointChecklist.setUser(user);
			}

			qcPointChecklistDao.save(qcPointChecklist);

			return true;

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return false;
	}

	/**
	 * function to save qc point check list for patient record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/deleteqcpoint", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteQCPoint(WebRequest request) {
		LOGGER.info("in deleteQCPoint method");

		try {
			if (null != request) {
				// for AR we need to delete qc point by qcpointid and sampleid
				if (request.getParameter("qaPatientInfoId") != null
						&& !request.getParameter("qaPatientInfoId").equals("")) {
					qcPointChecklistDao.delete(Long.valueOf(request
							.getParameter("qcPointId")), Long.valueOf(request
							.getParameter("qaPatientInfoId")));
				} else {
					qcPointChecklistDao
							.deleteByQCPointIdAndQAProductivitySamplingId(
									Long.valueOf(request
											.getParameter("qcPointId")), Long
											.valueOf(request
													.getParameter("sampleid")));
				}

			}

			return true;

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return false;
	}

	/**
	 * function to delete qc point check list for patient record
	 *
	 * @param request
	 * @return data: return true if deleted else return false.
	 */
	@RequestMapping(value = "/deletepatientinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteQaWorksheetPatientInfo(WebRequest request) {
		LOGGER.info("in deleteQCPoint method");

		try {
			if (null != request) {
				if (request.getParameter(Constants.ID) != null) {
					this.qaWorksheetPatientInfoDao.delete(Long.valueOf(request
							.getParameter(Constants.ID)));
				} else {
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return false;
	}

	/**
	 * function to save qc point check list for patient record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/deletesamplerecord", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<Long> deleteSampleRecord(WebRequest request) {
		LOGGER.info("in deleteSampleRecord method");

		List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = null;

		try {
			if (null != request) {

				if (request.getParameter(Constants.ID) != null) {
					qaWorksheetPatientInfos = this.qaProductivitySamplingDao
							.deleteDependencies(Long.valueOf(request
									.getParameter(Constants.ID)));
				}

			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		List<Long> patientIds = new ArrayList<Long>();
		if (qaWorksheetPatientInfos != null
				&& qaWorksheetPatientInfos.size() > 0) {
			for (QAWorksheetPatientInfo qaWorksheetPatientInfo : qaWorksheetPatientInfos) {
				patientIds.add(qaWorksheetPatientInfo.getId());
			}
		}

		return patientIds;
	}

	/**
	 * function to save qc point check list for patient record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/hidesamplerecord", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<Long> hideSampleRecord(WebRequest request) {
		LOGGER.info("in hideSampleRecord method");

		List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = null;
		QAProductivitySampling qaProductivitySampling = null;

		try {
			if (null != request) {

				if (request.getParameter(Constants.ID) != null) {

					Map<String, String> whereClauses = new HashMap<String, String>();
					whereClauses.put(Constants.ID,
							request.getParameter(Constants.ID));

					// QAProductivitySampling qaProductivitySampling =
					// this.qaProductivitySamplingDao
					// .findById(Long.valueOf(request
					// .getParameter(Constants.ID)));
					List<QAProductivitySampling> qaProductivitySamplings = this.qaProductivitySamplingDao
							.findAll(whereClauses, null, true);

					if (qaProductivitySamplings != null
							&& qaProductivitySamplings.size() > 0) {
						qaProductivitySampling = qaProductivitySamplings.get(0);

						if (request.getParameter(Constants.HIDE)
								.equalsIgnoreCase("true")) {
							qaProductivitySampling.setHidden(true);
						} else {
							qaProductivitySampling.setHidden(false);
						}

						qaProductivitySamplingDao
								.update(qaProductivitySampling);

						qaWorksheetPatientInfos = qaProductivitySampling
								.getQaWorksheetPatientInfos();
					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		List<Long> patientIds = new ArrayList<Long>();
		if (qaWorksheetPatientInfos != null
				&& qaWorksheetPatientInfos.size() > 0) {
			for (QAWorksheetPatientInfo qaWorksheetPatientInfo : qaWorksheetPatientInfos) {
				patientIds.add(qaWorksheetPatientInfo.getId());
			}
		}

		return patientIds;
	}

	/**
	 * function to save qc point check list for patient record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/error_count_per_account", method = RequestMethod.POST)
	@ResponseBody
	public Integer errorCountPerAccount(WebRequest request) {
		LOGGER.info("in CheckboxErrorCount method");

		Map<String, String> whereClauses = new HashMap<String, String>();

		Integer totalCount = 0;

		try {
			if (null != request) {
				if (request.getParameter(Constants.QA_PATIENT_INFO_ID) != null) {
					whereClauses.put(Constants.QA_PATIENT_INFO_ID,
							request.getParameter(Constants.QA_PATIENT_INFO_ID));
					totalCount = this.qcPointChecklistDao
							.totalRecord(whereClauses);
				}
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return totalCount;
	}

	/**
	 * function to save qc point check list for sample record
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointId': qcPointId},
	 */
	@RequestMapping(value = "/error_count_per_sample", method = RequestMethod.POST)
	@ResponseBody
	public Integer errorCountPerSample(WebRequest request) {
		LOGGER.info("in CheckboxErrorCount method");

		Map<String, String> whereClauses = new HashMap<String, String>();

		Integer totalCount = 0;

		try {
			if (null != request) {
				if (request.getParameter(Constants.QA_WORKSHEET_SAMPLE_ID) != null) {
					whereClauses.put(Constants.QA_WORKSHEET_SAMPLE_ID, request
							.getParameter(Constants.QA_WORKSHEET_SAMPLE_ID));
					totalCount = this.qcPointChecklistDao
							.totalRecord(whereClauses);
				}
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return totalCount;
	}

	/**
	 * function to insert multiple qc point check list for patient record
	 *
	 * Delete all qc point for that sample and then insert all again
	 *
	 * @param request
	 * @return data: {'sampleid':sampleId, 'qaPatientInfoId' : qaPatientInfoId,
	 *         'qcPointIds': qcPointIds},
	 */
	@RequestMapping(value = "/insertqcpoints", method = RequestMethod.POST)
	@ResponseBody
	public boolean insertQCPoint(WebRequest request) {
		LOGGER.info("in insertQCPoint method");

		try {
			if (null != request) {

				if (request.getParameter("qaPatientInfoId") != null
						&& !request.getParameter("qaPatientInfoId").equals("")) {
					// delete all qc check list for sample
					qcPointChecklistDao.deleteByQAProductivitySamplingId(Long
							.valueOf(request.getParameter("sampleid")), Long
							.valueOf(request.getParameter("qaPatientInfoId")));
				} else {
					// delete all qc check list for sample
					qcPointChecklistDao.deleteByQAProductivitySamplingId(Long
							.valueOf(request.getParameter("sampleid")));
				}

				if (request.getParameter("qcPointIds") != null
						&& !request.getParameter("qcPointIds").equals("")) {

					String[] qcPointIds = request.getParameter("qcPointIds")
							.split(",");

					if (qcPointIds != null && qcPointIds.length > 0) {
						QAProductivitySampling qaProductivitySampling = qaProductivitySamplingDao
								.findById(Long.valueOf(request
										.getParameter("sampleid")));

						User user = userDao.findById(
								Long.valueOf(request.getParameter("userid")),
								false);

						// for AR where we don't have qcPatientInfo
						QAWorksheetPatientInfo qaWorksheetPatientInfo = null;
						if (request.getParameter("qaPatientInfoId") != null
								&& !request.getParameter("qaPatientInfoId")
										.equals("")) {
							qaWorksheetPatientInfo = qaWorksheetPatientInfoDao
									.findById(Long.valueOf(request
											.getParameter("qaPatientInfoId")));
						}

						for (String strQcPoint : qcPointIds) {
							QCPointChecklist qcPointChecklist = new QCPointChecklist();

							qcPointChecklist
									.setQaWorksheetPatientInfo(qaWorksheetPatientInfo);

							qcPointChecklist
									.setQaProductivitySampling(qaProductivitySampling);

							qcPointChecklist.setUser(user);

							QcPoint qcPoint = qcPointDao.findById(
									Long.valueOf(strQcPoint), false);

							qcPointChecklist.setQcPoint(qcPoint);

							qcPointChecklistDao.save(qcPointChecklist);
						}

					}
				}
			}

			return true;

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		return false;
	}

	@RequestMapping(value = "/fetchqcpointchecklist", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<Long> fetchQcPointChecklist(WebRequest request) {
		LOGGER.info("in fetchQcPointChecklist method");

		List<QCPointChecklist> qcPointChecklists = null;
		Map<String, String> whereClauses = new HashMap<String, String>();

		try {
			if (null != request) {

				if (request.getParameter(Constants.SAMPLEID) != null) {
					whereClauses.put(Constants.QA_WORKSHEET_SAMPLE_ID,
							request.getParameter(Constants.SAMPLEID));
				}

				if (request.getParameter(Constants.QA_PATIENT_INFO_ID) != null
						&& !request.getParameter(Constants.QA_PATIENT_INFO_ID)
								.equals("")) {
					whereClauses.put(Constants.QA_PATIENT_INFO_ID,
							request.getParameter(Constants.QA_PATIENT_INFO_ID));
				}

				qcPointChecklists = qcPointChecklistDao.findAll(whereClauses,
						null, true);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		List<Long> qcPointIds = new ArrayList<Long>();
		if (qcPointChecklists != null && qcPointChecklists.size() > 0) {
			for (QCPointChecklist qcPointChecklist : qcPointChecklists) {
				qcPointIds.add(qcPointChecklist.getQcPoint().getId());
			}
		}

		return qcPointIds;
	}

	/*
	 * private List<QAWorksheetPatientInfo> listAllPatientInfo() {
	 * List<QAWorksheetPatientInfo> qaWorksheetPatientInfo = null;
	 * 
	 * Map<String, String> whereClause = new HashMap<String, String>(); try {
	 * qaWorksheetPatientInfo = this.qaWorksheetPatientInfoDao.findAll(
	 * whereClause, null, true); } catch (ArgusException e) {
	 * LOGGER.error("Error listing QAWorksheetPatientInfos :: ", e); } return
	 * qaWorksheetPatientInfo; }
	 */

}
