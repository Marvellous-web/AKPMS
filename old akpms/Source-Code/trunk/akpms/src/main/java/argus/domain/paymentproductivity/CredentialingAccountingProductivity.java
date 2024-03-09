/**
 *
 */
package argus.domain.paymentproductivity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import argus.domain.BaseEntity;
import argus.domain.EntityListener;

/**
 * @author rajiv.k
 *
 */
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "credentialing_accounting_productivity")
public class CredentialingAccountingProductivity extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date_recd")
	private Date dateRecd;

	@Column(name = "credentialing_task")
	private String credentialingTask;

	@Column(name = "task_completed")
	private Date taskCompleted;

	@Column(name = "time")
	private Integer time;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

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
	public Date getDateRecd() {
		return dateRecd;
	}

	/**
	 * @param dateRecd
	 *            the dateRecd to set
	 */
	public void setDateRecd(Date dateRecd) {
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
	public Date getTaskCompleted() {
		return taskCompleted;
	}

	/**
	 * @param taskCompleted
	 *            the taskCompleted to set
	 */
	public void setTaskCompleted(Date taskCompleted) {
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

}
