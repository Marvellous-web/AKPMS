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
import argus.domain.Doctor;
import argus.domain.EntityListener;
import argus.domain.PaymentBatch;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "payment_prod_refund_req")
public class PaymentProductivityRefundRequest extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = false) })
	private PaymentBatch paymentBatch;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "doctor_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Doctor doctor;

	@Column(name = "patient_name")
	private String patientName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "credit_amount")
	private Double creditAmount;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "time_taken")
	private Integer timeTaken;

	private String findings;

	@Column(name = "resolution_remark", columnDefinition = "TEXT")
	private String resolutionOrRemark;

	@Column(name = "request_date")
	private Date requestDate;

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

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
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

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getResolutionOrRemark() {
		return resolutionOrRemark;
	}

	public void setResolutionOrRemark(String resolutionOrRemark) {
		this.resolutionOrRemark = resolutionOrRemark;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

}
