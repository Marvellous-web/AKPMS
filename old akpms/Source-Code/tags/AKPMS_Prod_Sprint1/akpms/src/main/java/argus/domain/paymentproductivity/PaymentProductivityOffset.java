/**
 *
 */
package argus.domain.paymentproductivity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import argus.domain.BaseEntity;
import argus.domain.EntityListener;
import argus.domain.PaymentBatch;

/**
 * @author rajiv.k
 *
 */
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "payment_productivity_offset")
public class PaymentProductivityOffset extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chk_number")
	private String chkNumber;

	@Column(name = "chk_date")
	private Date chkDate;

	@Column(name = "patient_name")
	private String patientName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "productivityOffset", targetEntity = OffsetRecord.class)
	private List<OffsetRecord> offsetRecords;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "payment_batch_id", referencedColumnName = "id", unique = false, nullable = false) })
	private PaymentBatch paymentBatch;

	@Column(name = "offset_remark", columnDefinition = "TEXT")
	private String offsetRemark;

	@Transient
	private String ticketNumber;

	@Transient
	private String insuranceName;

	@Transient
	@Column(columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean offset = false;

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
	 * @return the chkDate
	 */
	public Date getChkDate() {
		return chkDate;
	}

	/**
	 * @param chkDate
	 *            the chkDate to set
	 */
	public void setChkDate(Date chkDate) {
		this.chkDate = chkDate;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber
	 *            the ticketNumber to set
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
	 * @param insuranceName
	 *            the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	/**
	 * @return the offsetRecords
	 */
	public List<OffsetRecord> getOffsetRecords() {
		return offsetRecords;
	}

	/**
	 * @param offsetRecords
	 *            the offsetRecords to set
	 */
	public void setOffsetRecords(List<OffsetRecord> offsetRecords) {
		this.offsetRecords = offsetRecords;
	}

	public String getOffsetRemark() {
		return offsetRemark;
	}

	public void setOffsetRemark(String offsetRemark) {
		this.offsetRemark = offsetRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the offset
	 */
	public boolean isOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(boolean offset) {
		this.offset = offset;
	}

	/**
	 * @return the paymentBatch
	 */
	public PaymentBatch getPaymentBatch() {
		return paymentBatch;
	}

	/**
	 * @param paymentBatch
	 *            the paymentBatch to set
	 */
	public void setPaymentBatch(PaymentBatch paymentBatch) {
		this.paymentBatch = paymentBatch;
	}


}
