package argus.util;

public class ChargeProductivityJsonData {

	private Long productivityId;

	private Long ticketNumber;

	private String productivityType;

	private Integer numberOfTransactions;

	private Integer time;

	private String workFlow;

	private String unholdRemarks;

	private String createdOn;

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

	public Integer getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public void setNumberOfTransactions(Integer numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
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

}
