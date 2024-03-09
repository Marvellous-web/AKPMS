package argus.domain.paymentproductivity;

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
import argus.domain.PaymentBatch;
import argus.domain.TaskName;

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

	private Double time = 0.0;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "task_name", referencedColumnName = "id", unique = false, nullable = true) })
	private TaskName taskName;

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

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public TaskName getTaskName() {
		return taskName;
	}

	public void setTaskName(TaskName taskName) {
		this.taskName = taskName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
