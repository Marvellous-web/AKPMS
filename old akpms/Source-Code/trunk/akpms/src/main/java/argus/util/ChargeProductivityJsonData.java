package argus.util;



public class ChargeProductivityJsonData {

	private Long productivityId;

	private Long ticketNumber;

	private String productivityType;

	private Integer t1;

	private Integer t2;

	private Integer t3;

	private Float time;

	private String workFlow;

	private String unholdRemarks;

	private String createdOn;

	private String createdBy;

	private String ctPostedDate;

	private String scanDate;

	private boolean onHold;

	private String remarks;

	public Long getProductivityId() {
		return productivityId;
	}

	public void setProductivityId(Long productivityId) {
		this.productivityId = productivityId;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getProductivityType() {
		return productivityType;
	}

	public void setProductivityType(String productivityType) {
		this.productivityType = productivityType;
	}


	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
	}

	public String getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(String workFlow) {
		this.workFlow = workFlow;
	}

	/**
	 * @return the unholdRemarks
	 */
	public String getUnholdRemarks() {
		return unholdRemarks;
	}

	/**
	 * @param unholdRemarks
	 *            the unholdRemarks to set
	 */
	public void setUnholdRemarks(String unholdRemarks) {
		this.unholdRemarks = unholdRemarks;
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
	 * @return the t1
	 */
	public Integer getT1() {
		return t1;
	}

	/**
	 * @param t1
	 *            the t1 to set
	 */
	public void setT1(Integer t1) {
		this.t1 = t1;
	}

	/**
	 * @return the t2
	 */
	public Integer getT2() {
		return t2;
	}

	/**
	 * @param t2
	 *            the t2 to set
	 */
	public void setT2(Integer t2) {
		this.t2 = t2;
	}

	/**
	 * @return the t3
	 */
	public Integer getT3() {
		return t3;
	}

	/**
	 * @param t3
	 *            the t3 to set
	 */
	public void setT3(Integer t3) {
		this.t3 = t3;
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

	public String getCtPostedDate() {
		return ctPostedDate;
	}

	public void setCtPostedDate(String ctPostedDate) {
		this.ctPostedDate = ctPostedDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the onHold
	 */
	public boolean isOnHold() {
		return onHold;
	}

	/**
	 * @param onHold
	 *            the onHold to set
	 */
	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	/**
	 * @return the scanDate
	 */
	public String getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate
	 *            the scanDate to set
	 */
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

}
