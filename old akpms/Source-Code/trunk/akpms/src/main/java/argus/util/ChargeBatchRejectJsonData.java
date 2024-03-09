package argus.util;


public class ChargeBatchRejectJsonData {

	private long id;

	private String patientName;

	private String sequence;

	private String account;

	private String dateOfService;

	private String location;

	private String reasonToReject;

	private String insuranceType;

	private String dateReceived;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private String dateOfFirstRequestToDoctorOffice;

	private String dateOfSecondRequestToDoctorOffice;

	private Long chargeBatchProcessing;

	private String doctorName;

	private String dob;

	private String status;

	private Integer workFLow;

	private String createdBy;

	private String createdOn;
	
	private String CompletedOn;

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

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName
	 *            the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the workFLow
	 */
	public Integer getWorkFLow() {
		return workFLow;
	}

	/**
	 * @param workFLow the workFLow to set
	 */
	public void setWorkFLow(Integer workFLow) {
		this.workFLow = workFLow;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	public String getCompletedOn() {
		return CompletedOn;
	}

	public void setCompletedOn(String completedOn) {
		CompletedOn = completedOn;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the dateReceived
	 */
	public String getDateReceived() {
		return dateReceived;
	}

	/**
	 * @param dateReceived
	 *            the dateReceived to set
	 */
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

}
