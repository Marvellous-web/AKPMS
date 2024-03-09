/**
 *
 */
package argus.util;

/**
 * @author sumit.v
 *
 */
public class PaymentProductivityHourlyJsonData {

	private Long id;

	private Double time;

	private String taskName;

	private String details;

	private String dateReceived ;

	private String taskCompletedDate;
	
	private Integer minutesspent = 0;

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
	 * @return the time
	 */
	public Double getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Double time) {
		this.time = time;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getTaskCompletedDate() {
		return taskCompletedDate;
	}

	public void setTaskCompletedDate(String taskCompletedDate) {
		this.taskCompletedDate = taskCompletedDate;
	}

	public Integer getMinutesspent() {
		return minutesspent;
	}

	public void setMinutesspent(Integer minutesspent) {
		this.minutesspent = minutesspent;
	}

}
