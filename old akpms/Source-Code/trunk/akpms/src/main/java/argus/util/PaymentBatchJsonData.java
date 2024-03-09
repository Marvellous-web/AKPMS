package argus.util;

public class PaymentBatchJsonData {

	private Long id;

	private String createdBy;

	private String billingMonth;

	private String doctor;

	private String parentDoctor;

	private String insurance;

	private String moneySource;

	private String paymentType;

	private Double depositAmount;

	private Double ndba;

	private String depositDate;

	/* Auto date */
	private String postedOn = null;

	private String postedBy = null;

	/* CT DATE POSTED */
	private String datePosted = null;

	private String revisedBy = null;

	private String revisedOn = null;

	private String offsetType;

	private String comment ;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getMoneySource() {
		return moneySource;
	}

	public void setMoneySource(String moneySource) {
		this.moneySource = moneySource;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Double getNdba() {
		return ndba;
	}

	public void setNdba(Double ndba) {
		this.ndba = ndba;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getRevisedBy() {
		return revisedBy;
	}

	public void setRevisedBy(String revisedBy) {
		this.revisedBy = revisedBy;
	}

	/**
	 * @return the offsetType
	 */
	public String getOffsetType() {
		return offsetType;
	}

	/**
	 * @param offsetType
	 *            the offsetType to set
	 */
	public void setOffsetType(String offsetType) {
		this.offsetType = offsetType;
	}

	public String getRevisedOn() {
		return revisedOn;
	}

	public void setRevisedOn(String revisedOn) {
		this.revisedOn = revisedOn;
	}

	public String getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(String postedOn) {
		this.postedOn = postedOn;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	/**
	 * @return the parentDoctor
	 */
	public String getParentDoctor() {
		return parentDoctor;
	}

	/**
	 * @param parentDoctor
	 *            the parentDoctor to set
	 */
	public void setParentDoctor(String parentDoctor) {
		this.parentDoctor = parentDoctor;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}


}
