package argus.util;

public interface Constants {

	Long ADMIN_ROLE_ID = 1L;

	Long DOCUMENT_MANAGER_ROLE_ID = 2L;

	Long STANDART_USER_ROLE_ID = 3L;

	Long TRAINEE_ROLE_ID = 4L;

	String SELECTED_ROLES_IDS = "selectedRolesIds";

	String SELECTED_DEPARTMENTS_IDS = "selectedDepartmentsIds";

	String DEPARTMENT_WITH_CHILD = "departmentWithChild";

	// String SEARCH_TEXT = "searchText";
	String KEYWORD = "keyword";

	String STATUS = "status";

	String RECORD_PRE_PAGE = "rp";

	String PAGE = "page";

	String OFFSET = "offset";

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

	String PAYMENT = "payment";

	String DOCTOR = "doctor";

	String DOCTOR_CODE = "doctorCode";

	String INSURANCE = "insurance";

	String DELETED = "deleted";

	String FIRST_NAME = "firstName";

	String ROLE = "role";

	String MORE_THAN_ONE_DEPT_AVAILABLE = "more than 1 dept available";

	String USER = "user";

	String DEPARTMENTS = "departments";

	String ARPRODUCTIVITY = "arProductivity";

	String ID = "id";

	String MODE = "mode";

	int ZERO = 0;

	String CHARGE_BATCH_PROCESS = "chargeBatchProcess";

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

	String WORKFLOW_ADJUSTMENT_LOG = "Adjustment log";

	String WORKFLOW_CODING_CORRECTION = "Coding correction";

	String WORKFLOW_SECOND_REQ_LOG = "Second request Log";

	String WORKFLOW_REKEY_REQ_TO_CHARGE_POSTING = "Re-Key request to charge posting";

	String WORKFLOW_PAYMENT_POSTING_LOG = "Payment posting log";

	String WORKFLOW_REQ_FOR_CHECK_TRACER = "Request for check tracer";

	String WORKFLOW_REFUND_REQUEST = "Refund Request";

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

	String AGENCY_INCOME = "agencyIncome";

	String OTHER_INCOME = "otherIncome";

	String OLD_PRIOR_AR = "oldPriorAr";

	String DATE_POSTED = "paymentBatch.datePosted";

	String MANUAL_TRANSACTION = "manualTransaction";

	String ELEC_TRANSACTION = "elecTransaction";

	String REMARK = "remark";

	String WORKFLOW_ID = "workflowId";

	String WORKFLOW = "workflow";

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

	Long PAYMENT_TYPE_OFFSET = 8L;

	Long PAYMENT_TYPE_OTC = 9L;

	Long REVENUE_TYPE_PATIENT_COLLECTION = 1L;

	String CHARGE_BATCH_PROCESSING_TICKET_RANGE = "ticketRange";

	String WORK_FLOW_TO_AR_IPA_FFS_HMO = "To AR IPA FFS HMO";

	String WORK_FLOW_TO_AR_FFS = "To AR FFS";

	String WORK_FLOW_TO_AR_CEP = "To AR CEP";

	String WORK_FLOW_TO_AR_MCL = "To AR MCL";

	String WORK_FLOW_OFFSET = "OFFSET";

	String WORK_FLOW_QUERY_TO_TL = "Query to TL";

	int TO_AR_IPA_FFS_HMO_WORK_FLOW = 1;

	int TO_AR_FFS_WORK_FLOW = 2;

	int TO_AR_CEP_WORK_FLOW = 3;

	int TO_AR_MCL_WORK_FLOW = 4;

	int OFFSET_WORK_FLOW = 5;

	int QUERY_TO_TL_WORK_FLOW = 6;

	String PAYMENT_DEPARTMENT_ID = "2"; // parent id of payment department

	String PAYMENTPRODQUERYTOTL = "paymentProdQueryToTL";

	String TICKET_NUMBER = "ticketNumber";

	String INSURANCE_NAME = "insuranceName";

	String CHK_DATE = "chkDate";

	String ACCOUNT_NUMBER = "accountNumber";

	String PATIENT_NAME = "patientName";

	String AR_PROD_PATIENT_NAME = "arProductivity.patientName";

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

	String CREATED_BY = "createdBy";

	String POSTED_BY = "postedBy";

	String TRANSACTION_TYPE = "transactionType";

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

	String PAYMENT_TYPE = "paymenttype";

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
	String DATE_RECEIVED_TO = "dateReceivedTo";
	String DATE_RECEIVED_FROM = "dateReceivedFrom";

	String REFUNDREQUEST = "refundRequest";
	String CREDENTIALING_ACCOUNTING = "credentialingAccounting";
	String TICKET_NUMBER_FROM = "ticketNumberFrom";

	String TICKET_NUMBER_TO = "ticketNumberTo";

	String ERA_CHECK_NO = "eraCheckNo";

	String EMAIL_HOST = "email.host";

	String EMAIL_FROM = "email.username";

	String EMAIL_PASSWORD = "email.password";
	String CODING_CHARGE_POSTING = "1"; // parent id of payment department

	String REJECTION_ID = "rejectId";

	String DOCTOR_NAME = "name";

	String PERMISSION = "permissions";

	String PERMISSION_OFFSET_MANAGER = "P-5";

	String PERMISSION_REVISE_CREATE_BATCH = "P-8";

	String FIRST_REQUEST_DUE_COUNT = "firstRequestDueCount";

	String SECOND_REQUEST_DUE_COUNT = "secondRequestDueCount";

	String NUMBER_OF_FIRST_REQUESTS_COUNT = "numberOfFirstRequestsCount";

	String NUMBER_OF_SECOND_REQUESTS_COUNT = "numberOfSecondRequestsCount";

	String REJECTION_COUNT = "rejectionCount";

	String RESOLVED_REJECTION_COUNT = "resolvedRejectionCount";

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

	String RECIEVED_BY = "recievedBy";

	String DOS_FROM = "dosFrom";

	String DOS_TO = "dosTo";

	String DATE_BATCH_POSTED_FROM = "dateBatchPostedFrom";

	String DATE_BATCH_POSTED_TO = "dateBatchPostedTo";

	String RECEIVED_BY_USERS = "receivedByUsers";

	String DOCTOR_LIST = "doctorList";

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

}
