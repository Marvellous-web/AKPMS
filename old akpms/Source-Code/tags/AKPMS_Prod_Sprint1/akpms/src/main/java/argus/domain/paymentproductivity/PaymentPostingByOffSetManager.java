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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import argus.domain.BaseEntity;
import argus.domain.EntityListener;
import argus.domain.Files;
import argus.domain.PaymentBatch;

/**
 * @author vishal.joshi
 *
 */
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "payment_productivity_offset_by_manager")
public class PaymentPostingByOffSetManager extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "payment_batch_id", referencedColumnName = "id", unique = false, nullable = false) })
	private PaymentBatch paymentBatch;

	@Column(name = "date_of_check")
	private Date dateOfCheck;

	@Column(name = "check_number")
	private String checkNumber;

	@Column(name = "fcn_ar")
	private String fcnOrAR;

	@Column(name = "time")
	private Integer time;

	@Column(name = "total_posted")
	private Double totalPosted;

	@Column(name = "patient_name")
	private String patientName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "offSetManager", targetEntity = OffsetPostingRecord.class)
	private List<OffsetPostingRecord> postingRecords;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = true) })
	private Files attachment;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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


	/**
	 * @return the dateOfCheck
	 */
	public Date getDateOfCheck() {
		return dateOfCheck;
	}

	/**
	 * @param dateOfCheck
	 *            the dateOfCheck to set
	 */
	public void setDateOfCheck(Date dateOfCheck) {
		this.dateOfCheck = dateOfCheck;
	}

	/**
	 * @return the checkNumber
	 */
	public String getCheckNumber() {
		return checkNumber;
	}

	/**
	 * @param checkNumber
	 *            the checkNumber to set
	 */
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	/**
	 * @return the fcnOrAR
	 */
	public String getFcnOrAR() {
		return fcnOrAR;
	}

	/**
	 * @param fcnOrAR
	 *            the fcnOrAR to set
	 */
	public void setFcnOrAR(String fcnOrAR) {
		this.fcnOrAR = fcnOrAR;
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
	 * @return the postingRecords
	 */
	public List<OffsetPostingRecord> getPostingRecords() {
		return postingRecords;
	}

	/**
	 * @param postingRecords
	 *            the postingRecords to set
	 */
	public void setPostingRecords(List<OffsetPostingRecord> postingRecords) {
		this.postingRecords = postingRecords;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the totalPosted
	 */
	public Double getTotalPosted() {
		return totalPosted;
	}

	/**
	 * @param totalPosted
	 *            the totalPosted to set
	 */
	public void setTotalPosted(Double totalPosted) {
		this.totalPosted = totalPosted;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName
	 *            the patientName to set
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
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the attachment
	 */
	public Files getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(Files attachment) {
		this.attachment = attachment;
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

}
