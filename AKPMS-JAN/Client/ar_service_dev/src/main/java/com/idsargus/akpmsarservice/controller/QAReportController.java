package com.idsargus.akpmsarservice.controller;

import com.idsargus.akpmsarservice.dto.QCPointResponse;
import com.idsargus.akpmsarservice.dto.QaManagerUser;
import com.idsargus.akpmsarservice.dto.QcPointRecord;
import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmsarservice.model.*;
import com.idsargus.akpmsarservice.model.domain.*;
import com.idsargus.akpmsarservice.repository.ChargeBatchProcessingRepository;
import com.idsargus.akpmsarservice.repository.DepartmentRepository;
import com.idsargus.akpmsarservice.repository.QAWorksheetRepository;
import com.idsargus.akpmsarservice.repository.UserDataRestRepository;
import com.idsargus.akpmsarservice.repository.chargeProductivity.ChargeProductivityDao;
import com.idsargus.akpmsarservice.repository.paymentproductivity.CredentialingAccountingProductivityDao;
import com.idsargus.akpmsarservice.repository.paymentproductivity.PaymentProductivityDao;
import com.idsargus.akpmsarservice.repository.productivity.ArProductivityDao;
import com.idsargus.akpmsarservice.repository.qamanager.*;
import com.idsargus.akpmsarservice.repository.qcpoint.QcPointDao;
import com.idsargus.akpmsarservice.repository.user.UserDao;
import com.idsargus.akpmsarservice.util.AkpmsUtil;
import com.idsargus.akpmsarservice.util.Constants;
import com.idsargus.akpmscommonservice.entity.ChargeBatchProcessing;
import com.idsargus.akpmscommonservice.entity.ChargeProductivity;
import com.idsargus.akpmscommonservice.entity.PaymentProductivity;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping(value = "/v1/arapi/reports")
public class QAReportController {

    private DepartmentRepository departmentDao;
    @Autowired
    private ArProductivityDao arProductivityDao;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private QAWorksheetDao qaWorksheetDao;
    @Autowired
    private QAWorksheetDoctorDao qaWorksheetDoctorDao;

    @Autowired
    private QAWorksheetPatientInfoDao qaWorksheetPatientInfoDao;
    @Autowired
    private QAProductivitySamplingDao daoQaProductivitySamplingDao;

    @Autowired
    private ChargeBatchProcessingRepository chargeBatchProcessingRepository;
    @Autowired
    private UserDataRestRepository userDataRestRepository;
    @Autowired
    private QCPointChecklistDao qcPointChecklistDao;
    @Autowired
    private QCPointChecklistDao checklistDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QcPointDao qcPointDao;

    @Autowired
    private QAWorksheetStaffDao qaWorksheetStaffDao;
    @Autowired
    private QAWorksheetRepository qaWorksheetRepository;

    @Autowired
    private QAProductivitySamplingDao qaProductivitySamplingDao;

    @Autowired
    private ChargeProductivityDao chargeProductivityDao;

    @Autowired
    private PaymentProductivityDao paymentProductivityDao;
    @Autowired
    private CredentialingAccountingProductivityDao credentialingAccountingProductivityDao;

    // Print Report criteria
    private Map<String, String> printReportCriteria = new HashMap<String, String>();

//    @Resource(name = "arStatusCodes")
//    private Properties arStatusCodesProps;


    @ModelAttribute("parentDepartments")
    public List<Department> parentDepartments() {

        List<Department> parentDepartemts = null;
        try {
            parentDepartemts = departmentDao.findAllParentOrderedByName();
        } catch (Exception e) {
            //LOGGER.error("Failed to findAllDepartmentOrderedByName", e);
        }

        return parentDepartemts;
    }

    @RequestMapping(value = "")
    public String qAReports(Model modelMap) {

        modelMap.addAttribute(Constants.MONTHS, AkpmsUtil.getMonths());
        modelMap.addAttribute(Constants.YEARS, AkpmsUtil.getYears());

        // Fetch all active user who have qa manager permission
        List<User> userList = new ArrayList<User>();

        try {
            Map<String, String> whereClauses = new HashMap<String, String>();
            whereClauses.put(Constants.SELECTED_ROLES_IDS,
                    Constants.STANDART_USER_ROLE_ID.toString());
            whereClauses.put(Constants.STATUS, Constants.STRING_ONE);
            whereClauses.put(Constants.PERMISSION,
                    Constants.PERMISSION_QA_WORKSHEET_MANAGER);

            userList = userDao.findAll(null, whereClauses);
        } catch (Exception e) {
            //LOGGER.error(e);
        }

        modelMap.addAttribute("QA_MANAGER_LIST", userList);
        //LOGGER.info(":: showing reports ::");

        return "qareport";
    }

    @RequestMapping(value = "/qareportuser", method = RequestMethod.GET)
    public QaManagerResponse calculatedQAErrorReportData(@RequestParam("fromDate") String fromDate,
                                                         @RequestParam("toDate") String toDate,
                                                         @RequestParam("subDepartment") String subDepartment,
                                                         @RequestParam("createdBy") String createdBy,
                                                         @RequestParam("status") String status,
                                                         @RequestParam("department") String department) {

        Map<String, String> whereClause = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        QaManagerResponse qaManagerResponse = new QaManagerResponse();
        try {

            /*
             * List<Object[]> qaReportResults =
             * this.daoQaProductivitySamplingDao
             * .findProductivityCountWithUsersAndQAWorksheets( whereClause,
             * orderClause);
             */
            if (fromDate != null && !fromDate.isEmpty()) {
                whereClause.put(Constants.QA_REPORT_FROM_DATE, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                whereClause.put(Constants.QA_REPORT_TO_DATE, toDate);
            }

            if (status != null && !status.isEmpty()) {
                whereClause.put(Constants.STATUS, status);
            }
            if (createdBy != null && !createdBy.isEmpty()) {
                whereClause.put(Constants.CREATED_BY, createdBy);
            }

            if (subDepartment != null && !subDepartment.isEmpty()) {
                whereClause.put(Constants.SUB_DEPARTMENT, subDepartment);
            }
            if (department != null && !department.isEmpty()) {
                whereClause.put(Constants.DEPARTMENT, department);
            }

            List<Object[]> qaReportResults = this.daoQaProductivitySamplingDao
                    .findQAWorksheetUserReportData(whereClause);


            qaManagerResponse.setDepartment(department);
            qaManagerResponse.setDateFrom(fromDate);
            qaManagerResponse.setDateTo(toDate);
            qaManagerResponse.setTeam(subDepartment);
            List<QaManagerUser> managerUserList = new ArrayList<>();
            for (Object[] qaReport : qaReportResults) {
                QaManagerUser qaManagerUser = new QaManagerUser();
                qaManagerUser.setName((String) qaReport[0]);
                qaManagerUser.setQaWorkSheetId(String.valueOf(qaReport[1]));
                qaManagerUser.setUserName(String.valueOf(qaReport[4]));
                qaManagerUser.setQaAccount(String.valueOf(qaReport[5]));
                qaManagerUser.setUserId((String) qaReport[3]);
                qaManagerUser.setError(String.valueOf(qaReport[7]));
                qaManagerUser.setErrorPercentage(String.valueOf(qaReport[8]));
                managerUserList.add(qaManagerUser);
            }
            qaManagerResponse.setQaManagerUserList(managerUserList);
            map.put("reportData", qaReportResults);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return qaManagerResponse;
    }

    @GetMapping(value = "/qareportqcpoint")
    public Map<String, Object> majorQAErrorReportData(@RequestParam("fromDate") String fromDate,
                                                      @RequestParam("toDate") String toDate,
                                                      @RequestParam("subDepartment") String subDepartment,
                                                      @RequestParam("createdBy") String createdBy,
                                                      @RequestParam("status") String status,
                                                      @RequestParam("department") String department) {
        Map<String, String> whereClause = null;
        Map<String, Object> map = new HashMap<>();

        QAReportRequest qaReportRequest = new QAReportRequest();
        if (fromDate != null && department != null && subDepartment != null && createdBy != null && toDate != null && status != null) {
            qaReportRequest.setFromDate(fromDate);
            qaReportRequest.setDepartment(department);
            qaReportRequest.setSubDepartment(subDepartment);
            qaReportRequest.setCreatedBy(createdBy);
            qaReportRequest.setToDate(toDate);
            qaReportRequest.setStatus(status);
        }
        if (qaReportRequest != null) {
            map = populateGenericMapData(map, qaReportRequest);
            whereClause = getQAReportCriteriaMap(qaReportRequest);

            try {
                // fetch user for selected department/sub-department

                List<Object[]> qaReportResults = this.checklistDao
                        .findMostlyErrorsDoneByUsers(whereClause);

                QAManagerQcPointResponse qaManagerQcPointResponse = new QAManagerQcPointResponse();
                for (Object[] qcReport : qaReportResults) {
                    qaManagerQcPointResponse.setQcPoint(qcReport[0].toString());
                    qaManagerQcPointResponse.setName(qcReport[1].toString());
                    qaManagerQcPointResponse.setErrorCount(qcReport[2].toString());
                }

                map.put("qaReportData", qaManagerQcPointResponse);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    private Map<String, Object> populateGenericMapData(Map<String, Object> map,
                                                       QAReportRequest qaReportRequest) {


        map.put(Constants.START_MONTH, qaReportRequest.getFromDate());
        map.put(Constants.END_MONTH, qaReportRequest.getToDate());

        //For Sub department
        if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.CHARGE_ENTRY_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CHARGE_ENTRY_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.CODING_PRIMARY_CARE_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CODING_PRIMARY_CARE_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.CODING_SPECIALIST_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CODING_SPECIALIST_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.DEMO_AND_VERIFICATION_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.DEMO_AND_VERIFICATION_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.PAYMENT_POSTING_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.PAYMENT_POSTING_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.TREASURY_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.TREASURY_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.HMO_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.HMO_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.MCL_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.MCL_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.MCR_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.MCR_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.PPO_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.PPO_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.SELF_PAY_AND_CEP_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.SELF_PAY_AND_CEP_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.WORK_COMP_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.WORK_COMP_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                Constants.CHDP_ID)) {
            map.put(Constants.SUB_DEPARTMENT_NAME, Constants.CHDP_NAME);
        } else if (qaReportRequest.getSubDepartment().equalsIgnoreCase(
                "-1")) {
            map.put(Constants.SUB_DEPARTMENT_NAME, "All");
        }

        //For Department
        if (qaReportRequest.getDepartment().equalsIgnoreCase(
                Constants.PAYMENT_DEPARTMENT_ID)) {
            map.put(Constants.DEPARTMENT, Constants.PAYMENT_DEPARTMENT_NAME);
        } else if (qaReportRequest.getDepartment().equalsIgnoreCase(
                Constants.CHARGE_DEPARTMENT_ID)) {
            map.put(Constants.DEPARTMENT, Constants.CHARGE_DEPARTMENT_NAME);
        } else if (qaReportRequest.getDepartment().equalsIgnoreCase(
                Constants.ACCOUNTING_DEPARTMENT_ID)) {
            map.put(Constants.DEPARTMENT, Constants.ACCOUNTING_DEPARTMENT_NAME);
        } else if (qaReportRequest.getDepartment().equalsIgnoreCase(
                Constants.AR_DEPARTMENT_ID)) {
            map.put(Constants.DEPARTMENT, Constants.AR_DEPARTMENT_NAME);
        }
        map.put(Constants.DEPARTMENT_ID,
                qaReportRequest.getDepartment());

        return map;
    }

    private Map<String, String> getQAReportCriteriaMap(
            QAReportRequest qaReportRequest) {

        Map<String, String> criteriaMap = new HashMap<String, String>(
                Constants.FOUR);
        if (qaReportRequest != null) {
            if (qaReportRequest.getDepartment() != null) {
                criteriaMap.put(Constants.DEPARTMENT,
                        qaReportRequest.getDepartment());
                criteriaMap.put(Constants.DEPARTMENT_ID,
                        qaReportRequest.getDepartment());
            }
            if (qaReportRequest.getSubDepartment() != null
                    && !qaReportRequest.getSubDepartment()
                    .equals("-1")) {
                criteriaMap.put(Constants.SUB_DEPARTMENT,
                        qaReportRequest.getSubDepartment());
            }
            if (qaReportRequest.getFromDate() != null) {
                criteriaMap.put(Constants.QA_REPORT_FROM_DATE,
                        qaReportRequest.getFromDate());
            }
            if (qaReportRequest.getToDate() != null) {
                criteriaMap.put(Constants.QA_REPORT_TO_DATE,
                        qaReportRequest.getToDate());
            }
            // set created by id
            if (qaReportRequest.getCreatedBy() != null) {
                criteriaMap.put(Constants.CREATED_BY, StringUtils.join(
                        qaReportRequest.getCreatedBy()));
            }
        }

        return criteriaMap;
    }

    @RequestMapping(value = "/summaryreport", method = RequestMethod.GET)
    public Map<String,Object> qaSummaryReport(@RequestParam("fromDate") String fromDate,
                                                            @RequestParam("toDate") String toDate,
                                                            @RequestParam("subDepartment") String subDepartment,
                                                            @RequestParam("createdBy") String createdBy,
                                                            @RequestParam("status") String status,
                                                            @RequestParam("department") String department) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> whereClause = null;
        QAReportRequest qaReportRequest = new QAReportRequest();
        qaReportRequest.setStatus(status);
        qaReportRequest.setDepartment(department);
        qaReportRequest.setFromDate(fromDate);
        qaReportRequest.setSubDepartment(subDepartment);
        qaReportRequest.setCreatedBy(createdBy);
        qaReportRequest.setToDate(toDate);
        List<QAProductivitySampling> qaProductivitiesSampleList = new ArrayList<QAProductivitySampling>();
        Map<String,Object> qaWorksheetLayoutPaymentResponse = new HashMap<>();
        if (qaReportRequest != null) {
            map = populateGenericMapData(map, qaReportRequest);

            try {
                fetchQCPoints(qaReportRequest.getDepartment(),
                        qaReportRequest.getSubDepartment(), qaWorksheetLayoutPaymentResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String orderBy = null;

        if (orderBy != null) {
            qaWorksheetLayoutPaymentResponse.put("OrderBy",orderBy);
        }

        Map<String, String> orderClauses = new HashMap<String, String>();

        if (orderBy != null) {
            orderClauses.put(Constants.ORDER_BY, orderBy);
        }

        whereClause = getQAReportCriteriaMap(qaReportRequest);

        whereClause.put(Constants.STATUS, Constants.STRING_TWO);

        try {
            qaProductivitiesSampleList = this.qaProductivitySamplingDao
                    .findAll(whereClause, orderClauses, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        qaWorksheetLayoutPaymentResponse.put("QA_PROD_SAMPLE_DATA_LIST",qaProductivitiesSampleList);

//        model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
//                qaProductivitiesSampleList);


        String view = "";
        if (department.equalsIgnoreCase(
                Constants.AR_DEPARTMENT_ID)) {
            view = "arSummaryReport";
        } else if (department.equalsIgnoreCase(
                Constants.PAYMENT_DEPARTMENT_ID)) {
            view = "paymentSummaryReport";
        } else if (department.equalsIgnoreCase(
                Constants.CHARGE_DEPARTMENT_ID)) {
            view = "codingSummaryReport";
        }

        //return view;
        return qaWorksheetLayoutPaymentResponse;

    }

    /**
     * fetch qc points for given department
     *
     * @param departmentId
     * @param
     * @throws ArgusException
     */
    // private void fetchQCPoints(String departmentId, String subDepartmentId,
    // Model model) throws ArgusException {
    //
    // Map<String, String> whereClauses = new HashMap<String, String>();
    // Map<String, String> orderClauses = new HashMap<String, String>();
    //
    // List<QcPoint> parentQCPoints = qcPointDao
    // .getQcPointsWithParentIdAndChildCount(
    // Long.valueOf(departmentId),
    // Long.valueOf(subDepartmentId));
    // model.addAttribute("PARENT_QC_POINTS", parentQCPoints);
    //
    // whereClauses.put(Constants.DEPARTMENT_ID, departmentId);
    //
    // if (null != subDepartmentId) {
    // whereClauses.put(Constants.SUB_DEPARTMENT_ID, subDepartmentId);
    // }
    //
    // whereClauses.put(Constants.CHILD_ONLY, "true");
    //
    // orderClauses.put(Constants.ORDER_BY, "parent.id, d.id");
    //
    // List<QcPoint> qcPointChildrenOnly = qcPointDao.findAll(orderClauses,
    // whereClauses, false);
    //
    // LOGGER.info("qcPointChildrenOnly:: " + qcPointChildrenOnly.size());
    //
    // model.addAttribute("CHILDREN_QC_POINTS", qcPointChildrenOnly);
    // }
    private void fetchQCPoints(String departmentId, String subDepartmentId, Map<String,Object> qaWorksheetLayoutPaymentResponse) throws ArgusException {

        Map<String, String> whereClauses = new HashMap<String, String>();
        Map<String, String> orderClauses = new HashMap<String, String>();

        whereClauses.put(Constants.DEPARTMENT_ID, departmentId);

        List<QcPoint> parentQCPoints = new ArrayList<QcPoint>();
        if (subDepartmentId != null && !subDepartmentId.equalsIgnoreCase("-1")) {
            whereClauses.put(Constants.SUB_DEPARTMENT_ID, subDepartmentId);

            parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
                    Long.valueOf(departmentId), Long.valueOf(subDepartmentId));
        } else {
            parentQCPoints = qcPointDao.getQcPointsWithParentIdAndChildCount(
                    Long.valueOf(departmentId), null);
        }
        if (qaWorksheetLayoutPaymentResponse != null) {
            qaWorksheetLayoutPaymentResponse.put("PARENT_QC_POINTS",parentQCPoints);
            Map<Long, String> parentQCPointMap = new HashMap<Long, String>();

            if (parentQCPoints != null && parentQCPoints.size() > 0) {
                for (QcPoint qcPoint : parentQCPoints) {
                    parentQCPointMap.put(Long.valueOf(qcPoint.getId()), qcPoint.getName());
                }
            }
            qaWorksheetLayoutPaymentResponse.put("PARENT_QC_POINTS_MAP",parentQCPointMap);

            whereClauses.put(Constants.CHILD_ONLY, "true");

            orderClauses.put(Constants.ORDER_BY, "parent.id, d.id");

            List<QcPoint> qcPointChildrenOnly = qcPointDao.findAll(orderClauses,
                    whereClauses, false);


//            qaWorksheetLayoutPaymentResponse.setQcPointChildrenOnly(
//                    fetchQcPointChildrenOnlyResponse(qcPointChildrenOnly));
            qaWorksheetLayoutPaymentResponse.put("CHILDREN_QC_POINTS",qcPointChildrenOnly);
        }

        //  model.addAttribute("PARENT_QC_POINTS", parentQCPoints);


        // model.addAttribute("PARENT_QC_POINTS_MAP", parentQCPointMap);


        //LOGGER.info("qcPointChildrenOnly:: " + qcPointChildrenOnly.size());

        // model.addAttribute("CHILDREN_QC_POINTS", qcPointChildrenOnly);
    }

    private List<QCPointChildrenOnlyResponse> fetchQcPointChildrenOnlyResponse(List<QcPoint> qcPointList) {

        QCPointChildrenOnlyResponse qcPointChildrenOnlyResponse = new QCPointChildrenOnlyResponse();
        List<QCPointChildrenOnlyResponse> list = new ArrayList<>();
        for (QcPoint qcPoint : qcPointList) {
            //    qcPointChildrenOnlyResponse.setParent(qcPoint.getParent());
            // qcPointChildrenOnlyResponse.setDeleted(qcPoint.getParent().isDeleted());
            qcPointChildrenOnlyResponse.setDescription(qcPoint.getDescription());
            //  qcPointChildrenOnlyResponse.setStatus(qcPoint.getParent().isStatus());
            qcPointChildrenOnlyResponse.setName(qcPoint.getName());
            list.add(qcPointChildrenOnlyResponse);

        }
        return list;

    }


    @RequestMapping(value = "/monthlysummaryreport", method = RequestMethod.GET)
    public Map<String, Object> monthlySummaryReport(@RequestParam("fromDate") String fromDate,
                                                    @RequestParam("toDate") String toDate,
                                                    @RequestParam("subDepartment") String subDepartment,
                                                    @RequestParam("createdBy") String createdBy,
                                                    @RequestParam("status") String status,
                                                    @RequestParam("department") String department) {

        QAReportRequest qaReportRequest = new QAReportRequest();
        qaReportRequest.setStatus(status);
        qaReportRequest.setDepartment(department);
        qaReportRequest.setFromDate(fromDate);
        qaReportRequest.setSubDepartment(subDepartment);
        qaReportRequest.setCreatedBy(createdBy);
        qaReportRequest.setToDate(toDate);
        Map<String, Object> map = new HashMap<>();
        Map<String, String> whereClause = null;
        if (qaReportRequest != null) {
            whereClause = getQAReportCriteriaMap(qaReportRequest);
            map = populateGenericMapData(map, qaReportRequest);

            try {
                List<Object[]> qaReportResults = this.daoQaProductivitySamplingDao
                        .monthlyQASummary(whereClause);
                List<MonthlySummaryReportResponse> monthlySummaryReportResponseList = new ArrayList<>();
                for (Object[] qaReport : qaReportResults) {
                    MonthlySummaryReportResponse monthlySummaryReportResponse = new MonthlySummaryReportResponse();
                    monthlySummaryReportResponse.setDay((String) qaReport[0]);
                    monthlySummaryReportResponse.setProductivityCount(((BigInteger) qaReport[1]).intValue());
                    monthlySummaryReportResponse.setSampleCount(((BigInteger) qaReport[2]).intValue());
                    monthlySummaryReportResponse.setErrorCount(((BigDecimal) qaReport[3]).intValue());
                    monthlySummaryReportResponse.setErrorRate(((BigDecimal) qaReport[4]).intValue());
                    monthlySummaryReportResponseList.add(monthlySummaryReportResponse);
                }
                map.put("reportData", monthlySummaryReportResponseList);

            } catch (NumberFormatException e) {
                //LOGGER.error(e);
            } catch (ArgusException e) {
                //LOGGER.error(e);
            }
        }

        return map;
    }

    @RequestMapping(value = "/qastaff_json", method = RequestMethod.GET)
    @ResponseBody
    public List<QAWorksheetStaffJsonData> showStaffRemarksPopUp(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "departmentId") Long departmentId) {

        Map<String, String> whereClause = new HashMap<String, String>();

        whereClause.put(Constants.QAWORKSHEET_ID, id.toString());
        whereClause.put(Constants.DEPARTMENT, departmentId.toString());

        List<QAWorksheetStaffJsonData> staffJsonData = new ArrayList<QAWorksheetStaffJsonData>();

        try {
            staffJsonData
                    .addAll(getQAWorksheetStaffsJson(this.qaProductivitySamplingDao
                            .findExecutedUsersRecords(whereClause,
                                    Constants.QA_WORKSEET_TYPE_BYSTAFF)));
            Collections.unmodifiableCollection(staffJsonData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffJsonData;
    }

    /**
     * Helper for * rid
     *
     * @return List<QAWorksheetJsonData>
     */
    private List<QAWorksheetStaffJsonData> getQAWorksheetStaffsJson(
            List<Object[]> records) {

        List<QAWorksheetStaffJsonData> staffJsonData = new ArrayList<QAWorksheetStaffJsonData>();
        for (Object[] record : records) {
            QAWorksheetStaffJsonData jsonData = new QAWorksheetStaffJsonData();

            jsonData.setUserName(record[0] instanceof String ? (String) record[0]
                    : "");

            jsonData.setCount(record[1].toString());

            jsonData.setArStatusCode(record[2].toString());

            staffJsonData.add(jsonData);
        }

        return staffJsonData;
    }

    @GetMapping("/qaworksheetlayout/payment")
    public ResponseEntity<?> paymentLayout(@RequestParam("qaworksheetid") Integer qaWorksheetId,
                                                          @RequestParam("orderBy") String orderBy) {
        Map<String,Object> qaWorksheetLayoutPaymentResponse = new HashMap<>();
        extractQAWorkSheeLayout(qaWorksheetId, orderBy, qaWorksheetLayoutPaymentResponse);
        return ResponseEntity.ok().body(qaWorksheetLayoutPaymentResponse);
    }

    private void extractQAWorkSheeLayout(Integer qaWorksheetId, String orderBy, Map<String,Object> qaWorksheetLayoutPaymentResponse) {
        QAWorksheet qaWorksheet = null;
        try {
            qaWorksheet = qaWorksheetRepository.findById(qaWorksheetId).get();
        } catch (Exception e) {
            // LOGGER.error(e.getMessage());
        }

        if (orderBy != null) {
            qaWorksheetLayoutPaymentResponse.put("OrderBy",orderBy);
        }


        try {
            fetchQCPoints(Constants.PAYMENT_DEPARTMENT_ID, null, qaWorksheetLayoutPaymentResponse);

            List<QAProductivitySampling> qaProductivitiesSampleList = fetchAllProductivitySamplingData(
                    Constants.PAYMENT_DEPARTMENT_ID, qaWorksheetId, orderBy);

            if (qaWorksheet != null
                    && Constants.PAYMENT_DEPARTMENT_ID.equals(qaWorksheet
                    .getDepartment().getId().toString())
                    && qaProductivitiesSampleList != null
                    && qaProductivitiesSampleList.size() == 0) {

//                model.addAttribute(Constants.ERROR, this.messageSource
//                        .getMessage("qalayout.no.record",
//                                new Object[] { "payment productivity" },
//                                Locale.ENGLISH));

                 qaWorksheetLayoutPaymentResponse.put("Error",this.messageSource.getMessage(
                         "qalayout.no.record", new Object[] { "ar productivity" },
                         Locale.ENGLISH));
            } else if (qaProductivitiesSampleList != null
                    && qaProductivitiesSampleList.size() == 0) {

//                model.addAttribute(Constants.ERROR, this.messageSource
//                        .getMessage("qalayout.error", new Object[] {
//                                        qaWorksheetId, "payment productivity" },
//                                Locale.ENGLISH));
                qaWorksheetLayoutPaymentResponse.put("Error",this.messageSource
                        .getMessage("qalayout.error", new Object[] {
                                        qaWorksheetId, "payment productivity" },
                                Locale.ENGLISH));

            } else {

//                model.addAttribute("QA_PROD_SAMPLE_DATA_LIST",
//                        qaProductivitiesSampleList);
                qaWorksheetLayoutPaymentResponse.put("QA_PROD_SAMPLE_DATA_LIST",qaProductivitiesSampleList);

            }

        } catch (EntityNotFoundException efe) {
//            model.addAttribute(Constants.ERROR, this.messageSource.getMessage(
//                    "qalayout.no.record", new Object[] { "ar productivity" },
//                    Locale.ENGLISH));

        } catch (Exception e) {
            // LOGGER.error(e);
        }
        // model.addAttribute("QA_WORKSHEET", qaWorksheet);
        qaWorksheetLayoutPaymentResponse.put("QA_WORKSHEET",qaWorksheet);
    }

    private QaWorkSheetResponse getQAWorkSheetResponse(QAWorksheet qaWorksheet) {
        QaWorkSheetResponse qaWorkSheetResponse = new QaWorkSheetResponse();
        qaWorkSheetResponse.setDeleted(qaWorksheet.getDeleted());
        qaWorkSheetResponse.setBillingMonth(qaWorksheet.getBillingMonth());
        qaWorkSheetResponse.setAccountPercentage(qaWorksheet.getAccountPercentage());
        qaWorkSheetResponse.setName(qaWorksheet.getName());
        qaWorkSheetResponse.setArStatusCode(qaWorksheet.getArStatusCode());
        //  qaWorkSheetResponse.setDepartmentName(qaWorksheet.getDepartmentName());
        qaWorkSheetResponse.setStatus(qaWorksheet.getStatus());
        // qaWorkSheetResponse.setSubDepartment(qaWorksheet.getSubDepartment());
        qaWorkSheetResponse.setGeneralPercentage(qaWorksheet.getGeneralPercentage());
        qaWorkSheetResponse.setPostingDateFrom(qaWorksheet.getPostingDateFrom());
        qaWorkSheetResponse.setPostingDateTo(qaWorksheet.getPostingDateTo());
        qaWorkSheetResponse.setScanDateTo(qaWorksheet.getScanDateTo());
        qaWorkSheetResponse.setScanDateFrom(qaWorksheet.getScanDateFrom());
        qaWorkSheetResponse.setDepartmentName(qaWorksheet.getDepartmentName());

        return qaWorkSheetResponse;

    }

    private List<QAProductivitySampling> fetchAllProductivitySamplingData(
            String departmentId, Integer qaWorksheetId, String orderBy)
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

    @PostMapping(value = "/insertqcpoints")
    public boolean insertQCPoint(@RequestParam("qaPatientInfoId") String qaPatientInfoId,
                                 @RequestParam("sampleId") String sampleId,
                                 @RequestParam("qcPointIds") List<String> qcPointIds,
                                 @RequestParam("userId") String userId) throws ArgusException {


        try {
            if (qaPatientInfoId != null
                    && !qaPatientInfoId.equals("")) {
                // delete all qc check list for sample
                qcPointChecklistDao.deleteByQAProductivitySamplingId(Long
                        .valueOf(sampleId), Long
                        .valueOf(qaPatientInfoId));
            } else {
                // delete all qc check list for sample
                qcPointChecklistDao.deleteByQAProductivitySamplingId(Long
                        .valueOf(sampleId));
            }

            if (qcPointIds != null
                    && !qcPointIds.equals("")) {

//                    String[] qcPointIds = request.getParameter("qcPointIds")
//                            .split(",");

                if (qcPointIds != null && qcPointIds.size() > 0) {
                    QAProductivitySampling qaProductivitySampling = null;
                    try {
                        qaProductivitySampling = qaProductivitySamplingDao
                                .findById(Long.valueOf(sampleId));
                    } catch (ArgusException ex) {
                        throw new RuntimeException(ex);
                    }

                    UserEntity user = userDao.findById(
                            Long.valueOf(userId),
                            false);

                    // for AR where we don't have qcPatientInfo
                    QAWorksheetPatientInfo qaWorksheetPatientInfo = null;
                    if (qaPatientInfoId != null
                            && !qaPatientInfoId
                            .equals("")) {
                        qaWorksheetPatientInfo = qaWorksheetPatientInfoDao
                                .findById(Long.valueOf(qaPatientInfoId));
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

                        try {
                            qcPointChecklistDao.save(qcPointChecklist);
                        } catch (ArgusException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
            }
            return true;

        } catch (Exception e) {

        }

        return false;
    }

    @PostMapping("/fetchqcpointchecklist")
    public ResponseEntity<?> fetchQcPointChecklist(@RequestParam String sampleId,
                                                   @RequestParam String qaPatientInfoId) {


        List<QCPointChecklist> qcPointChecklists = null;
        Map<String, String> whereClauses = new HashMap<String, String>();

        try {


            if (sampleId != null) {
                whereClauses.put(Constants.QA_WORKSHEET_SAMPLE_ID,
                        sampleId);
            }

            if (qaPatientInfoId != null
                    && !qaPatientInfoId
                    .equals("")) {
                whereClauses.put(Constants.QA_PATIENT_INFO_ID,
                        qaPatientInfoId);
            }

            qcPointChecklists = qcPointChecklistDao.findAll(whereClauses,
                    null, true);


        } catch (Exception e) {

        }
        List<QcPointRecord> qcPointList = new ArrayList<>();
        QCPointResponse qcPointResponse = new QCPointResponse();
        if (qcPointChecklists != null && qcPointChecklists.size() > 0) {
            // return qcPointChecklists;

            for (QCPointChecklist qcPointChecklist : qcPointChecklists) {
                QcPointRecord qcPoint = new QcPointRecord();
                qcPoint.setId(qcPointChecklist.getQcPoint().getId().toString());
                qcPoint.setName(qcPointChecklist.getQcPoint().getName());
                qcPointList.add(qcPoint);
                //   qcPointIds.add(Long.valueOf(qcPointChecklist.getQcPoint().getId()));
            }
            qcPointResponse.setQcPoint(qcPointList);
            return ResponseEntity.ok().body(qcPointResponse);
        } else {
            return ResponseEntity.ok().body("No QC point found!");
        }

        // return qcPointIds;
    }

    @GetMapping(value = "/execute/{id}")
    public StringBuffer executeQAWorksheet(@PathVariable Integer id) {
        StringBuffer msg = new StringBuffer();
        try {
            QAWorksheet qaWorksheet = qaWorksheetRepository.findById(id).get();

            if (qaWorksheet != null) {
                // delete all the existing samples for this qa worksheet in case
                // of re-execute (This should be )
                Map<String, String> whereClause = new HashMap<String, String>();
                Map<String, String> orderByClause = new HashMap<String, String>();

                whereClause.put(Constants.QA_WORKSHEET_MONTH, qaWorksheet
                        .getBillingMonth().toString());

                whereClause.put(Constants.QA_WORKSHEET_YEAR, qaWorksheet
                        .getBillingYear().toString());

                // whereClause.put(Constants.DEPARTMENT_ID,
                // qaWorksheet.getDepartment().getId().toString());

                if (qaWorksheet.getPostingDateFrom() != null) {
                    whereClause.put(Constants.POSTING_DATE_FROM, qaWorksheet
                            .getPostingDateFrom().toString());
                }

                if (qaWorksheet.getPostingDateTo() != null) {
                    whereClause.put(Constants.POSTING_DATE_TO, qaWorksheet
                            .getPostingDateTo().toString());
                }

                if (qaWorksheet.getScanDateFrom() != null) {
                    whereClause.put(Constants.SCAN_DATE_FROM, qaWorksheet
                            .getScanDateFrom().toString());
                }

                if (qaWorksheet.getScanDateTo() != null) {
                    whereClause.put(Constants.SCAN_DATE_TO, qaWorksheet
                            .getScanDateTo().toString());
                }

                List<QAWorksheetStaff> qaWorksheetStaffs = null;
                List<QAWorksheetDoctor> qaWorksheetDoctors = null;
                if(qaWorksheet.getType() != null) {
                    if (qaWorksheet.getType().equals(Constants.QA_WORKSEET_TYPE_BYSTAFF)) {
                        Map<String, String> qaWorksheetStaffWhereClauses = new HashMap<String, String>();
                        qaWorksheetStaffWhereClauses.put(Constants.QAWORKSHEET_ID,
                                qaWorksheet.getId().toString());

                        // find staff by worksheet id
                        qaWorksheetStaffs = this.qaWorksheetStaffDao.findAll(null,
                                qaWorksheetStaffWhereClauses, true);
                    } else if (qaWorksheet.getType().equals(Constants.QA_WORKSEET_TYPE_BYDOCTOR)) {
                        Map<String, String> qaWorksheetDoctorWhereClauses = new HashMap<String, String>();
                        qaWorksheetDoctorWhereClauses.put(Constants.QAWORKSHEET_ID,
                                qaWorksheet.getId().toString());

                        // find staff by worksheet id
                        qaWorksheetDoctors = this.qaWorksheetDoctorDao.findAll(
                                null, qaWorksheetDoctorWhereClauses, true);
                    }
                }
                boolean allowDuplicateTicketNumber = true;
                if (qaWorksheet.getDepartment().getId().toString().equals(Constants.CHARGE_DEPARTMENT_ID)) {

                    // fetch records from charge productivity
                    if (qaWorksheet.getSubDepartment() != null) {
                        // build sub department criteria for ChargeProductivity
                        String subDepartmentId = qaWorksheet.getSubDepartment()
                                .getId().toString();
                        if (subDepartmentId
                                .equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_DEMO)) {
                            whereClause.put(Constants.CHARGE_DEPARTMENT_DEMO,
                                    subDepartmentId);
                        } else if (subDepartmentId
                                .equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CE)) {
                            whereClause.put(Constants.CHARGE_DEPARTMENT_CE,
                                    subDepartmentId);
                        } else if (subDepartmentId
                                .equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_PRIMARY)) {
                            whereClause.put(
                                    Constants.CHARGE_DEPARTMENT_CODING_PRIMARY,
                                    subDepartmentId);
                        } else if (subDepartmentId
                                .equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_CODING_SPECIAL)) {
                            whereClause.put(
                                    Constants.CHARGE_DEPARTMENT_CODING_SPECIAL,
                                    subDepartmentId);
                        }
                    } else {
                        allowDuplicateTicketNumber = false;
                    }

                    if (qaWorksheetStaffs != null
                            && qaWorksheetStaffs.size() != 0) {

                        int calPerValue = 0;
                        List<String> userNameList = new ArrayList<String>();
                        Integer percentage = 0;
                        int total = 0;
                        for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {

                            whereClause.put(Constants.USER, qaWorksheetStaff
                                    .getUser().getId().toString());

                            int totalRecord = this.chargeProductivityDao
                                    .totalRecord(whereClause);

                            // // calculate all the percentage
                            // calPerValue =
                            // qaWorksheetStaff.getPercentageValue()
                            // .intValue();
                            // percentage = calPerValue * (totalRecord / 100);
                            if (totalRecord > 0) {
                                // calculate all the percentage
								/*calPerValue = qaWorksheetStaff
										.getPercentageValue().intValue();*/
                                calPerValue = qaWorksheet.getAccountPercentage().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;
                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);
                                fillQAProductivity(qaWorksheet,
                                        chargeProductivities,
                                        allowDuplicateTicketNumber);

                                // QA worksheet staff full name
                                userNameList.add(qaWorksheetStaff.getUser()
                                        .getFirstName()
                                        + " "
                                        + qaWorksheetStaff.getUser()
                                        .getLastName());
                            }
                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.staff.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                userNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));

                    } else if (qaWorksheetDoctors != null
                            && qaWorksheetDoctors.size() != 0) {
                        int calPerValue = 0;
                        List<String> doctorNameList = new ArrayList<String>();

                        Integer percentage = 0;
                        int total = 0;

                        for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
                            whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
                                    .getDoctor().getId().toString());
                            int totalRecord = this.chargeProductivityDao
                                    .totalRecord(whereClause);

                            // // calculate all the percentage
                            // calPerValue =
                            // qaWorksheetStaff.getPercentageValue()
                            // .intValue();
                            // percentage = calPerValue * (totalRecord / 100);
                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetDoctor
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;
                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);
                                fillQAProductivity(qaWorksheet,
                                        chargeProductivities,
                                        allowDuplicateTicketNumber);

                                // QA worksheet staff full name
                                doctorNameList.add(qaWorksheetDoctor
                                        .getDoctor().getName());
                            }

                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.doctor.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                doctorNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    } else {
                        int totalRecord = this.chargeProductivityDao
                                .totalRecord(whereClause);

                        Integer percentage = 0;

                        if (totalRecord > 0) {
                            // calculate all the percentage
                            int generalPercentage = qaWorksheet
                                    .getGeneralPercentage().intValue();
                            // Integer percentage = generalPercentage
                            // * (totalRecord / 100);

                            percentage = (int) Math.ceil(generalPercentage
                                    * ((float) totalRecord / 100));
                        }

                        // if percentage is 0 no need to pull up records
                        if (percentage != null && percentage > Constants.ZERO) {

                            orderByClause.put(Constants.RANDOM_RECOREDS,
                                    Constants.RANDOM_RECOREDS);
                            orderByClause.put(Constants.OFFSET, 0 + "");
                            orderByClause.put(Constants.LIMIT,
                                    percentage.toString());

                            List<ChargeProductivity> chargeProductivities = this.chargeProductivityDao
                                    .findAll(orderByClause, whereClause, false);


                            fillQAProductivityFromQAWorksheet(qaWorksheet,
                                    chargeProductivities,
                                    allowDuplicateTicketNumber, "chargeBatch");

                            orderByClause.clear();
                        }
                        msg.append(percentage +" record found for "+ qaWorksheet.getName()+ " worksheet ("+
                                qaWorksheet.getDepartment().getName()+")."
                        );
//                        msg.append(this.messageSource.getMessage(
//                                "record.found.message", new Object[] {
//                                        percentage,
//                                        qaWorksheet.getDepartment().getName(),
//                                        "'" + qaWorksheet.getName() + "'" },
//                                Locale.ENGLISH));
                    }

                }

                else if (qaWorksheet.getDepartment().getId().toString().equals(Constants.PAYMENT_DEPARTMENT_ID)) {

                    // fetch records from payment productivity

                    if (qaWorksheetStaffs != null
                            && qaWorksheetStaffs.size() != 0) {
                        int calPerValue = 0;
                        List<String> userNameList = new ArrayList<String>();
                        Integer percentage = 0;
                        int total = 0;
                        for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
                            whereClause.put(Constants.USER, qaWorksheetStaff
                                    .getUser().getId().toString());

                            int totalRecord = this.paymentProductivityDao
                                    .totalRecord(whereClause);

                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetStaff
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;

                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);

                                fillQAProductivity(qaWorksheet,
                                        paymentProductivities, true);

                                // QA worksheet staff full name
                                userNameList.add(qaWorksheetStaff.getUser()
                                        .getFirstName()
                                        + " "
                                        + qaWorksheetStaff.getUser()
                                        .getLastName());
                            }
                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.staff.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                userNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    } else if (qaWorksheetDoctors != null
                            && qaWorksheetDoctors.size() != 0) {

                        int calPerValue = 0;
                        List<String> doctorNameList = new ArrayList<String>();

                        Integer percentage = 0;
                        int total = 0;

                        for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
                            whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
                                    .getDoctor().getId().toString());
                            int totalRecord = this.paymentProductivityDao
                                    .totalRecord(whereClause);

                            // // calculate all the percentage
                            // calPerValue =
                            // qaWorksheetStaff.getPercentageValue()
                            // .intValue();
                            // percentage = calPerValue * (totalRecord / 100);
                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetDoctor
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;
                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);

                                fillQAProductivity(qaWorksheet,
                                        paymentProductivities, true);

                                // QA worksheet staff full name
                                doctorNameList.add(qaWorksheetDoctor
                                        .getDoctor().getName());
                            }

                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.doctor.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                doctorNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    } else {

                        int totalRecord = this.paymentProductivityDao
                                .totalRecord(whereClause);

                        // calculate all the percentage
                        Integer percentage = 0;

                        if (totalRecord > 0) {
                            percentage = (int) Math.ceil(qaWorksheet
                                    .getGeneralPercentage().intValue()
                                    * ((float) totalRecord / 100));

                            // percentage = * (totalRecord / 100);
                        }

                        // if percentage is 0 no need to pull up records
                        if (percentage != null && percentage > Constants.ZERO) {

                            orderByClause.put(Constants.RANDOM_RECOREDS,
                                    Constants.RANDOM_RECOREDS);
                            orderByClause.put(Constants.OFFSET, 0 + "");
                            orderByClause.put(Constants.LIMIT,
                                    percentage.toString());

                            List<PaymentProductivity> paymentProductivities = this.paymentProductivityDao
                                    .findAll(orderByClause, whereClause, false);

                            fillQAProductivityFromQAWorksheet(qaWorksheet,
                                    paymentProductivities, true,"");

                            orderByClause.clear();
                        }
                        msg.append(percentage +" record found for "+ qaWorksheet.getName()+ " worksheet ("+
                                qaWorksheet.getDepartment().getName()+")."
                        );

//                        msg.append(this.messageSource.getMessage(
//                                "record.found.message", new Object[] {
//                                        percentage,
//                                        qaWorksheet.getDepartment().getName(),
//                                        "'" + qaWorksheet.getName() + "'" },
//                                Locale.ENGLISH));
                    }

                } else if (qaWorksheet.getDepartment().getId().toString().equals(Constants.AR_DEPARTMENT_ID)) {

                    // fetch records from ar productivity
                    if (qaWorksheet.getSubDepartment() != null) {
                        // build sub department criteria for ChargeProductivity
                        whereClause.put(Constants.SUB_DEPARTMENT_ID,
                                qaWorksheet.getSubDepartment().getId()
                                        .toString());
                    }

                    if (qaWorksheet.getArStatusCode() != null
                            && !qaWorksheet.getArStatusCode().trim().equals("")) {
                        whereClause.put(Constants.STATUS_CODE,
                                qaWorksheet.getArStatusCode());
                    }

                    if (qaWorksheetStaffs != null
                            && qaWorksheetStaffs.size() != 0) {
                        int calPerValue = 0;
                        List<String> userNameList = new ArrayList<String>();
                        Integer percentage = 0;
                        int total = 0;
                        for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
                            whereClause.put(Constants.USER, qaWorksheetStaff
                                    .getUser().getId().toString());

                            int totalRecord = this.arProductivityDao
                                    .totalRecord(whereClause);

                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetStaff
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;
                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<ArProductivity> arProductivities = this.arProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);

                                fillQAProductivity(qaWorksheet,
                                        arProductivities, true);

                                // QA worksheet staff full name
                                userNameList.add(qaWorksheetStaff.getUser()
                                        .getFirstName()
                                        + " "
                                        + qaWorksheetStaff.getUser()
                                        .getLastName());
                            }
                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.staff.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                userNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    } else if (qaWorksheetDoctors != null
                            && qaWorksheetDoctors.size() != 0) {

                        int calPerValue = 0;
                        List<String> doctorNameList = new ArrayList<String>();

                        Integer percentage = 0;
                        int total = 0;

                        for (QAWorksheetDoctor qaWorksheetDoctor : qaWorksheetDoctors) {
                            whereClause.put(Constants.DOCTOR, qaWorksheetDoctor
                                    .getDoctor().getId().toString());
                            int totalRecord = this.arProductivityDao
                                    .totalRecord(whereClause);

                            // // calculate all the percentage
                            // calPerValue =
                            // qaWorksheetStaff.getPercentageValue()
                            // .intValue();
                            // percentage = calPerValue * (totalRecord / 100);
                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetDoctor
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }

                            total += percentage;
                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<ArProductivity> arProductivities = this.arProductivityDao
                                        .findAll(orderByClause, whereClause,
                                                false);

                                fillQAProductivity(qaWorksheet,
                                        arProductivities, true);

                                // QA worksheet staff full name
                                doctorNameList.add(qaWorksheetDoctor
                                        .getDoctor().getName());
                            }

                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.doctor.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                doctorNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));

                    } else {

                        int totalRecord = this.arProductivityDao
                                .totalRecord(whereClause);

                        Integer percentage = 0;
                        // calculate all the percentage
                        // Integer percentage =
                        // qaWorksheet.getGeneralPercentage()
                        // .intValue() * (totalRecord / 100);
                        if (totalRecord > 0 && qaWorksheet
                                .getGeneralPercentage()!=null) {
                            percentage = (int) Math.ceil(qaWorksheet
                                    .getGeneralPercentage().intValue()
                                    * ((float) totalRecord / 100));

                            // percentage = * (totalRecord / 100);
                        }

                        // if percentage is 0 no need to pull up records
                        if (percentage != null && percentage > Constants.ZERO) {

                            orderByClause.put(Constants.RANDOM_RECOREDS,
                                    Constants.RANDOM_RECOREDS);
                            orderByClause.put(Constants.OFFSET, 0 + "");
                            orderByClause.put(Constants.LIMIT,
                                    percentage.toString());

                            List<ArProductivity> arProductivities = this.arProductivityDao
                                    .findAll(orderByClause, whereClause, false);

                            fillQAProductivityFromQAWorksheet(qaWorksheet,
                                    arProductivities, true,"");

                            orderByClause.clear();
                        }
//                        msg.append(this.messageSource.getMessage(
//                                "record.found.message", new Object[] {
//                                        percentage,
//                                        qaWorksheet.getDepartment().getName(),
//                                        "'" + qaWorksheet.getName() + "'" },
//                                Locale.ENGLISH));
                        //3 record found for 'abc_test' worksheet (Accounts Receivable Department).
                        msg.append(percentage +" record found for "+ qaWorksheet.getName()+ " worksheet ("+
                                qaWorksheet.getDepartment().getName()+")."
                                );
                    }

                } else if (qaWorksheet.getDepartment().getId().toString().equals(Constants.ACCOUNTING_DEPARTMENT_ID)) {
                    // fetch records from accounting productivity

                    if (qaWorksheetStaffs != null
                            && qaWorksheetStaffs.size() != 0) {
                        int calPerValue = 0;
                        List<String> userNameList = new ArrayList<String>();
                        Integer percentage = 0;
                        int total = 0;
                        for (QAWorksheetStaff qaWorksheetStaff : qaWorksheetStaffs) {
                            whereClause.put(Constants.USER, qaWorksheetStaff
                                    .getUser().getId().toString());

                            int totalRecord = this.credentialingAccountingProductivityDao
                                    .totalRecord(whereClause);
                            // // calculate all the percentage
                            // calPerValue =
                            // qaWorksheetStaff.getPercentageValue()
                            // .intValue();
                            // percentage = calPerValue * (totalRecord / 100);
                            if (totalRecord > 0) {
                                // calculate all the percentage
                                calPerValue = qaWorksheetStaff
                                        .getPercentageValue().intValue();
                                percentage = (int) Math.ceil(calPerValue
                                        * ((float) totalRecord / 100));
                            }
                            total += percentage;

                            // if percentage is 0 no need to pull up records
                            if (percentage != null
                                    && percentage > Constants.ZERO) {

                                orderByClause.put(Constants.RANDOM_RECOREDS,
                                        Constants.RANDOM_RECOREDS);
                                orderByClause.put(Constants.OFFSET, 0 + "");
                                orderByClause.put(Constants.LIMIT,
                                        percentage.toString());

                                List<CredentialingAccountingProductivity> caProductivities = this.credentialingAccountingProductivityDao
                                        .findAllJPQL(orderByClause,
                                                whereClause, false);

                                fillQAProductivity(qaWorksheet,
                                        caProductivities, true);

                                // QA worksheet staff full name
                                userNameList.add(qaWorksheetStaff.getUser()
                                        .getFirstName()
                                        + " "
                                        + qaWorksheetStaff.getUser()
                                        .getLastName());
                            }
                            orderByClause.clear();
                        }

                        msg.append(this.messageSource.getMessage(
                                "record.found.staff.message",
                                new Object[] {
                                        total,
                                        "'"
                                                + StringUtils.join(
                                                userNameList, ", ")
                                                + "'",
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    } else {
                        int totalRecord = this.credentialingAccountingProductivityDao
                                .totalRecord(whereClause);

                        // calculate all the percentage
                        Integer percentage = 0;
                        // qaWorksheet.getGeneralPercentage()
                        // .intValue() * (totalRecord / 100);

                        if (totalRecord > 0) {
                            percentage = (int) Math.ceil(qaWorksheet
                                    .getGeneralPercentage().intValue()
                                    * ((float) totalRecord / 100));
                        }

                        // if percentage is 0 no need to pull up records
                        if (percentage != null && percentage > Constants.ZERO) {

                            orderByClause.put(Constants.RANDOM_RECOREDS,
                                    Constants.RANDOM_RECOREDS);
                            orderByClause.put(Constants.OFFSET, 0 + "");
                            orderByClause.put(Constants.LIMIT,
                                    percentage.toString());

                            List<CredentialingAccountingProductivity> caProductivities = this.credentialingAccountingProductivityDao
                                    .findAllJPQL(orderByClause, whereClause,
                                            false);

                            fillQAProductivityFromQAWorksheet(qaWorksheet,
                                    caProductivities, true,"");

                            orderByClause.clear();
                        }
                        msg.append(this.messageSource.getMessage(
                                "record.found.message", new Object[] {
                                        percentage,
                                        qaWorksheet.getDepartment().getName(),
                                        "'" + qaWorksheet.getName() + "'" },
                                Locale.ENGLISH));
                    }
                }
            }
        } catch (Exception e) {
          //  LOGGER.error(Constants.EXCEPTION, e);
            // msg.append("Record already exist");
        }

        return msg;
    }

    @GetMapping("/continue")
    public ResponseEntity<?> continueQAWorksheet(@RequestParam(value = "id") Integer id, @RequestParam("orderby") String orderby ) {
        try {
            QAWorksheet qaworksheet = this.qaWorksheetDao.findById(id);
            if (qaworksheet != null) {

                String orderBy = "";
                if (orderby != null) {
                    orderBy = orderby;
                }

                if (qaworksheet.getDepartment().getId().toString()
                        .equalsIgnoreCase(Constants.PAYMENT_DEPARTMENT_ID)) {
                    Map<String,Object> qaWorksheetLayoutPaymentResponse = new HashMap<>();
                    extractQAWorkSheeLayout(qaworksheet.getId(), orderBy, qaWorksheetLayoutPaymentResponse);
                    return ResponseEntity.ok().body(qaWorksheetLayoutPaymentResponse);
                }
//                } else if (qaworksheet.getDepartment().getId().toString()
//                        .equalsIgnoreCase(Constants.CHARGE_DEPARTMENT_ID)) {
//                    return "redirect:/qaworksheetlayout/coding/?qaworksheetid="
//                            + id + "&orderby=" + orderBy;
//                } else if (qaworksheet.getDepartment().getId().toString()
//                        .equalsIgnoreCase(Constants.AR_DEPARTMENT_ID)) {
//                    return "redirect:/qaworksheetlayout/ar/?qaworksheetid="
//                            + id + "&orderby=" + orderBy;
//                }
            }


        } catch (Exception e) {
//            LOGGER.error(e);
//            session.setAttribute(Constants.ERROR, e.getMessage());
        }
        return ResponseEntity.ok().body("");
    }
    private void fillQAProductivity(QAWorksheet qaWorksheet,
                                    List<?> productivities, boolean allowDuplicateTicketNumbers)
            throws ArgusException {

        boolean updateFlag = false;
        boolean saveFlag = false;

        List<Long> ticketNumberList = new ArrayList<Long>();

        // shuffle the list
        if (productivities != null && productivities.size() > 0) {
            // no need to shuffle we are pulling up random records from db
            /*
             * long seed = System.nanoTime();
             * Collections.shuffle(productivities, new Random(seed));
             */

            UserEntity createdBy = AkpmsUtil.getLoggedInUser();

            for (Object object : productivities) {
                saveFlag = true;
                QAProductivitySampling qaSampling = new QAProductivitySampling();

                qaSampling.setQaWorksheet(qaWorksheet);

                if (object instanceof PaymentProductivity) {
                    qaSampling
                            .setPaymentProductivity((PaymentProductivity) object);

                } else if (object instanceof ChargeProductivity) {

                    ChargeProductivity chargeProductivity = (ChargeProductivity) object;

                    if (allowDuplicateTicketNumbers) {
                        qaSampling
                                .setChargeProductivity((ChargeProductivity) object);
                    } else {

                        // check if already in list, then ignore
                        if (!ticketNumberList.contains(chargeProductivity
                                .getTicketNumber().getId())) {
                            qaSampling
                                    .setChargeProductivity((ChargeProductivity) object);

                            ticketNumberList.add(Long.valueOf(chargeProductivity
                                    .getTicketNumber().getId()));
                        } else {
                            saveFlag = false;
                        }
                    }

                } else if (object instanceof ArProductivity) {
                    qaSampling.setArProductivity((ArProductivity) object);

                } else if (object instanceof CredentialingAccountingProductivity) {
                    qaSampling
                            .setCredentialingAccountingProductivity((CredentialingAccountingProductivity) object);
                }

                if (saveFlag) {
                    qaSampling.setCreatedOn(new Date());
                    qaSampling.setCreatedBy(createdBy);
                    // qaSampling.setDepartmentId(qaWorksheet.getDepartment());
                    qaProductivitySamplingDao.save(qaSampling);
                }
            }
            if (updateFlag == false) {
                updateFlag = true;
            }
        }

        // Update status in qaworksheet as executed
        if (updateFlag == true) {
            qaWorksheet.setStatus(Constants.ONE);
            try {
                this.qaWorksheetDao.update(qaWorksheet);
            } catch (ArgusException e) {
              //  LOGGER.error("Error merging QAWorksheet", e);
            }
        }
    }

    private void fillQAProductivityFromQAWorksheet(QAWorksheet qaWorksheet,
                                                   List<?> productivities, boolean allowDuplicateTicketNumbers, String processType)
            throws ArgusException {

        boolean updateFlag = false;
        boolean saveFlag = false;


        List<Long> ticketNumberList = new ArrayList<Long>();
        // shuffle the list
        if (productivities != null && productivities.size() > 0) {
            // no need to shuffle records we are already pulling random records
            // from db
            /*
             * long seed = System.nanoTime();
             * Collections.shuffle(productivities, new Random(seed));
             */

          //  UserEntity createdBy = AkpmsUtil.getLoggedInUser();
            String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal());
            UserEntity userEntity = userDataRestRepository.findByNameForAdmin(email);

            for (Object object : productivities) {
                saveFlag = true;
                QAProductivitySampling qaSampling = new QAProductivitySampling();

                qaSampling.setQaWorksheet(qaWorksheet);

                if (object instanceof PaymentProductivity) {
                    qaSampling
                            .setPaymentProductivity((PaymentProductivity) object);

                } else if (processType.equals("chargeBatch")) {
                    // qaSampling
                    // .setChargeProductivity((ChargeProductivity) object);

                    ChargeProductivity chargeProductivity = new ChargeProductivity();
                    chargeProductivity.setId(Integer.valueOf(((Object[])object)[0].toString()));
                    chargeProductivity.setProductivityType(((Object[])object)[2].toString());
                    chargeProductivity.setRemarks(((Object[])object)[3].toString());
                    chargeProductivity.setWorkFlow( ((Object[])object)[4].toString());
                    chargeProductivity.setScanDate((Date) ((Object[])object)[5]);
                 ChargeBatchProcessing chargeBatchProcessin =
                          chargeBatchProcessingRepository.findByTicketNumber(((Object[])object)[1].toString());

                 ChargeBatchProcessing chargeBatchProcessing = new ChargeBatchProcessing();
                 chargeBatchProcessing.setId(chargeBatchProcessin.getId());

//                 List<ChargeBatchProcessing> chargeBatchProcessingList = new ArrayList<>();
//                         chargeBatchProcessingIterator.forEachRemaining(chargeBatchProcessingList:: add);
//
//                   ChargeBatchProcessing chargeBatchProcessingFilter = chargeBatchProcessingList.stream()
//                            .filter(ticketNumber -> ticketNumber.getId().equals(((Object[])object)[1].toString()))
//                                    .findFirst().get();
                    chargeProductivity.setTicketNumber(chargeBatchProcessing);

                    if (allowDuplicateTicketNumbers) {
                        qaSampling
                                .setChargeProductivity(chargeProductivity);
                    } else {

                        // check if already in list, then ignore
                        if (!ticketNumberList.contains(chargeProductivity
                                .getTicketNumber().getId())) {
                            qaSampling
                                    .setChargeProductivity(chargeProductivity);

                            ticketNumberList.add(Long.valueOf(chargeProductivity
                                    .getTicketNumber().getId()));
                        } else {
                            saveFlag = false;
                        }
                    }

                } else if (object instanceof ArProductivity) {
                    qaSampling.setArProductivity((ArProductivity) object);

                } else if (object instanceof CredentialingAccountingProductivity) {
                    qaSampling
                            .setCredentialingAccountingProductivity((CredentialingAccountingProductivity) object);
                }

                if (saveFlag) {

                    qaSampling.setCreatedOn(new Date());
                    qaSampling.setCreatedBy(userEntity);
                    // qaSampling.setDepartmentId(qaWorksheet.getDepartment());
                    qaProductivitySamplingDao.save(qaSampling);
                }
            }

            if (updateFlag == false) {
                updateFlag = true;
            }
        }
        // Update status in qaworksheet as executed
        if (updateFlag == true) {
            qaWorksheet.setStatus(Constants.ONE);
            this.qaWorksheetDao.update(qaWorksheet);
        }
    }
}
