package argus.util;

public interface Constants {

	Long ADMIN_ROLE_ID = 1L;

	Long DOCUMENT_MANAGER_ROLE_ID = 2L;

	Long STANDART_USER_ROLE_ID = 3L;

	Long TRAINEE_ROLE_ID = 4L;

	Long OTHER_ROLE_ID = 5L;

	String SELECTED_ROLES_IDS = "selectedRolesIds";

	String SELECTED_DEPARTMENTS_IDS = "selectedDepartmentsIds";

	String DEPARTMENT_WITH_CHILD = "departmentWithChild";

	// String SEARCH_TEXT = "searchText";
	String KEYWORD = "keyword";

	String STATUS = "status";

	String STATUS_CODE = "statusCode";

	String SUBSTATUS = "substatus";

	String FOLLOUPS = "followups";

	String SOURCE = "source";

	String RECORD_PRE_PAGE = "rp";

	String PAGE = "page";

	String OFFSET = "offset";

	String ISOFFSET = "isOffset";

	String SORT_ORDER = "sortorder";

	String SORT_BY = "sortBy";

	String SORT_NAME = "sortname";

	String ORDER_BY = "orderBy";

	String QTYPE = "qtype";

	String QUERY = "query";

	String BY_NAME = "name";

	String LIMIT = "limit";

	String REQUEST_ATTRIBUTE_PASSWORD = "password";

	String REQUEST_ATTRIBUTE_CREATED_BY = "createdBy";

	String ERROR = "error";

	String SUCCESS_UPDATE = "successUpdate";
	String SUCCESS_ADDED = "successAdded";
	String SUCCESS = "success";

	Long PROCESS_MANUAL_MODIFICATION_MAIL_TEMPLATE = new Long(1);

	Long TRAINEE_EVALUATION_TEMPLATE = new Long(2);

	Long ARGUS_REGISTRATION_MAIL_TEMPLATE = new Long(3);

	Long CHANGE_PASSWORD_TEMPLATE = new Long(4);

	Long FORGET_PASSWORD_TEMPLATE = new Long(5);

	String TYPE_IDS_TL = "IDS_TL";

	String TYPE_ARGUS_TL = "Argus_TL";

	String DEPENDANCY_FILES = "files";

	String DEPENDANCY_DEPARTMENTS = "departments";

	String DEPENDANCY_USERS = "users";

	String DEPENDANCY_SUB_PROCESS_MANUALS = "subProcessManuals";

	int IS_DELETED = 1;

	int NON_DELETED = 0;

	Boolean BOOLEAN_DELETED = true;

	Boolean BOOLEAN_NON_DELETED = false;

	int ACTIVATE = 1;

	int INACTIVATE = 0;

	Boolean BOOLEAN_ACTIVATE = true;

	Boolean BOOLEAN_INACTIVATE = false;

	String NO_ENTRY_FOUND_FOR_QUERY = "No entity found for query";

	String NO_RECORD_FOUND = "No Record Found.";

	String NO_PROCESS_MANUAL_FOUND = "No Process Manual Added.";

	String EXCEPTION = "Exception : ";

	int DASHBOARD_NOTIFICATION_COUNT = 5;

	String EDIT = "Edit";

	String REVISE = "Revise";

	String SAVE = "Save";

	String UPDATE = "Update";

	String VIEW = "View";

	String OPERATION_TYPE = "operationType";

	String BUTTON_NAME = "buttonName";

	String ADD = "Add";

	String ADD_MORE = "addMore";

	String PAYMENT = "payment";

	String REVENUETYPE = "revenuetype";

	String DOCTOR = "doctor";

	// String DOCTOR_CODE = "doctorCode";

	String INSURANCE = "insurance";

	String LOCATION = "location";

	String DELETED = "deleted";

	String FIRST_NAME = "firstName";

	String ROLE = "role";

	String MORE_THAN_ONE_DEPT_AVAILABLE = "more than 1 dept available";

	String USER = "user";

	String DEPARTMENTS = "departments";

	String DEPARTMENT_ID = "departmentId";

	String SUB_DEPARTMENT_ID = "subDepartmentId";

	String ARPRODUCTIVITY = "arProductivity";

	String ID = "id";

	String MODE = "mode";

	int ZERO = 0;

	String CHARGE_BATCH_PROCESS = "chargeBatchProcess";

	String WORK_FLOW_STATUS = "workFlowStatus";

	int WORKFLOW_APPROVE = 1;

	int WORKFLOW_REJECT = 2;

	int WORKFLOW_ESCALATE = 3;

	String ADJUSTMENTLOG = "adjustmentLogs";

	String CHANGE_PASSWORD = "changePassword";

	int ADJUSTMENT_LOG_WORKFLOW = 1;

	int CODING_CORRECTION_WORKFLOW = 2;

	int SECOND_REQUEST_LOG_WORKFLOW = 3;

	int REKEY_REQ_TO_CHARGE_POSTING_WORKFLOW = 4;

	int PAYMENT_POSTING_LOG_WORKFLOW = 5;

	int REQ_FOR_CHECK_TRACER_WORKFLOW = 6;

	int QUERY_TO_TL_WORKFLOW = 7;

	int REFUND_REQUEST_WORKFLOW = 8;

	int REQUEST_FOR_DOCS = 10;

	int STOP_PAYMENT_REISSUE = 9;

	int REQUEST_FOR_DOCS_WORKFLOW = 10;

	String WORKFLOW_ADJUSTMENT_LOG = "Adjustment log";

	String WORKFLOW_CODING_CORRECTION = "Coding correction";

	String WORKFLOW_SECOND_REQ_LOG = "Second request Log";

	String WORKFLOW_REKEY_REQ_TO_CHARGE_POSTING = "Re-Key request to charge posting";

	String WORKFLOW_PAYMENT_POSTING_LOG = "Payment posting log";

	String WORKFLOW_REQ_FOR_CHECK_TRACER = "Request for check tracer";

	String WORKFLOW_REFUND_REQUEST = "Refund Request";

	String WORKFLOW_REQUEST_FOR_DOCS = "Request for docs";

	String WORKFLOW_QUERY_TO_TL = "Query to TL";

	int PAYER_EDIT_SOURCE = 1;

	int UNPAID_INACTIVE_SOURCE = 2;

	int CORRESPONDENCE_SOURCE = 3;

	int DIVIDER_SET_TO_DENY_SOURCE = 4;

	int IPA_FFS_SOURCE = 5;

	int MISSING_INFO_SOURCE = 6;

	int HOURLY_SOURCE = 7;

	String SOURCE_PAYER_EDIT = "Payer Edit";

	String SOURCE_UNPAID_INACTIVE = "Unpaid / Inactive";

	String SOURCE_CORRESPONDENCE = "correspondence";

	String SOURCE_DIVIDER_SET_TO_DENY = "Divider / Set to deny";

	String SOURCE_IPA_FFS = "IPA-FFS";

	String SOURCE_MISSING_INFO = "Missing Info";

	String SOURCE_HOURLY = "Hourly";

	String ARPRODUCTIVITY_ID = "arProductivity.id";

	String READ_ONLY = "readOnly";

	String CHECK_WORK_FLOW = "checkWorkFlow";

	String CHECKTRACER = "checkTracer";

	String SUBMIT_FOR_APPROVAL = "Submit";

	String PAYMENTPRODUCTIVITY = "paymentProductivity";

	String PaymentProductivityRefundRequest = "paymentProductivityRefundRequest";

	String PaymentProductivityHourly = "paymentProductivityHourly";

	String GET_DETAILS = "Get Detail";

	String GET_BUTTON = "getButton";

	String CURRENT_DATE = "currentDate";

	String PAYMENTBATCH_ID = "paymentBatch.id";

	String CHK_NUMBER = "chkNumber";

	String MANUALLY_POSTED_AMT = "paymentBatch.manuallyPostedAmt";

	String ELEC_POSTED_AMT = "paymentBatch.elecPostedAmt";

	String SUSPENSE = "suspense";

	String SUSPENSE_ACCOUNT = "suspenseAccount";

	String AGENCY_INCOME = "agencyIncome";

	String OTHER_INCOME = "otherIncome";

	String OLD_PRIOR_AR = "oldPriorAr";

	String DATE_POSTED = "paymentBatch.datePosted";

	String MANUAL_TRANSACTION = "manualTransaction";

	String ELEC_TRANSACTION = "elecTransaction";

	String SCAN_DATE = "scanDate";

	String REMARK = "remark";

	String WORKFLOW_ID = "workflowId";

	String WORKFLOW = "workflow";

	String ON_HOLD = "onHold";

	int ERA_WORK_FLOW = 1;

	int NON_ERA_WORK_FLOW = 2;

	int CAP_WORK_FLOW = 3;

	int REFUND_REQUEST_WORK_FLOW = 4;

	int HOURLY_WORK_FLOW = 5;

	String CHARGE_BATCH_REPORT_TYPE = "reportType";

	/****** doctor type [start] ********/

	String DOCTOR_TYPE_PAYMENT = "payment";

	String DOCTOR_TYPE_AR = "ar";

	String DOCTOR_TYPE_CODING = "coding";

	/****** doctor type [end] ********/

	Long PRO_HEALTH_GROUP_ID = 1L;

	Long PAYMENT_TYPE_ADMIN_INCOME = 1L;

	Long MONEY_SOURCE_EFT = 1L;

	Long MONEY_SOURCE_LOCKBOX = 2L;

	Long PAYMENT_TYPE_OFFSET = 8L;

	Long PAYMENT_TYPE_OTC = 9L;

	Long REVENUE_TYPE_PATIENT_COLLECTION = 1L;

	String CHARGE_BATCH_PROCESSING_TICKET_RANGE = "ticketRange";

	String WORK_FLOW_TO_AR_IPA_FFS_HMO = "To AR IPA FFS HMO";

	String WORK_FLOW_TO_AR_FFS = "To AR FFS";

	String WORK_FLOW_TO_AR_CEP = "To AR CEP";

	String WORK_FLOW_TO_AR_MCL = "To AR MCL";

	String WORK_FLOW_TO_AR_MCR = "To AR MCR";

	String WORK_FLOW_TO_AR_WC = "To AR WC";

	String WORK_FLOW_OFFSET = "OFFSET";

	String WORK_FLOW_QUERY_TO_TL = "Query to TL";

	int TO_AR_IPA_FFS_HMO_WORK_FLOW = 1;

	int TO_AR_FFS_WORK_FLOW = 2;

	int TO_AR_CEP_WORK_FLOW = 3;

	int TO_AR_MCL_WORK_FLOW = 4;

	int TO_AR_MCR_WORK_FLOW = 7;

	int TO_AR_WC_WORK_FLOW = 8;

	int OFFSET_WORK_FLOW = 5;

	int QUERY_TO_TL_WORK_FLOW = 6;

	String CHARGE_DEPARTMENT_ID = "1"; // parent id of payment department

	String PAYMENT_DEPARTMENT_ID = "2"; // parent id of payment department

	String AR_DEPARTMENT_ID = "3"; // parent id of AR department

	String ACCOUNTING_DEPARTMENT_ID = "4"; // parent id of payment department

	//String CODING_CHARGE_POSTING = "1"; // parent id of payment department

	String CHARGE_DEPARTMENT_CE = "6"; // sub department under charges department

	String CHARGE_DEPARTMENT_CODING_PRIMARY = "7"; // sub department under charges department

	String CHARGE_DEPARTMENT_CODING_SPECIAL = "8"; // sub department under charges department

	String CHARGE_DEPARTMENT_DEMO = "9"; // sub department under charges department


	String PAYMENTPRODQUERYTOTL = "paymentProdQueryToTL";

	String TICKET_NUMBER = "ticketNumber";

	String INSURANCE_NAME = "insuranceName";

	String LOCATION_NAME = "locationName";

	String CHK_DATE = "chkDate";

	String ACCOUNT_NUMBER = "accountNumber";

	String SR_NO = "srNo";

	String CHECK = "check";

	String PATIENT_NAME = "patientName";

	String TIMILY_FILING = "timilyFiling";

	String AR_PROD_PATIENT_NAME = "arProductivity.patientName";

	String AR_PROD_PATIENT_ACC_NO = "arProductivity.patientAccNo";

	String AMOUNT = "amount";

	// search fields for payment batch list
	String MONTH = "month";

	String YEAR = "year";

	String DOCTOR_ID = "doctorId";

	String PH_DOCTOR_IDS = "phDoctorIds";

	String DATE_POSTED_FROM = "datePostedFrom";

	String DATE_POSTED_TO = "datePostedTo";

	String DATE_CREATED_FROM = "dateCreatedFrom";

	String DATE_CREATED_TO = "dateCreatedTo";

	String DATE_DEPOSIT_FROM = "dateDepositFrom";

	String DATE_DEPOSIT_TO = "dateDepositTo";

	String INSURANCE_ID = "insuranceId";

	String LOCATION_ID = "locationId";

	String TEAM_ID = "teamId";

	String CREATED_BY = "createdBy";

	String POSTED_BY = "postedBy";

	// String RECEIVED_BY = "receivedBy";

	String TRANSACTION_TYPE = "transactionType";

	String TRANSACTION = "transaction";

	String ERA = "ERA";

	String NON_ERA = "NON ERA";

	String CAP = "CAP";

	String REFUND_REQUEST = "Refund Request";

	String HOURLY = "Hourly";

	String PROD_TYPE = "prodType";

	String POSTED_BY_ID = "postedById";

	String SERACH_DATE = "searchDate";

	String REVENUE_TYPE_IDS = "revenueTypeIds";

	String PAYMENT_TYPE_IDS = "paymentTypeIds";

	String TICKET_NUMBER_IDS = "ticketNumberIds";

	String PAYMENT_TYPE = "paymenttype";

	String HOURLY_TASK = "hourlyTask";

	String MONEY_SOURCE_IDS = "moneySourceIds";

	String PAYMENT_BATCH = "paymentBatch";

	String CHARGE_BATCH_TYPE_OFFICEBATCH = "Office";

	String CHARGE_BATCH_TYPE_HOSPITALBATCH = "Hospital";

	String CHARGE_BATCH_TYPE_DISPLAYBATCH = "Display";

	String CHARGE_BATCH_TYPE_EKG = "EKG";

	String CHARGE_BATCH_TYPE_CHDPBATCH = "CHDP";

	String CHARGE_BATCH_TYPE_CAPBATCH = "CAP";

	String CHARGE_BATCH_TYPE_SNF = "SNF";

	String CHARGE_BATCH_TYPE_HOMEHEALTH = "Home Health";

	String CHARGE_BATCH_TYPE_SUBACUTE = "Sub Acute";

	String CHARGE_BATCH_TYPE_FACILITY = "Facility";

	String CHARGE_BATCH_TYPE_HOMEVISITS = "Home Visits";

	String CHARGE_BATCH_TYPE_REKEY = "Rekey";

	String CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH = "Posted Batch";

	String CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH = "Un-Posted Batch";

	String CHARGE_BATCH_REPORT_TYPE_BATCHWITHDISCREPANCY = "Batch with Discrepancy";

	String CHARGE_BATCH_PRODUCTIVITY_FFS = "FFS";

	String CHARGE_BATCH_PRODUCTIVITY_CAP = "CAP";

	String CHARGE_PRODUCTIVITY = "chargeProductivity";

	String CHARGE_PROD_REJECT = "chargeProdReject";

	String HIDE = "hide";

	String MYSQL_DATE_FORMAT = "yyyy-MM-dd";
	
	String PAYMENTPRODOFFSET = "paymentProdOffset";

	String WORKFLOW_OFFSET = "Offset";

	String RECEIVED_DATE = "receivedDate";

	String CHARGE_BATCH_WORK_FLOW_PAYMENT_REVERSAL = "Payment Reversal";

	String CHARGE_BATCH_WORK_FLOW_REJECT = "Reject";

	String PAYMENT_OFFSET_MANAGER = "paymentOffsetManager";

	String OFFSET_ID = "offset.id";

	String DATE_OF_CHECK = "dateOfCheck";

	String CHECK_NUMBER = "checkNumber";

	String BATCH_ID = "batchId";

	String PROVIDE = "provide";

	String PROVIDER_ID = "providerId";

	String DATE_RESOLUTION_FROM = "dateResolutionFrom";
	String DATE_RESOLUTION_TO = "dateResolutionTo";
	String DATE_TRANSACTION_FROM = "dateTransactionFrom";
	String DATE_TRANSACTION_TO = "dateTransactionTo";

	String PARENT_ID = "parentId";
	String PARENT_ONLY = "parentOnly";
	String CHILD_ONLY = "childOnly";

	String DATE_RECEIVED_TO = "dateReceivedTo";
	String DATE_RECEIVED_FROM = "dateReceivedFrom";

	String REFUNDREQUEST = "refundRequest";
	String CREDENTIALING_ACCOUNTING = "credentialingAccounting";

	String TICKET_NUMBER_FROM = "ticketNumberFrom";

	String TICKET_NUMBER_TO = "ticketNumberTo";

	String TICKET_NUMBER_SEARCH = "ticketNumberSearch";

	String ERA_CHECK_NO = "eraCheckNo";

	String EMAIL_HOST = "email.host";

	String EMAIL_FROM = "email.username";

	String EMAIL_PASSWORD = "email.password";

	String REJECTION_ID = "rejectId";

	String DOCTOR_NAME = "name";

	String PERMISSION = "permissions";

	String PERMISSION_OFFSET_MANAGER = "P-5";

	String PERMISSION_REVISE_CREATE_BATCH = "P-8";

	String SUB_ADMIN_ACCOUNTING = "P-13";

	String FIRST_REQUEST_DUE_COUNT = "firstRequestDueCount";

	String SECOND_REQUEST_DUE_COUNT = "secondRequestDueCount";

	String NUMBER_OF_FIRST_REQUESTS_COUNT = "numberOfFirstRequestsCount";

	String NUMBER_OF_SECOND_REQUESTS_COUNT = "numberOfSecondRequestsCount";

	String REJECTION_COUNT = "rejectionCount";

	String RESOLVED_REJECTION_COUNT = "resolvedRejectionCount";

	String COMPLETED_REJECTION_COUNT = "completedRejectionCount";

	String RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT = "resolvedRejectionWithDummyCPTCount";

	String OFFSET_RECORDS = "offsetRecords";

	String ERR = "err";

	int ONE = 1;

	int TWO = 2;

	int THREE = 3;

	String STRING_ONE = "1";

	String STRING_TWO = "2";

	String STRING_THREE = "3";

	String DATE_FORMAT = "MM/dd/yyyy";

	String TASK_NAME = "taskName";

	String RECEIVED_BY = "receivedBy";

	String RECEIVED = "received";

	String DOS_FROM = "dosFrom";

	String DOS_TO = "dosTo";

	String DATE_BATCH_POSTED_FROM = "dateBatchPostedFrom";

	String DATE_BATCH_POSTED_TO = "dateBatchPostedTo";

	String RECEIVED_BY_USERS = "receivedByUsers";

	String DOCTOR_LIST = "doctorList";

	String USER_LIST = "userList";

	String TEAM_LIST = "teamList";

	String MONTHS = "months";

	String YEARS = "years";

	String PARAM = "param";

	String ACTIVE_DEPTS = "activeDepts";

	String INACTIVE_DEPTS = "inactiveDepts";

	String ACTIVE_USERS = "activeUsers";

	String INACTIVE_USERS = "inactiveUsers";

	String ACTIVE_INSURANCES = "activeInsurances";

	String INACTIVE_INSURANCES = "inactiveInsurances";

	String ACTIVE_DOCTORS = "activeDoctors";

	String INACTIVE_DOCTORS = "inactiveDoctors";

	String ACTIVE_PAYMENTTYPES = "activePaymentTypes";

	String INACTIVE_PAYMENTTYPES = "inactivePaymentTypes";

	String MYSQL_DATE_FORMAT_WITH_TIME = "yyyy-MM-dd HH:mm:ss";

	int FOUR = 4;

	int FIVE = 5;

	int ELEVEN = 11;

	int TWELVE = 12;

	int EIGHT = 8;

	int HUNDRED = 100;

	int SIX = 6;

	int SEVEN = 7;

	int NINE = 9;

	int TEN = 10;

	String BATCH_POSTED = "batchPosted";

	String CHARGE_BATCH_PROCESSING_TICKET_RANGE_FROM = "ticketRangeFrom";

	String CHARGE_BATCH_PROCESSING_TICKET_RANGE_TO = "ticketRangeTo";

	String QUERY_WILDCARD_PERCENTAGE = "\\%";

	String QUERY_WILDCARD_UNDERSCORE = "\\_";

	String PRODUCTIVITY_ID = "productivityId";

	String REDIRECT_ERROR_403 = "redirect:/error403";

	String CREATED_ON = "createdOn";

	String SALT = "login@123";

	/********** payment batch list [transaction type] **********/
	String ALL_BATCH = "All";
	String POSTED_BATCH = "Posted";
	String NOT_POSTED_BATCH = "Not Posted";

	String SORT_ORDER_DESC = "DESC";

	String POPUP = "Popup";
	String CLOSE_POPUP = "closePopup";

	String SHOW_EXTRA_OPTIONS = "showExtraOptions";

	/* USE IN DEMO PRODUCTIVITY */
	String DEMO_EXISTING_CUSTOMER = "demoExistingCustomer";
	String DEMO_NEW_CUSTOMER = "demoNewCustomer";

	float DEMO_TIME_EXISTING_CUSTOMER = 6.67f;
	float DEMO_TIME_NEW_CUSTOMER = 10;

	String PAYMENT_POSTING_WORKFLOW = "paymentPostingWorkFlow";

	String STATUS_APPROVE = "Approve";
	String STATUS_REJECT = "Reject";
	String STATUS_PENDING = "Pending";

	String OFFSET_TICKET_NUMBER = "offsetTicketNumber";

	String NDBA = "ndba";

	String STATUS_AR_STEP_COMPLETED = "AR Step Completed";
	String STATUS_OFFSET_RESOLVE = "Offset Resolve";
	String STATUS_RESOLVE = "Resolved";
	String STATUS_COMPLETED = "Completed";

	String CHARGEABLE = "Chargeable";
	String NOT_CHARGEABLE = "Not Chargeable";

	String TASK_RECEIVED_DATE_FROM = "taskReceivedDateFrom";
	String TASK_RECEIVED_DATE_TO = "taskReceivedDateTo";
	String TASK_COMPLETED_DATE_FROM = "taskCompletedDateFrom";
	String TASK_COMPLETED_DATE_TO = "taskCompletedDateTo";

	String SCAN_DATE_FROM = "scanDateFrom";
	String SCAN_DATE_TO = "scanDateTo";

	String WITHOUT_TIMILY_FILING = "withoutTimilyFiling";

	/**************** Coding correction work flow names ********************/

	String ESCALATE_TO_ARGUS_TL = "Escalate to Argus TL";

	String FORWARD_TO_CODING_CORRECTION = "Forward to coding correction";

	String RETURN_TO_AR = "Return to AR";

	String DONE = "Done";

	String CODING_TO_CE = "Coding to CE";

	String PERMISSION_DOCUMENT_MANAGER = "P-14";

	/** Constant for QAManager **/

	String QAWORKSHEET = "qaworksheet";

	String QAWORKSHEET_ID = "qaWorksheet.id";

	String TYPE = "type";

	Short QA_WORKSEET_TYPE_BYSTAFF = 1;

	Short QA_WORKSEET_TYPE_GENERAL = 2;

	Short QA_WORKSEET_TYPE_BYDOCTOR = 3;

	String SAVE_QAWORKSHEET = "Save QA Worksheet";

	String UPDATE_QAWORKSHEET = "Update QA Worksheet";

	double PERCENT_LIMIT = 100.0;

	String LOCATION_LIST = "locationList";

	String PERMISSION_QA_WORKSHEET_MANAGER = "P-2";

	String SAMPLING_PRODUCTIVITY_ID = "productivity_id";

	String POSTING_DATE_FROM = "postingDateFrom";

	String POSTING_DATE_TO = "postingDateTo";

	String TIME_STAMP_FORMAT = "yyyy-MM-dd hh:mm:ss.S";

	String PARENT_QCPOINTS = "parentQcPoints";

	String CHILD_QCPOINTS = "childQcPoints";
	/**
	 * QAProductivitySampling constants
	 */
	String QA_PRODUCTIVITY_SAMPLING = "qaProductivitySampling";

	String QC_POINT_CHECKPOINT = "checkPoint";

	String SAMPLEID = "sampleid";

	String CPT_CODE_DEMO = "cptCodeDemo";

	String QA_PATIENT_INFO_ID = "qaPatientInfoId";

	String QA_WORKSHEET_SAMPLE_ID = "qaWorksheetSampleId";

	String DEPARTMENT = "department";

	String SUB_DEPARTMENT = "subDepartment";

	String PAYMENT_DEPARTMENT_NAME = "Payments Department";

	String CHARGE_DEPARTMENT_NAME = "Coding and Charge Entry Department";

	String AR_DEPARTMENT_NAME = "Accounts Receivable Department";

	String RANDOM_RECOREDS = "random";

	/**
	 * QAReport date constants
	 *
	 */
	String QA_REPORT_FROM_DATE = "qaReportFromDate";
	String QA_REPORT_TO_DATE = "qaReportToDate";

	String FOLLOW_UP_DATE_FROM = "followUpDateFrom";
	String FOLLOW_UP_DATE_TO = "followUpDateTo";

	String BATCH_PERCENTAGE = "batchPercentage";
	String ACCOUNT_COUNT = "accountCount";
	String CPT_COUNT = "cptCount";

	String QA_WORKSHEET_MONTH = "qaWorksheetMonth";

	String QA_WORKSHEET_YEAR = "qaWorksheetYear";

	String START_MONTH = "startMonth";

	String END_MONTH = "endMonth";

	String SUB_DEPARTMENT_NAME = "subDepartmentName";

	String ACCOUNTING_DEPARTMENT_NAME = "Accounting Department";

	String CHARGE_ENTRY_ID = "6";

	String CHARGE_ENTRY_NAME = "Charge Entry";

	String CODING_PRIMARY_CARE_ID = "7";
	
	String CODING_PRIMARY_CARE_NAME = "Coding Primary Care";
	
	String CODING_SPECIALIST_ID = "8";
	
	String CODING_SPECIALIST_NAME = "Coding Specialist";
	
	String DEMO_AND_VERIFICATION_ID = "9";
	
	String DEMO_AND_VERIFICATION_NAME = "Demo and Verification";
	
	String PAYMENT_POSTING_ID = "10";
	
	String PAYMENT_POSTING_NAME = "Payments Posting";
	
	String TREASURY_ID = "11";
	
	String TREASURY_NAME = "Treasury";
	
	String HMO_ID = "13";
	
	String HMO_NAME = "HMO";
	
	String MCL_ID = "14";
	
	String MCL_NAME = "MCL";
	
	String MCR_ID = "15";
	
	String MCR_NAME = "MCR";
	
	String PPO_ID = "16";
	
	String PPO_NAME = "PPO";
	
	String SELF_PAY_AND_CEP_ID = "17";
	
	String SELF_PAY_AND_CEP_NAME = "Self Pay and CEP";
	
	String WORK_COMP_ID = "18";
	
	String WORK_COMP_NAME = "Work Comp";
	
	String CHDP_ID = "19";
	
	String CHDP_NAME = "CHDP";
	
	String COUNTRYURL = "https://ipinfo.io/";
	
}
