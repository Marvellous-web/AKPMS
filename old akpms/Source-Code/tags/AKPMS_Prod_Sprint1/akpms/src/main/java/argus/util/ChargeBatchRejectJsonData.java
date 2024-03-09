package argus.util;


public class ChargeBatchRejectJsonData {

	private long id;

	private String patientName;

	private Integer sequence;

	private String account;

	private String dateOfService;

	private String reasonToReject;

	private String insuranceType;

	private String dateOfFirstRequestToDoctorOffice;

	private String dateOfSecondRequestToDoctorOffice;

	private Long chargeBatchProcessing;

	private String dob;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDateOfService() {
		return dateOfService;
	}

	public void setDateOfService(String dateOfService) {
		this.dateOfService = dateOfService;
	}

	public String getReasonToReject() {
		return reasonToReject;
	}

	public void setReasonToReject(String reasonToReject) {
		this.reasonToReject = reasonToReject;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getDateOfFirstRequestToDoctorOffice() {
		return dateOfFirstRequestToDoctorOffice;
	}

	public void setDateOfFirstRequestToDoctorOffice(
			String dateOfFirstRequestToDoctorOffice) {
		this.dateOfFirstRequestToDoctorOffice = dateOfFirstRequestToDoctorOffice;
	}

	public String getDateOfSecondRequestToDoctorOffice() {
		return dateOfSecondRequestToDoctorOffice;
	}

	public void setDateOfSecondRequestToDoctorOffice(
			String dateOfSecondRequestToDoctorOffice) {
		this.dateOfSecondRequestToDoctorOffice = dateOfSecondRequestToDoctorOffice;
	}

	/**
	 * @return the chargeBatchProcessing
	 */
	public Long getChargeBatchProcessing() {
		return chargeBatchProcessing;
	}

	/**
	 * @param chargeBatchProcessing
	 *            the chargeBatchProcessing to set
	 */
	public void setChargeBatchProcessing(Long chargeBatchProcessing) {
		this.chargeBatchProcessing = chargeBatchProcessing;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

}
