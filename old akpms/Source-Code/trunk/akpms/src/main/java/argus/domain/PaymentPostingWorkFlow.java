package argus.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment_posting_workflow")
@EntityListeners(EntityListener.class)
public class PaymentPostingWorkFlow extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ArProductivity arProductivity;

	@Column(name = "cpt")
	private String cpt;

	@Column(name = "billed_amount")
	private Double billedAmount = 0.0;

	@Column(name = "primary_amount")
	private Double primaryAmount = 0.0;

	@Column(name = "secondary_amount")
	private Double secondaryAmount = 0.0;

	@Column(name = "contractual_adj")
	private Double contractualAdj = 0.0;

	@Column(name = "bulk_payment_amount")
	private Double bulkPaymentAmount = 0.0;

	@Column(name = "patient_response")
	private Double patientResponse;

	@Column(name = "check_issue_date")
	private Date checkIssueDate = null;

	@Column(name = "check_no")
	private String checkNo;

	@Column(name = "check_cashed_date", columnDefinition = "TEXT")
	private Date checkCashedDate = null;

	@Column(name = "address_check_send")
	private String addressCheckSend;

	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumns({ @JoinColumn(name = "copy_cancel_check", referencedColumnName = "id", nullable = true) })
	private Files copyCancelCheck = null;

	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumns({ @JoinColumn(name = "eob", referencedColumnName = "id", nullable = true) })
	private Files eob = null;

	@NotNull
	@Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
	private boolean deleted = false;

	@Column(name = "status")
	private String status;

	@Column(name = "ticketNumber")
	private String ticketNumber;

	@Transient
	private boolean addMore = false;

	private boolean isEobAvailable = false;

	private boolean offSet = false;

	/************* add fields from productivity (START) ************/
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Insurance insurance = null;

	// @XStreamConverter(value = XstreamDateConverter.class)
	private String dos;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Doctor doctor = null;

	private Float balanceAmt;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	/************* add fields from productivity (END) ************/

	public boolean isEobAvailable() {
		return isEobAvailable;
	}

	public void setEobAvailable(boolean isEobAvailable) {
		this.isEobAvailable = isEobAvailable;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArProductivity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public Double getPrimaryAmount() {
		return primaryAmount;
	}

	public void setPrimaryAmount(Double primaryAmount) {
		this.primaryAmount = primaryAmount;
	}

	public Double getSecondaryAmount() {
		return secondaryAmount;
	}

	public void setSecondaryAmount(Double secondaryAmount) {
		this.secondaryAmount = secondaryAmount;
	}

	public Double getContractualAdj() {
		return contractualAdj;
	}

	public void setContractualAdj(Double contractualAdj) {
		this.contractualAdj = contractualAdj;
	}

	public Double getBulkPaymentAmount() {
		return bulkPaymentAmount;
	}

	public void setBulkPaymentAmount(Double bulkPaymentAmount) {
		this.bulkPaymentAmount = bulkPaymentAmount;
	}

	/**
	 * @return the patientResponse
	 */
	public Double getPatientResponse() {
		return patientResponse;
	}

	/**
	 * @param patientResponse
	 *            the patientResponse to set
	 */
	public void setPatientResponse(Double patientResponse) {
		this.patientResponse = patientResponse;
	}

	public Date getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(Date checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public Date getCheckCashedDate() {
		return checkCashedDate;
	}

	public void setCheckCashedDate(Date checkCashedDate) {
		this.checkCashedDate = checkCashedDate;
	}

	public String getAddressCheckSend() {
		return addressCheckSend;
	}

	public void setAddressCheckSend(String addressCheckSend) {
		this.addressCheckSend = addressCheckSend;
	}

	public Files getCopyCancelCheck() {
		return copyCancelCheck;
	}

	public void setCopyCancelCheck(Files copyCancelCheck) {
		this.copyCancelCheck = copyCancelCheck;
	}

	public Files getEob() {
		return eob;
	}

	public void setEob(Files eob) {
		this.eob = eob;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAddMore() {
		return addMore;
	}

	public void setAddMore(boolean addMore) {
		this.addMore = addMore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return the insurance
	 */
	public Insurance getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the dos
	 */
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
	}

	/**
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor
	 *            the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the balanceAmt
	 */
	public Float getBalanceAmt() {
		return balanceAmt;
	}

	/**
	 * @param balanceAmt
	 *            the balanceAmt to set
	 */
	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
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
	 * @return the offSet
	 */
	public boolean isOffSet() {
		return offSet;
	}

	/**
	 * @param offSet
	 *            the offSet to set
	 */
	public void setOffSet(boolean offSet) {
		this.offSet = offSet;
	}

}
