package argus.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ARRekeyRequestWorkFlow")
@Entity
@Table(name = "rekey_request_workflow")
public class RekeyRequestWorkFlow extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ArProductivity arProductivity;

	@Column(name = "batch_number")
	private String batchNumber;

	@Column(name = "request_reason")
	private String requestReason;

	@Column(name="status")
	private int status = 1;

	@Column(name = "charge_posting_remark", columnDefinition = "TEXT")
	private String chargePostingRemark;

	@Column(name = "coding_remark", columnDefinition = "TEXT")
	private String codingRemark;

	@Column(name = "charge_posting_remark_date")
	private Date chargePostingRemarkDate;

	@Column(name = "coding_remark_date")
	private Date codingRemarkDate;

	@Column(name="dos")
	private String dos;

	/************* add fields from productivity (START) ************/
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Insurance insurance = null;

	// @XStreamConverter(value = XstreamDateConverter.class)
	// private String dos;

	@Column(name = "cpt", length = 100)
	private String cpt;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Doctor doctor = null;

	private Float balanceAmt;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	/************* add fields from productivity (END) ************/

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "rekeyRequestWorkFlow", fetch = FetchType.EAGER, targetEntity = RekeyRequestRecord.class)
	private List<RekeyRequestRecord> rekeyRequestRecords;

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
	 * @return the arProductivity
	 */
	public ArProductivity getArProductivity() {
		return arProductivity;
	}

	/**
	 * @param arProductivity
	 *            the arProductivity to set
	 */
	public void setArProductivity(ArProductivity arProductivity) {
		this.arProductivity = arProductivity;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 *            the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the requestReason
	 */
	public String getRequestReason() {
		return requestReason;
	}

	/**
	 * @param requestReason
	 *            the requestReason to set
	 */
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the chargePostingRemark
	 */
	public String getChargePostingRemark() {
		return chargePostingRemark;
	}

	/**
	 * @param chargePostingRemark
	 *            the chargePostingRemark to set
	 */
	public void setChargePostingRemark(String chargePostingRemark) {
		this.chargePostingRemark = chargePostingRemark;
	}

	/**
	 * @return the codingRemark
	 */
	public String getCodingRemark() {
		return codingRemark;
	}

	/**
	 * @param codingRemark
	 *            the codingRemark to set
	 */
	public void setCodingRemark(String codingRemark) {
		this.codingRemark = codingRemark;
	}

	/**
	 * @return the chargePostingRemarkDate
	 */
	public Date getChargePostingRemarkDate() {
		return chargePostingRemarkDate;
	}

	/**
	 * @param chargePostingRemarkDate
	 *            the chargePostingRemarkDate to set
	 */
	public void setChargePostingRemarkDate(Date chargePostingRemarkDate) {
		this.chargePostingRemarkDate = chargePostingRemarkDate;
	}

	/**
	 * @return the codingRemarkDate
	 */
	public Date getCodingRemarkDate() {
		return codingRemarkDate;
	}

	/**
	 * @param codingRemarkDate
	 *            the codingRemarkDate to set
	 */
	public void setCodingRemarkDate(Date codingRemarkDate) {
		this.codingRemarkDate = codingRemarkDate;
	}

	/**
	 * @return the rekeyRequestRecords
	 */
	public List<RekeyRequestRecord> getRekeyRequestRecords() {
		return rekeyRequestRecords;
	}

	/**
	 * @param rekeyRequestRecords
	 *            the rekeyRequestRecords to set
	 */
	public void setRekeyRequestRecords(
			List<RekeyRequestRecord> rekeyRequestRecords) {
		this.rekeyRequestRecords = rekeyRequestRecords;
	}

	/**
	 * @return the dos
	 */
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
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
	 * @return the cpt
	 */
	public String getCpt() {
		return cpt;
	}

	/**
	 * @param cpt
	 *            the cpt to set
	 */
	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

}
