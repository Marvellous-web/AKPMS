package argus.util;

import java.util.List;

public class ERAOffsetJsonData {

	private Long id;

	private String chkNumber;

	private String chkDate;

	private String patientName;

	private String accountNumber;

	private Double totalAmount;

	private String remark;

	private Long batchId;

	private String insuranceName;

	private Long paymentProductivity;

	private String postedBy;

	private String createdOn;

	private String operation;

	private long offsetPostId;

	private String status;

	private String doctorName;

	private Long offsetTicketNumber;

	private List<OffsetRecordJsonData> recordList;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the chkNumber
	 */
	public String getChkNumber() {
		return chkNumber;
	}

	/**
	 * @param chkNumber
	 *            the chkNumber to set
	 */
	public void setChkNumber(String chkNumber) {
		this.chkNumber = chkNumber;
	}

	/**
	 * @return the chkDate
	 */
	public String getChkDate() {
		return chkDate;
	}

	/**
	 * @param chkDate
	 *            the chkDate to set
	 */
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName
	 *            the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the batchId
	 */
	public Long getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the insuranceName
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * @param insuranceName
	 *            the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	/**
	 * @return the paymentProductivity
	 */
	public Long getPaymentProductivity() {
		return paymentProductivity;
	}

	/**
	 * @param paymentProductivity
	 *            the paymentProductivity to set
	 */
	public void setPaymentProductivity(Long paymentProductivity) {
		this.paymentProductivity = paymentProductivity;
	}

	/**
	 * @return the postedBy
	 */
	public String getPostedBy() {
		return postedBy;
	}

	/**
	 * @param postedBy
	 *            the postedBy to set
	 */
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
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
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the offsetPostId
	 */
	public long getOffsetPostId() {
		return offsetPostId;
	}

	/**
	 * @param offsetPostId
	 *            the offsetPostId to set
	 */
	public void setOffsetPostId(long offsetPostId) {
		this.offsetPostId = offsetPostId;
	}

	/**
	 * @return the recordList
	 */
	public List<OffsetRecordJsonData> getRecordList() {
		return recordList;
	}

	/**
	 * @param recordList
	 *            the recordList to set
	 */
	public void setRecordList(List<OffsetRecordJsonData> recordList) {
		this.recordList = recordList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Long getOffsetTicketNumber() {
		return offsetTicketNumber;
	}

	public void setOffsetTicketNumber(Long offsetTicketNumber) {
		this.offsetTicketNumber = offsetTicketNumber;
	}



}
