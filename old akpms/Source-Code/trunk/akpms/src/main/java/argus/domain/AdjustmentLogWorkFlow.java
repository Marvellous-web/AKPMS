package argus.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ARLogWorkFlow")
@Entity
@Table(name = "adjustment_log_workflow")
@EntityListeners(EntityListener.class)
public class AdjustmentLogWorkFlow extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = false) })
	private ArProductivity arProductivity;

	/************* add fields from productivity (START) ************/
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "insurance_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Insurance insurance = null;

	// @XStreamConverter(value = XstreamDateConverter.class)
	private String dos;

	@Column(name = "cpt", length = 100)
	private String cpt;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Doctor doctor = null;

	private Float balanceAmt;
	/************* add fields from productivity (END) ************/

	/* This field not in use*/
	@Column(name = "without_timily_filing")
	private Boolean withoutTimilyFiling = false; //Not in use

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@Column(name = "manager_remark", columnDefinition = "TEXT")
	private String managerRemark;

	@Column(name = "work_flow_status")
	private int workFlowStatus;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(int workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	 * @return the managerRemark
	 */
	public String getManagerRemark() {
		return managerRemark;
	}

	/**
	 * @param managerRemark
	 *            the managerRemark to set
	 */
	public void setManagerRemark(String managerRemark) {
		this.managerRemark = managerRemark;
	}

	/**
	 * @return the withoutTimilyFiling
	 */
	public Boolean getWithoutTimilyFiling() {
		return withoutTimilyFiling;
	}

	/**
	 * @param withoutTimilyFiling
	 *            the withoutTimilyFiling to set
	 */
	public void setWithoutTimilyFiling(Boolean withoutTimilyFiling) {
		this.withoutTimilyFiling = withoutTimilyFiling;
	}

}
