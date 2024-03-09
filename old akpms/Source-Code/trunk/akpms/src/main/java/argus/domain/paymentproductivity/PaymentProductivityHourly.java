package argus.domain.paymentproductivity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import argus.domain.BaseEntity;
import argus.domain.EntityListener;
import argus.domain.HourlyTask;
import argus.domain.PaymentBatch;

@Entity
@Table(name = "payment_prod_hourly")
@EntityListeners(EntityListener.class)
public class PaymentProductivityHourly extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = true) })
	private PaymentBatch paymentBatch;

	private Integer hours = 0;

	private Integer minutes = 0;
	
	private Integer minutesspent = 0;
	
	@Column(name="date_received")
	private Date dateReceived;

	@Column(name="date_task_completed")
	private Date taskCompleted;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "task_name", referencedColumnName = "id", unique = false, nullable = true) })
	private HourlyTask hourlyTask;

	@Column(columnDefinition = "TEXT")
	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentBatch getPaymentBatch() {
		return paymentBatch;
	}

	public void setPaymentBatch(PaymentBatch paymentBatch) {
		this.paymentBatch = paymentBatch;
	}


	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	public Integer getMinutesspent() {
		return minutesspent;
	}

	public void setMinutesspent(Integer minutesspent) {
		this.minutesspent = minutesspent;
	}

	public HourlyTask getHourlyTask() {
		return hourlyTask;
	}

	public void setHourlyTask(HourlyTask hourlyTask) {
		this.hourlyTask = hourlyTask;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public Date getTaskCompleted() {
		return taskCompleted;
	}

	public void setTaskCompleted(Date taskCompleted) {
		this.taskCompleted = taskCompleted;
	}

}
