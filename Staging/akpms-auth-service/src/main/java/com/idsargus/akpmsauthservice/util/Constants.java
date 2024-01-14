package com.idsargus.akpmsauthservice.util;

public final class Constants {
	private Constants() {
		// restrict instantiation
	}

	public static final Integer ADMIN_ROLE_ID = 1;

	public static final Integer DOCUMENT_MANAGER_ROLE_ID = 2;
	

	public static final Integer STANDART_USER_ROLE_ID = 3;

	public static final Integer TRAINEE_ROLE_ID = 4;
	
	public static final Integer OTHER_ROLE_ID = 5;

	public static final String SELECTED_ROLES_IDS = "selectedRolesIds";

	public static final String SELECTED_DEPARTMENTS_IDS = "selectedDepartmentsIds";

	public static final String DEPARTMENT_WITH_CHILD = "departmentWithChild";


	public static final String STATUS = "status";

	public static final String STATUS_CODE = "statusCode";


	public static final String FOLLOUPS = "followups";

	public static final String RECORD_PRE_PAGE = "rp";

	public static final String PAGE = "page";

	public static final String OFFSET = "offset";

	public static final String ISOFFSET = "isOffset";

	public static final String SORT_ORDER = "sortorder";

	public static final String SORT_BY = "sortBy";

	public static final String SORT_NAME = "sortname";

	public static final String ORDER_BY = "orderBy";

	public static final String QTYPE = "qtype";

	public static final String QUERY = "query";
	public static final String ROLE_TRAINEE = "role_trainee";
	
	public static final String TRAINEE_EVALUATION = "Trainee Evaluation";

	public static final String BY_NAME = "name";

	public static final String LIMIT = "limit";

	public static final String REQUEST_ATTRIBUTE_PASSWORD = "password";

	public static final String REQUEST_ATTRIBUTE_CREATED_BY = "createdBy";

	public static final String ERROR = "error";

	public static final String SUCCESS_UPDATE = "successUpdate";
	public static final String SUCCESS_ADDED = "successAdded";
	public static final String SUCCESS = "success";

	public static final Integer PROCESS_MANUAL_MODIFICATION_MAIL_TEMPLATE = 1;

	public static final Integer TRAINEE_EVALUATION_TEMPLATE = 2;

	public static final Integer ARGUS_REGISTRATION_MAIL_TEMPLATE = 3;

	public static final Integer CHANGE_PASSWORD_TEMPLATE = 4;

	public static final Integer FORGET_PASSWOR_TEMPLATE = 5;

	public static final String TYPE_IDS_TL = "IDS_TL";

	public static final String TYPE_ARGUS_TL = "Argus_TL";

	public static final String DEPENDANCY_FILES = "files";

	public static final String DEPENDANCY_DEPARTMENTS = "departments";

	public static final String DEPENDANCY_USERS = "users";

	public static final String DEPENDANCY_SUB_PROCESS_MANUALS = "subProcessManuals";

	public static final int IS_DELETED = 1;

	public static final int NON_DELETED = 0;

	public static final Boolean BOOLEAN_DELETED = true;

	public static final Boolean BOOLEAN_NON_DELETED = false;

	public static final int ACTIVATE = 1;

	public static final int INACTIVATE = 0;

	public static final Boolean BOOLEAN_ACTIVATE = true;

	public static final Boolean BOOLEAN_INACTIVATE = false;

	public static final String NO_ENTRY_FOUND_FOR_QUERY = "No entity found for query";

	public static final String NO_RECORD_FOUND = "No Record Found.";

	public static final String NO_PROCESS_MANUAL_FOUND = "No Process Manual Added.";

	public static final String EXCEPTION = "Exception : ";

	public static final int DASHBOARD_NOTIFICATION_COUNT = 5;

	public static final String EDIT = "Edit";

	public static final String REVISE = "Revise";

	public static final String SAVE = "Save";

	public static final String UPDATE = "Update";

	public static final String VIEW = "View";

	public static final String OPERATION_TYPE = "operationType";

	public static final String BUTTON_NAME = "buttonName";

	public static final String ADD = "Add";

	public static final String ADD_MORE = "addMore";

	public static final String PAYMENT = "payment";

	public static final String REVENUETYPE = "revenuetype";

	public static final String DOCTOR = "doctor";

	// String DOCTOR_CODE = "doctorCode";

	public static final String INSURANCE = "insurance";

	public static final String LOCATION = "location";

	public static final String DELETED = "deleted";

	public static final String FIRST_NAME = "firstName";

	public static final String ROLE = "role";

	public static final String MORE_THAN_ONE_DEPT_AVAILABLE = "more than 1 dept available";

	public static final String USER = "user";

	public static final String DEPARTMENTS = "departments";

	public static final String DEPARTMENT_ID = "departmentId";

	public static final String SUB_DEPARTMENT_ID = "subDepartmentId";

	public static final String ARPRODUCTIVITY = "arProductivity";

	public static final String ID = "id";

	public static final String MODE = "mode";

	public static final int ZERO = 0;

	public static final String CHARGE_BATCH_PROCESS = "chargeBatchProcess";

	public static final String WORK_FLOW_STATUS = "workFlowStatus";

	public static final int WORKFLOW_APPROVE = 1;

	public static final int WORKFLOW_REJECT = 2;

	public static final int WORKFLOW_ESCALATE = 3;

	public static final String ADJUSTMENTLOG = "adjustmentLogs";

	public static final String CHANGE_PASSWORD = "changePassword";

	public static final int ADJUSTMENT_LOG_WORKFLOW = 1;

	public static final int CODING_CORRECTION_WORKFLOW = 2;

	public static final int SECOND_REQUEST_LOG_WORKFLOW = 3;

	public static final int REKEY_REQ_TO_CHARGE_POSTING_WORKFLOW = 4;

	public static final int PAYMENT_POSTING_LOG_WORKFLOW = 5;

	public static final int REQ_FOR_CHECK_TRACER_WORKFLOW = 6;

	public static final int QUERY_TO_TL_WORKFLOW = 7;

	public static final int REFUND_REQUEST_WORKFLOW = 8;

	public static final int REQUEST_FOR_DOCS = 10;

	public static final int STOP_PAYMENT_REISSUE = 9;

	public static final int REQUEST_FOR_DOCS_WORKFLOW = 10;

	public static final String WORKFLOW_ADJUSTMENT_LOG = "Adjustment log";

	public static final String WORKFLOW_CODING_CORRECTION = "Coding correction";

	public static final String WORKFLOW_SECOND_REQ_LOG = "Second request Log";

	public static final String WORKFLOW_REKEY_REQ_TO_CHARGE_POSTING = "Re-Key request to charge posting";

	public static final String WORKFLOW_PAYMENT_POSTING_LOG = "Payment posting log";

	public static final String WORKFLOW_REQ_FOR_CHECK_TRACER = "Request for check tracer";

	public static final String WORKFLOW_REFUND_REQUEST = "Refund Request";

	public static final String WORKFLOW_REQUEST_FOR_DOCS = "Request for docs";

	public static final String WORKFLOW_QUERY_TO_TL = "Query to TL";

	public static final int PAYER_EDIT_SOURCE = 1;

	public static final int UNPAID_INACTIVE_SOURCE = 2;

	public static final int CORRESPONDENCE_SOURCE = 3;

	public static final int DIVIDER_SET_TO_DENY_SOURCE = 4;

	public static final int IPA_FFS_SOURCE = 5;

	public static final int MISSING_INFO_SOURCE = 6;

	public static final int HOURLY_SOURCE = 7;

	public static final String SOURCE_PAYER_EDIT = "Payer Edit";

	public static final String SOURCE_UNPAID_INACTIVE = "Unpaid / Inactive";

	public static final String SOURCE_CORRESPONDENCE = "correspondence";

	public static final String SOURCE_DIVIDER_SET_TO_DENY = "Divider / Set to deny";

	public static final String SOURCE_IPA_FFS = "IPA-FFS";

	public static final String SOURCE_MISSING_INFO = "Missing Info";

	public static final String SOURCE_HOURLY = "Hourly";

	public static final String ARPRODUCTIVITY_ID = "arProductivity.id";

	public static final String READ_ONLY = "readOnly";

	public static final String CHECK_WORK_FLOW = "checkWorkFlow";

	public static final String CHECKTRACER = "checkTracer";

	public static final String SUBMIT_FOR_APPROVAL = "Submit";

	public static final String PAYMENTPRODUCTIVITY = "paymentProductivity";

	public static final String PaymentProductivityRefundRequest = "paymentProductivityRefundRequest";

	public static final String PaymentProductivityHourly = "paymentProductivityHourly";

	public static final String GET_DETAILS = "Get Detail";

	public static final String GET_BUTTON = "getButton";

	public static final String CURRENT_DATE = "currentDate";

	public static final String PAYMENTBATCH_ID = "paymentBatch.id";

	public static final String CHK_NUMBER = "chkNumber";

	public static final String MANUALLY_POSTED_AMT = "paymentBatch.manuallyPostedAmt";

	public static final String ELEC_POSTED_AMT = "paymentBatch.elecPostedAmt";

	public static final String SUSPENSE = "suspense";

	public static final String SUSPENSE_ACCOUNT = "suspenseAccount";

	public static final String AGENCY_INCOME = "agencyIncome";

	public static final String OTHER_INCOME = "otherIncome";

	public static final String OLD_PRIOR_AR = "oldPriorAr";

	public static final String DATE_POSTED = "paymentBatch.datePosted";

	public static final String MANUAL_TRANSACTION = "manualTransaction";

	public static final String ELEC_TRANSACTION = "elecTransaction";

	public static final String SCAN_DATE = "scanDate";

	public static final String REMARK = "remark";

	public static final String WORKFLOW_ID = "workflowId";

	public static final String WORKFLOW = "workflow";

	public static final String ON_HOLD = "onHold";

	public static final int ERA_WORK_FLOW = 1;

	public static final int NON_ERA_WORK_FLOW = 2;

	public static final int CAP_WORK_FLOW = 3;

	public static final int REFUND_REQUEST_WORK_FLOW = 4;

	public static final int HOURLY_WORK_FLOW = 5;

	public static final String CHARGE_BATCH_REPORT_TYPE = "reportType";

	/****** doctor type [start] ********/

	public static final String DOCTOR_TYPE_PAYMENT = "payment";

	public static final String DOCTOR_TYPE_AR = "ar";

	public static final String DOCTOR_TYPE_CODING = "coding";

	/****** doctor type [end] ********/

	public static final Long PRO_HEALTH_GROUP_ID = 1L;

	public static final Long PAYMENT_TYPE_ADMIN_INCOME = 1L;

	public static final Long MONEY_SOURCE_EFT = 1L;

	public static final Long MONEY_SOURCE_LOCKBOX = 2L;

	public static final Long PAYMENT_TYPE_OFFSET = 8L;

	public static final Long PAYMENT_TYPE_OTC = 9L;

	public static final Long REVENUE_TYPE_PATIENT_COLLECTION = 1L;

	public static final String CHARGE_BATCH_PROCESSING_TICKET_RANGE = "ticketRange";

	public static final String WORK_FLOW_TO_AR_IPA_FFS_HMO = "To AR IPA FFS HMO";

	public static final String WORK_FLOW_TO_AR_FFS = "To AR FFS";

	public static final String WORK_FLOW_TO_AR_CEP = "To AR CEP";

	public static final String WORK_FLOW_TO_AR_MCL = "To AR MCL";

	public static final String WORK_FLOW_TO_AR_MCR = "To AR MCR";

	public static final String WORK_FLOW_TO_AR_WC = "To AR WC";

	public static final String WORK_FLOW_OFFSET = "OFFSET";

	public static final String WORK_FLOW_QUERY_TO_TL = "Query to TL";

	public static final int TO_AR_IPA_FFS_HMO_WORK_FLOW = 1;

	public static final int TO_AR_FFS_WORK_FLOW = 2;

	public static final int TO_AR_CEP_WORK_FLOW = 3;

	public static final int TO_AR_MCL_WORK_FLOW = 4;

	public static final int TO_AR_MCR_WORK_FLOW = 7;

	public static final int TO_AR_WC_WORK_FLOW = 8;

	public static final int OFFSET_WORK_FLOW = 5;

	public static final int QUERY_TO_TL_WORK_FLOW = 6;

	public static final String CHARGE_DEPARTMENT_ID = "1"; // parent id of payment department

	public static final String PAYMENT_DEPARTMENT_ID = "2"; // parent id of payment department

	public static final String AR_DEPARTMENT_ID = "3"; // parent id of AR department

	public static final String ACCOUNTING_DEPARTMENT_ID = "4"; // parent id of payment department

	//String CODING_CHARGE_POSTING = "1"; // parent id of payment department

	public static final String CHARGE_DEPARTMENT_CE = "6"; // sub department under charges department

	public static final String CHARGE_DEPARTMENT_CODING_PRIMARY = "7"; // sub department under charges department

	public static final String CHARGE_DEPARTMENT_CODING_SPECIAL = "8"; // sub department under charges department

	public static final String CHARGE_DEPARTMENT_DEMO = "9"; // sub department under charges department


	public static final String PAYMENTPRODQUERYTOTL = "paymentProdQueryToTL";

	public static final String TICKET_NUMBER = "ticketNumber";

	public static final String INSURANCE_NAME = "insuranceName";

	public static final String LOCATION_NAME = "locationName";

	public static final String CHK_DATE = "chkDate";

	public static final String ACCOUNT_NUMBER = "accountNumber";

	public static final String SR_NO = "srNo";

	public static final String CHECK = "check";

	public static final String PATIENT_NAME = "patientName";

	public static final String TIMILY_FILING = "timilyFiling";

	public static final String AR_PROD_PATIENT_NAME = "arProductivity.patientName";

	public static final String AR_PROD_PATIENT_ACC_NO = "arProductivity.patientAccNo";

	public static final String AMOUNT = "amount";

	// search fields for payment batch list
	public static final String MONTH = "month";

	public static final String YEAR = "year";

	public static final String DOCTOR_ID = "doctorId";

	public static final String PH_DOCTOR_IDS = "phDoctorIds";

	public static final String DATE_POSTED_FROM = "datePostedFrom";

	public static final String DATE_POSTED_TO = "datePostedTo";

	public static final String DATE_CREATED_FROM = "dateCreatedFrom";

	public static final String DATE_CREATED_TO = "dateCreatedTo";

	public static final String DATE_DEPOSIT_FROM = "dateDepositFrom";

	public static final String DATE_DEPOSIT_TO = "dateDepositTo";

	public static final String INSURANCE_ID = "insuranceId";

	public static final String LOCATION_ID = "locationId";

	public static final String TEAM_ID = "teamId";

	public static final String CREATED_BY = "createdBy";

	public static final String POSTED_BY = "postedBy";

	// String RECEIVED_BY = "receivedBy";

	public static final String TRANSACTION_TYPE = "transactionType";

	public static final String TRANSACTION = "transaction";

	public static final String ERA = "ERA";

	public static final String NON_ERA = "NON ERA";

	public static final String CAP = "CAP";

	public static final String REFUND_REQUEST = "Refund Request";

	public static final String HOURLY = "Hourly";

	public static final String PROD_TYPE = "prodType";

	public static final String POSTED_BY_ID = "postedById";

	public static final String SERACH_DATE = "searchDate";

	public static final String REVENUE_TYPE_IDS = "revenueTypeIds";

	public static final String PAYMENT_TYPE_IDS = "paymentTypeIds";

	public static final String TICKET_NUMBER_IDS = "ticketNumberIds";

	public static final String PAYMENT_TYPE = "paymenttype";

	public static final String HOURLY_TASK = "hourlyTask";

	public static final String MONEY_SOURCE_IDS = "moneySourceIds";

	public static final String PAYMENT_BATCH = "paymentBatch";

	public static final String CHARGE_BATCH_TYPE_OFFICEBATCH = "Office";

	public static final String CHARGE_BATCH_TYPE_HOSPITALBATCH = "Hospital";

	public static final String CHARGE_BATCH_TYPE_DISPLAYBATCH = "Display";

	public static final String CHARGE_BATCH_TYPE_EKG = "EKG";

	public static final String CHARGE_BATCH_TYPE_CHDPBATCH = "CHDP";

	public static final String CHARGE_BATCH_TYPE_CAPBATCH = "CAP";

	public static final String CHARGE_BATCH_TYPE_SNF = "SNF";

	public static final String CHARGE_BATCH_TYPE_HOMEHEALTH = "Home Health";

	public static final String CHARGE_BATCH_TYPE_SUBACUTE = "Sub Acute";

	public static final String CHARGE_BATCH_TYPE_FACILITY = "Facility";

	public static final String CHARGE_BATCH_TYPE_HOMEVISITS = "Home Visits";

	public static final String CHARGE_BATCH_TYPE_REKEY = "Rekey";

	public static final String CHARGE_BATCH_REPORT_TYPE_POSTEDBATCH = "Posted Batch";

	public static final String CHARGE_BATCH_REPORT_TYPE_UNPOSTEDBATCH = "Un-Posted Batch";

	public static final String CHARGE_BATCH_REPORT_TYPE_BATCHWITHDISCREPANCY = "Batch with Discrepancy";

	public static final String CHARGE_BATCH_PRODUCTIVITY_FFS = "FFS";

	public static final String CHARGE_BATCH_PRODUCTIVITY_CAP = "CAP";

	public static final String CHARGE_PRODUCTIVITY = "chargeProductivity";

	public static final String CHARGE_PROD_REJECT = "chargeProdReject";

	public static final String HIDE = "hide";

	public static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String PAYMENTPRODOFFSET = "paymentProdOffset";

	public static final String WORKFLOW_OFFSET = "Offset";

	public static final String RECEIVED_DATE = "receivedDate";

	public static final String CHARGE_BATCH_WORK_FLOW_PAYMENT_REVERSAL = "Payment Reversal";

	public static final String CHARGE_BATCH_WORK_FLOW_REJECT = "Reject";

	public static final String PAYMENT_OFFSET_MANAGER = "paymentOffsetManager";

	public static final String OFFSET_ID = "offset.id";

	public static final String DATE_OF_CHECK = "dateOfCheck";

	public static final String CHECK_NUMBER = "checkNumber";

	public static final String BATCH_ID = "batchId";

	public static final String PROVIDE = "provide";

	public static final String PROVIDER_ID = "providerId";

	public static final String DATE_RESOLUTION_FROM = "dateResolutionFrom";
	public static final String DATE_RESOLUTION_TO = "dateResolutionTo";
	public static final String DATE_TRANSACTION_FROM = "dateTransactionFrom";
	public static final String DATE_TRANSACTION_TO = "dateTransactionTo";

	public static final String PARENT_ID = "parentId";
	public static final String PARENT_ONLY = "parentOnly";
	public static final String CHILD_ONLY = "childOnly";

	public static final String DATE_RECEIVED_TO = "dateReceivedTo";
	public static final String DATE_RECEIVED_FROM = "dateReceivedFrom";

	public static final String REFUNDREQUEST = "refundRequest";
	public static final String CREDENTIALING_ACCOUNTING = "credentialingAccounting";

	public static final String TICKET_NUMBER_FROM = "ticketNumberFrom";

	public static final String TICKET_NUMBER_TO = "ticketNumberTo";

	public static final String TICKET_NUMBER_SEARCH = "ticketNumberSearch";

	public static final String ERA_CHECK_NO = "eraCheckNo";

	public static final String EMAIL_HOST = "email.host";

	public static final String EMAIL_FROM = "email.username";

	public static final String EMAIL_PASSWORD = "email.password";

	public static final String REJECTION_ID = "rejectId";

	public static final String DOCTOR_NAME = "name";

	public static final String PERMISSION = "permissions";

	public static final String PERMISSION_OFFSET_MANAGER = "P-5";

	public static final String PERMISSION_REVISE_CREATE_BATCH = "P-8";

	public static final String SUB_ADMIN_ACCOUNTING = "P-13";

	public static final String FIRST_REQUEST_DUE_COUNT = "firstRequestDueCount";

	public static final String SECOND_REQUEST_DUE_COUNT = "secondRequestDueCount";

	public static final String NUMBER_OF_FIRST_REQUESTS_COUNT = "numberOfFirstRequestsCount";

	public static final String NUMBER_OF_SECOND_REQUESTS_COUNT = "numberOfSecondRequestsCount";

	public static final String REJECTION_COUNT = "rejectionCount";

	public static final String RESOLVED_REJECTION_COUNT = "resolvedRejectionCount";

	public static final String COMPLETED_REJECTION_COUNT = "completedRejectionCount";

	public static final String RESOLVED_REJECTION_WITH_DUMMY_CPT_COUNT = "resolvedRejectionWithDummyCPTCount";

	public static final String OFFSET_RECORDS = "offsetRecords";

	public static final String ERR = "err";

	public static final int ONE = 1;

	public static final int TWO = 2;

	public static final int THREE = 3;

	public static final String STRING_ONE = "1";

	public static final String STRING_TWO = "2";

	public static final String STRING_THREE = "3";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String TASK_NAME = "taskName";

	public static final String RECEIVED_BY = "receivedBy";

	public static final String RECEIVED = "received";

	public static final String DOS_FROM = "dosFrom";

	public static final String DOS_TO = "dosTo";

	public static final String DATE_BATCH_POSTED_FROM = "dateBatchPostedFrom";

	public static final String DATE_BATCH_POSTED_TO = "dateBatchPostedTo";

	public static final String RECEIVED_BY_USERS = "receivedByUsers";

	public static final String DOCTOR_LIST = "doctorList";

	public static final String USER_LIST = "userList";

	public static final String TEAM_LIST = "teamList";

	public static final String MONTHS = "months";

	public static final String YEARS = "years";

	public static final String PARAM = "param";

	public static final String ACTIVE_DEPTS = "activeDepts";

	public static final String INACTIVE_DEPTS = "inactiveDepts";

	public static final String ACTIVE_USERS = "activeUsers";

	public static final String INACTIVE_USERS = "inactiveUsers";

	public static final String ACTIVE_INSURANCES = "activeInsurances";

	public static final String INACTIVE_INSURANCES = "inactiveInsurances";

	public static final String ACTIVE_DOCTORS = "activeDoctors";

	public static final String INACTIVE_DOCTORS = "inactiveDoctors";

	public static final String ACTIVE_PAYMENTTYPES = "activePaymentTypes";

	public static final String INACTIVE_PAYMENTTYPES = "inactivePaymentTypes";

	public static final String MYSQL_DATE_FORMAT_WITH_TIME = "yyyy-MM-dd HH:mm:ss";

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int ELEVEN = 11;

	public static final int TWELVE = 12;

	public static final int EIGHT = 8;

	public static final int HUNDRED = 100;

	public static final int SIX = 6;

	public static final int SEVEN = 7;

	public static final int NINE = 9;

	public static final int TEN = 10;

	public static final String BATCH_POSTED = "batchPosted";

	public static final String CHARGE_BATCH_PROCESSING_TICKET_RANGE_FROM = "ticketRangeFrom";

	public static final String CHARGE_BATCH_PROCESSING_TICKET_RANGE_TO = "ticketRangeTo";

	public static final String QUERY_WILDCARD_PERCENTAGE = "\\%";

	public static final String QUERY_WILDCARD_UNDERSCORE = "\\_";

	public static final String PRODUCTIVITY_ID = "productivityId";

	public static final String REDIRECT_ERROR_403 = "redirect:/error403";

	public static final String CREATED_ON = "createdOn";

	public static final String SALT = "login@123";

	/********** payment batch list [transaction type] **********/
	public static final String ALL_BATCH = "All";
	public static final String POSTED_BATCH = "Posted";
	public static final String NOT_POSTED_BATCH = "Not Posted";

	public static final String SORT_ORDER_DESC = "DESC";

	public static final String POPUP = "Popup";
	public static final String CLOSE_POPUP = "closePopup";

	public static final String SHOW_EXTRA_OPTIONS = "showExtraOptions";

	/* USE IN DEMO PRODUCTIVITY */
	public static final String DEMO_EXISTING_CUSTOMER = "demoExistingCustomer";
	public static final String DEMO_NEW_CUSTOMER = "demoNewCustomer";

	public static final float DEMO_TIME_EXISTING_CUSTOMER = 6.67f;
	public static final float DEMO_TIME_NEW_CUSTOMER = 10;

	public static final String PAYMENT_POSTING_WORKFLOW = "paymentPostingWorkFlow";

	public static final String STATUS_APPROVE = "Approve";
	public static final String STATUS_REJECT = "Reject";
	public static final String STATUS_PENDING = "Pending";

	public static final String OFFSET_TICKET_NUMBER = "offsetTicketNumber";

	public static final String NDBA = "ndba";

	public static final String STATUS_AR_STEP_COMPLETED = "AR Step Completed";
	public static final String STATUS_OFFSET_RESOLVE = "Offset Resolve";
	public static final String STATUS_RESOLVE = "Resolved";
	public static final String STATUS_COMPLETED = "Completed";

	public static final String CHARGEABLE = "Chargeable";
	public static final String NOT_CHARGEABLE = "Not Chargeable";

	public static final String TASK_RECEIVED_DATE_FROM = "taskReceivedDateFrom";
	public static final String TASK_RECEIVED_DATE_TO = "taskReceivedDateTo";
	public static final String TASK_COMPLETED_DATE_FROM = "taskCompletedDateFrom";
	public static final String TASK_COMPLETED_DATE_TO = "taskCompletedDateTo";

	public static final String SCAN_DATE_FROM = "scanDateFrom";
	public static final String SCAN_DATE_TO = "scanDateTo";

	public static final String WITHOUT_TIMILY_FILING = "withoutTimilyFiling";

	/**************** Coding correction work flow names ********************/

	public static final String ESCALATE_TO_ARGUS_TL = "Escalate to Argus TL";

	public static final String FORWARD_TO_CODING_CORRECTION = "Forward to coding correction";

	public static final String RETURN_TO_AR = "Return to AR";

	public static final String DONE = "Done";

	public static final String CODING_TO_CE = "Coding to CE";

	public static final String PERMISSION_DOCUMENT_MANAGER = "P-14";

	/** Constant for QAManager **/

	public static final String QAWORKSHEET = "qaworksheet";

	public static final String QAWORKSHEET_ID = "qaWorksheet.id";

	public static final String TYPE = "type";

	public static final Short QA_WORKSEET_TYPE_BYSTAFF = 1;

	public static final Short QA_WORKSEET_TYPE_GENERAL = 2;

	public static final Short QA_WORKSEET_TYPE_BYDOCTOR = 3;

	public static final String SAVE_QAWORKSHEET = "Save QA Worksheet";

	public static final String UPDATE_QAWORKSHEET = "Update QA Worksheet";

	public static final double PERCENT_LIMIT = 100.0;

	public static final String LOCATION_LIST = "locationList";

	public static final String PERMISSION_QA_WORKSHEET_MANAGER = "P-2";

	public static final String SAMPLING_PRODUCTIVITY_ID = "productivity_id";

	public static final String POSTING_DATE_FROM = "postingDateFrom";

	public static final String POSTING_DATE_TO = "postingDateTo";

	public static final String TIME_STAMP_FORMAT = "yyyy-MM-dd hh:mm:ss.S";

	public static final String PARENT_QCPOINTS = "parentQcPoints";

	public static final String CHILD_QCPOINTS = "childQcPoints";
	/**
	 * QAProductivitySampling constants
	 */
	public static final String QA_PRODUCTIVITY_SAMPLING = "qaProductivitySampling";

	public static final String QC_POINT_CHECKPOINT = "checkPoint";

	public static final String SAMPLEID = "sampleid";

	public static final String CPT_CODE_DEMO = "cptCodeDemo";

	public static final String QA_PATIENT_INFO_ID = "qaPatientInfoId";

	public static final String QA_WORKSHEET_SAMPLE_ID = "qaWorksheetSampleId";

	public static final String DEPARTMENT = "department";

	public static final String SUB_DEPARTMENT = "subDepartment";

	public static final String PAYMENT_DEPARTMENT_NAME = "Payments Department";

	public static final String CHARGE_DEPARTMENT_NAME = "Coding and Charge Entry Department";

	public static final String AR_DEPARTMENT_NAME = "Accounts Receivable Department";

	public static final String RANDOM_RECOREDS = "random";

	/**
	 * QAReport date constants
	 *
	 */
	public static final String QA_REPORT_FROM_DATE = "qaReportFromDate";
	public static final String QA_REPORT_TO_DATE = "qaReportToDate";

	public static final String FOLLOW_UP_DATE_FROM = "followUpDateFrom";
	public static final String FOLLOW_UP_DATE_TO = "followUpDateTo";

	public static final String BATCH_PERCENTAGE = "batchPercentage";
	public static final String ACCOUNT_COUNT = "accountCount";
	public static final String CPT_COUNT = "cptCount";

	public static final String QA_WORKSHEET_MONTH = "qaWorksheetMonth";

	public static final String QA_WORKSHEET_YEAR = "qaWorksheetYear";

	public static final String START_MONTH = "startMonth";

	public static final String END_MONTH = "endMonth";

	public static final String SUB_DEPARTMENT_NAME = "subDepartmentName";

	public static final String ACCOUNTING_DEPARTMENT_NAME = "Accounting Department";

	public static final String CHARGE_ENTRY_ID = "6";

	public static final String CHARGE_ENTRY_NAME = "Charge Entry";

	public static final String CODING_PRIMARY_CARE_ID = "7";
	
	public static final String CODING_PRIMARY_CARE_NAME = "Coding Primary Care";
	
	public static final String CODING_SPECIALIST_ID = "8";
	
	public static final String CODING_SPECIALIST_NAME = "Coding Specialist";
	
	public static final String DEMO_AND_VERIFICATION_ID = "9";
	
	public static final String DEMO_AND_VERIFICATION_NAME = "Demo and Verification";
	
	public static final String PAYMENT_POSTING_ID = "10";
	
	public static final String PAYMENT_POSTING_NAME = "Payments Posting";
	
	public static final String TREASURY_ID = "11";
	
	public static final String TREASURY_NAME = "Treasury";
	
	public static final String HMO_ID = "13";
	
	public static final String HMO_NAME = "HMO";
	
	public static final String MCL_ID = "14";
	
	public static final String MCL_NAME = "MCL";
	
	public static final String MCR_ID = "15";
	
	public static final String MCR_NAME = "MCR";
	
	public static final String PPO_ID = "16";
	
	public static final String PPO_NAME = "PPO";
	
	public static final String SELF_PAY_AND_CEP_ID = "17";
	
	public static final String SELF_PAY_AND_CEP_NAME = "Self Pay and CEP";
	
	public static final String WORK_COMP_ID = "18";
	
	public static final String WORK_COMP_NAME = "Work Comp";
	
	public static final String CHDP_ID = "19";
	
	public static final String CHDP_NAME = "CHDP";
	
	public static final String COUNTRYURL = "https://ipinfo.io/";
	
	public static final String MASTER_DATA_TABLES = "Master Data Tables";
	
	public static final String HOURLY_PROJECTS_PRODUCTIVITY = "Hourly Projects Productivity";
	
	public static final String PAYMENTS_TEAM_PRODUCTIVITY = "Payments Team Productivity";
	
	public static final String PAYMENTS_PROCESS_WORKFLOW = "Payments Process Workflow";
	
	public static final String PAYMENTS_PROCESS_WORKFLOW_ERA = "Payments Process Workflow (ERA)";
	
	public static final String PAYMENTS_PROCESS_WORKFLOW_NONERA = "Payments Process Workflow (Non ERA)";
	
	public static final String PAYMENTS_PROCESS_WORKFLOW_CAP = "Payments Process Workflow (CAP)";
	
	public static final String DEMO_CE_CODING_VAL_PROCESS = "Demo, CE & Coding Validation Team Prod. & Process Workflow";
	
	public static final String CODING_CORRECTION_LOG = "Coding Correction Log";
	
	public static final String ACC_RECV_TEAM_PROD_PROCESS_WF = "Accounts Receivable (AR) Team Productivity & Process Workflow";
	
	public static final String ERA_LIST = "ERA List";
	
	public static final String CAP_LIST = "CAP List";
	
	public static final String NON_ERA_LIST = "NON-ERA List";
	
	public static final String TO_AR_IPA_FFS_HMO ="To AR IPA FFS HMO";
	
	public static final String TO_AR_FFS = "To AR FFS";
	
	public static final String TO_AR_CEP = "To AR CEP";
	
	public static final String TO_AR_MCL = "To AR MCL";
	
	public static final String TO_AR_MCR = "To AR MCR";
	
	public static final String TO_AR_WC ="To AR WC";
	
	public static final String QUERY_TO_TL = "Query to TL";
	
	public static final String LIST_PRODUCTIVITY  = "List Productivity";
	
	//public static final String ON_HOLD  = "On Hold"; already exist at line 318
	
	public static final String  REJECTED_LOGS =  "Rejected Logs";
	
    public static final String NEW_REJECTIONS  =  "New Rejections";
	
	public static final String  FIRST_REQUEST_DUE = "1st Request Due";
	
	public static final String   NUMBER_OF_FIRST_REQUESTS = "Number Of First Requests";
	
    public static final String  SECOND_REQUEST_DUE = "2nd Request Due";
	
	public static final String  NUMBER_OF_SECOND_REQUESTS = "Number Of Second Requests";
	
	public static final String  RESOLVED_REJECTIONS = "Resolved Rejections";
	
    public static final String  RESOLVED_REJECTIONS_WITH_DUMMY_CPT = "Resolved Rejections with dummy CPT";
	
	public static final String  COMPLETED_REJECTIONS = "Completed Rejections";
	
	//public static final String  CODING_CORRECTION_LOG = "Coding Correction Log";
	//public static final String  FORWARD_TO_CODING_CORRECTION = "Forward to coding correction";
	
    // public static final String  ESCALATE_TO_ARGUS_TL = "Escalate to Argus TL" ;
	
	//public static final String  RETURN_TO_AR = "Return to AR";
	
	//public static final String  CODING_TO_CE = "Coding to CE";
	
    //public static final String  DONE = "Done";
	
	
   	public static final String PAYMENT_POSTING_OFFSET_LOG = "Payment Posting Offset Log";
   	
    public static final String OFFSETS_PENDING  = "Offsets Pending" ;
	
    public static final String OFFSETS_APPROVED  = "Offsets Approved";
    	
    public static final String OFFSETS_COMPLETED  = "Offsets Completed" ;
    	
    public static final String PAYMENT_POSTING_LOG  = "Payment Posting Log" ;
    
    public static final String PENDING  = "Pending" ;
    	
    public static final String REJECTED  = "Rejected" ;
    	
    public static final String APPROVED  = "Approved" ;
    	
    public static final String COMPLETED  = "Completed" ;
    
    public static final String REKEY_REQUEST_LOG= "Rekey Request Log";
	
    public static final String QUERY_TO_CHARGE_ENTRY = "Query to Charge Entry" ;
	
   // public static final String RETURN_TO_AR = "Return to AR";
	
    public static final String CLOSED = "Closed";
	
    public static final String OFFSET_REFERENCE_POSTINGS = "Offset Reference & Postings";

    public static final String OFFSET_REFERENCE_LIST = "Offset Reference List";
	
    public static final String OFFSET_REFERENCE_PENDING  = "Offset Reference Pending";

    public static final String  OFFSET_REFERENCE_AR_STEP_COMPLETED = "Offset Reference AR Step Completed";

    public static final String OFFSET_REFERENCE_OFFSET_RESOLVED = "Offset Reference Offset Resolved";

    public static final String ADJUSTMENT_LOG = "Adjustment Log";
    
    public static final String ADJUSTMENT_LOG_LIST_ALL = "Adjustment Log [List All]";
 	
    public static final String TIMELY_FILING  = "Timely Filing";
    
    public static final String WITHOUT_TIMELY_FILING = "Without Timely Filing";
    
    public static final String ACCOUNTS_RECEIVABLE_TEAM_PRODUCTIVITY_AND_PROCESS = "Accounts Receivable (AR) Team Productivity & Process Workflow";
    
    public static final String ADJUSTMENT_LOG_TIMELY_FILING = "Adjustment Log Timely Filing";
    
    public static final String ADJUSTMENT_LOG_WITHOUT_TIMELY_FILING ="Adjustment Log Without Timely Filing";
    //public static final String APPROVED  = "Approved";
    public static final String LIST_AR_PRODUCTIVITY = "List AR Productivity";
    
    public static final String ESCALATE = "Escalate";
    
    
    public static final String REJECT = "Reject";
    
    
    //public static final String CLOSED = "Closed";
    
   // public static final String RETURN_TO_AR = "Return to AR";
    
    public static final String  REKEY_TO_CHARGE_POSTING = "Rekey to charge posting";
    
    public static final String NEXT_FOLLOW_UP_DATE = "Next Follow Up Date";
    
    public static final String REFUND_REQUEST_LIST_ALL = "Refund Request [List All]";
//    
//    public static final String  = "";
//    
//    public static final String  = "";
//    
    
    //public static final String  = "";
    
    
    
    
    
    
}
