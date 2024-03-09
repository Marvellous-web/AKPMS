package argus.util;



public class RefundRequestReportJsonData {

	private Long id;
	private Long paymentBatch;
	private String patientName;
	private String accountNumber;
	private Double creditAmount;
	private String transactionDate;
	private Integer timeTaken;
	private String resolutionOrRemark;
	private String resolutionDate;
	private String findings;
	private Long ticketNumber;
	private String createdDate;
	private String doctorName;

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
	 * @return the paymentBatch
	 */
	public Long getPaymentBatch() {
		return paymentBatch;
	}

	/**
	 * @param paymentBatch
	 *            the paymentBatch to set
	 */
	public void setPaymentBatch(Long paymentBatch) {
		this.paymentBatch = paymentBatch;
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
	 * @return the creditAmount
	 */
	public Double getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @param creditAmount
	 *            the creditAmount to set
	 */
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the timeTaken
	 */
	public Integer getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @param timeTaken
	 *            the timeTaken to set
	 */
	public void setTimeTaken(Integer timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * @return the resolutionOrRemark
	 */
	public String getResolutionOrRemark() {
		return resolutionOrRemark;
	}

	/**
	 * @param resolutionOrRemark
	 *            the resolutionOrRemark to set
	 */
	public void setResolutionOrRemark(String resolutionOrRemark) {
		this.resolutionOrRemark = resolutionOrRemark;
	}


	/**
	 * @return the resolutionDate
	 */
	public String getResolutionDate() {
		return resolutionDate;
	}

	/**
	 * @param resolutionDate
	 *            the resolutionDate to set
	 */
	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	/**
	 * @return the findings
	 */
	public String getFindings() {
		return findings;
	}

	/**
	 * @param findings
	 *            the findings to set
	 */
	public void setFindings(String findings) {
		this.findings = findings;
	}

	/**
	 * @return the ticketNumber
	 */
	public Long getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber
	 *            the ticketNumber to set
	 */
	public void setTicketNumber(Long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

}
