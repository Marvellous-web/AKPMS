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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import argus.domain.EntityListener;

/**
 * @author vishal.joshi
 *
 */
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "payment_prod_query_to_tl")
public class PaymentProdQueryToTL {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="chk_number")
	private String chkNumber;

	@Column(name="patient_name")
	private String patientName;

	@Column(name="amount")
	private Float amount = 0f;

	@Column(name="chk_date")
	private Date chkDate;

	@Column(name="acc_number")
	private String accountNumber;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "payment_prod_id", referencedColumnName = "id", unique = false, nullable = false) })
	private PaymentProductivity paymentProductivity;

	@Column(name = "tl_remark", columnDefinition = "TEXT")
	private String tlRemark;

	@Transient
	private String ticketNumber;

	@Transient
	private String insuranceName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the chkNumber
	 */
	public String getChkNumber() {
		return chkNumber;
	}

	/**
	 * @param chkNumber the chkNumber to set
	 */
	public void setChkNumber(String chkNumber) {
		this.chkNumber = chkNumber;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the amount
	 */
	public Float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}

	/**
	 * @return the chkDate
	 */
	public Date getChkDate() {
		return chkDate;
	}

	/**
	 * @param chkDate the chkDate to set
	 */
	public void setChkDate(Date chkDate) {
		this.chkDate = chkDate;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the paymentProductivity
	 */
	public PaymentProductivity getPaymentProductivity() {
		return paymentProductivity;
	}

	/**
	 * @param paymentProductivity the paymentProductivity to set
	 */
	public void setPaymentProductivity(PaymentProductivity paymentProductivity) {
		this.paymentProductivity = paymentProductivity;
	}

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return the insuranceName
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * @param insuranceName the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getTlRemark() {
		return tlRemark;
	}

	public void setTlRemark(String tlRemark) {
		this.tlRemark = tlRemark;
	}

}
