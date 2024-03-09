/**
 *
 */
package argus.util;

/**
 * @author rajiv.k
 *
 */
public class CredentialingAccountingProductivityJsonData {
	private Long id;
	private String dateRecd;
	private String credentialingTask;
	private String taskCompleted;
	private Integer time;
	private String remark;
	private String postedBy;

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
	 * @return the dateRecd
	 */
	public String getDateRecd() {
		return dateRecd;
	}

	/**
	 * @param dateRecd
	 *            the dateRecd to set
	 */
	public void setDateRecd(String dateRecd) {
		this.dateRecd = dateRecd;
	}

	/**
	 * @return the credentialingTask
	 */
	public String getCredentialingTask() {
		return credentialingTask;
	}

	/**
	 * @param credentialingTask
	 *            the credentialingTask to set
	 */
	public void setCredentialingTask(String credentialingTask) {
		this.credentialingTask = credentialingTask;
	}

	/**
	 * @return the taskCompleted
	 */
	public String getTaskCompleted() {
		return taskCompleted;
	}

	/**
	 * @param taskCompleted
	 *            the taskCompleted to set
	 */
	public void setTaskCompleted(String taskCompleted) {
		this.taskCompleted = taskCompleted;
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

}
