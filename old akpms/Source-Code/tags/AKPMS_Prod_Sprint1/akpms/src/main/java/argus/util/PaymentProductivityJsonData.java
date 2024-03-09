package argus.util;


/**
 * 
 * @author vishal.joshi
 * 
 */
public class PaymentProductivityJsonData {
	private Long id;
	private String paymentProdType;
	private Long paymentBatch;
	private String chkNumber;

	private Double manuallyPostedAmt;

	private Double elecPostedAmt;

	private Double suspense;

	private Double agencyIncome;

	private Double otherIncome;

	private Double oldPriorAr;

	private Double depositAmt;

	private String receivedDate;

	private String datePosted;

	private Integer manualTransaction;

	private Integer elecTransaction;

	private Integer time;

	private String remark;

	private String postedBy;

	private boolean queryToTL;

	private int workFlowId;

	private String workFlowName;
	private String doctorName;
	private String insurance;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the agencyIncome
	 */
	public Double getAgencyIncome() {
		return agencyIncome;
	}

	/**
	 * @param agencyIncome
	 *            the agencyIncome to set
	 */
	public void setAgencyIncome(Double agencyIncome) {
		this.agencyIncome = agencyIncome;
	}
	public String getPaymentProdType() {
		return paymentProdType;
	}
	public void setPaymentProdType(String paymentProdType) {
		this.paymentProdType = paymentProdType;
	}
	public Long getPaymentBatch() {
		return paymentBatch;
	}
	public void setPaymentBatch(Long paymentBatch) {
		this.paymentBatch = paymentBatch;
	}
	public String getChkNumber() {
		return chkNumber;
	}
	public void setChkNumber(String chkNumber) {
		this.chkNumber = chkNumber;
	}

	/**
	 * @return the manuallyPostedAmt
	 */
	public Double getManuallyPostedAmt() {
		return manuallyPostedAmt;
	}

	/**
	 * @param manuallyPostedAmt
	 *            the manuallyPostedAmt to set
	 */
	public void setManuallyPostedAmt(Double manuallyPostedAmt) {
		this.manuallyPostedAmt = manuallyPostedAmt;
	}

	/**
	 * @return the elecPostedAmt
	 */
	public Double getElecPostedAmt() {
		return elecPostedAmt;
	}

	/**
	 * @param elecPostedAmt
	 *            the elecPostedAmt to set
	 */
	public void setElecPostedAmt(Double elecPostedAmt) {
		this.elecPostedAmt = elecPostedAmt;
	}
	public Double getOldPriorAr() {
		return oldPriorAr;
	}

	public void setOldPriorAr(Double oldPriorAr) {
		this.oldPriorAr = oldPriorAr;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	/**
	 * @return the manualTransaction
	 */
	public Integer getManualTransaction() {
		return manualTransaction;
	}

	/**
	 * @param manualTransaction
	 *            the manualTransaction to set
	 */
	public void setManualTransaction(Integer manualTransaction) {
		this.manualTransaction = manualTransaction;
	}

	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(int workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getWorkFlowName() {
		return workFlowName;
	}
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	/**
	 * @return the suspense
	 */
	public Double getSuspense() {
		return suspense;
	}

	/**
	 * @param suspense
	 *            the suspense to set
	 */
	public void setSuspense(Double suspense) {
		this.suspense = suspense;
	}

	/**
	 * @return the otherIncome
	 */
	public Double getOtherIncome() {
		return otherIncome;
	}

	/**
	 * @param otherIncome
	 *            the otherIncome to set
	 */
	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	/**
	 * @return the elecTransaction
	 */
	public Integer getElecTransaction() {
		return elecTransaction;
	}

	/**
	 * @param elecTransaction
	 *            the elecTransaction to set
	 */
	public void setElecTransaction(Integer elecTransaction) {
		this.elecTransaction = elecTransaction;
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

	/**
	 * @return the insurance
	 */
	public String getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the depositAmt
	 */
	public Double getDepositAmt() {
		return depositAmt;
	}

	/**
	 * @param depositAmt
	 *            the depositAmt to set
	 */
	public void setDepositAmt(Double depositAmt) {
		this.depositAmt = depositAmt;
	}

	/**
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate
	 *            the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the queryToTL
	 */
	public boolean isQueryToTL() {
		return queryToTL;
	}

	/**
	 * @param queryToTL
	 *            the queryToTL to set
	 */
	public void setQueryToTL(boolean queryToTL) {
		this.queryToTL = queryToTL;
	}

}
