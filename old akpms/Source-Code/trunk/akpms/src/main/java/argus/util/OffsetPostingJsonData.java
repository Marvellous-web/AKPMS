/**
 *
 */
package argus.util;

import java.util.List;

/**
 * @author vishal.joshi
 *
 */
public class OffsetPostingJsonData {

	private Long id;

	private String doctor;

	private String dateOfCheck;

	private String checkNumber;

	private String fcnOrAR;

	private Long offsetId;

	private Long batchId;

	private String paymentProdType;

	private String createdBy;

	private String patientName;

	private String accountNumber;

	private String insuranceName;

	private String createdOn;

	private Double totalPosted;

	private Integer time;

	private List<OffsetPostingRecordJsonData> postingRecords;

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
	 * @return the doctor
	 */
	public String getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor
	 *            the doctor to set
	 */
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the dateOfCheck
	 */
	public String getDateOfCheck() {
		return dateOfCheck;
	}

	/**
	 * @param dateOfCheck
	 *            the dateOfCheck to set
	 */
	public void setDateOfCheck(String dateOfCheck) {
		this.dateOfCheck = dateOfCheck;
	}

	/**
	 * @return the fcnOrAR
	 */
	public String getFcnOrAR() {
		return fcnOrAR;
	}

	/**
	 * @param fcnOrAR
	 *            the fcnOrAR to set
	 */
	public void setFcnOrAR(String fcnOrAR) {
		this.fcnOrAR = fcnOrAR;
	}

	/**
	 * @return the postingRecords
	 */
	public List<OffsetPostingRecordJsonData> getPostingRecords() {
		return postingRecords;
	}

	/**
	 * @param postingRecords
	 *            the postingRecords to set
	 */
	public void setPostingRecords(
			List<OffsetPostingRecordJsonData> postingRecords) {
		this.postingRecords = postingRecords;
	}

	/**
	 * @return the checkNumber
	 */
	public String getCheckNumber() {
		return checkNumber;
	}

	/**
	 * @param checkNumber
	 *            the checkNumber to set
	 */
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
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
	 * @return the offsetId
	 */
	public Long getOffsetId() {
		return offsetId;
	}

	/**
	 * @param offsetId
	 *            the offsetId to set
	 */
	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}



	/**
	 * @return the paymentProdType
	 */
	public String getPaymentProdType() {
		return paymentProdType;
	}

	/**
	 * @param paymentProdType
	 *            the paymentProdType to set
	 */
	public void setPaymentProdType(String paymentProdType) {
		this.paymentProdType = paymentProdType;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the totalPosted
	 */
	public Double getTotalPosted() {
		return totalPosted;
	}

	/**
	 * @param totalPosted
	 *            the totalPosted to set
	 */
	public void setTotalPosted(Double totalPosted) {
		this.totalPosted = totalPosted;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Integer time) {
		this.time = time;
	}


}
