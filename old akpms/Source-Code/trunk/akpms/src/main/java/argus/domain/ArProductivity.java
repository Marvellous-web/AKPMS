/**
 *
 */
package argus.domain;

import java.util.Date;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author vishal.joshi
 *
 */
@XStreamAlias("ArProductivity")
@Entity
@Table(name = "ar_productivity")
@EntityListeners(EntityListener.class)
public class ArProductivity extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "patient_acc_no")
	private String patientAccNo;

	private String patientName;

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

	// private String dataBas;
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "database_id", referencedColumnName = "id", unique = false, nullable = false) })
	private ArDatabase arDatabase = null;

	private Float balanceAmt;

	private String source = "";

	private String statusCode = "";

	// not in use now 15/11/2013 : Bhupinder
	private int workFlow = 0;

	@Column(name = "tl_remark", columnDefinition = "TEXT")
	private String tlRemark;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	private Boolean timilyFiling = false;

	@Column(name = "sub_status")
	private Integer subStatus;
	
	@Column(name = "productivity_type")
	private Integer productivityType;

	public Integer getProductivityType() {
		return productivityType;
	}

	public void setProductivityType(Integer productivityType) {
		this.productivityType = productivityType;
	}

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinTable(name = "arprod_workflow_rel", joinColumns = { @JoinColumn(name = "prod_id") }, inverseJoinColumns = { @JoinColumn(name = "workfLow_id") })
	@Fetch(FetchMode.SUBSELECT)
	// @LazyCollection(LazyCollectionOption.FALSE)
	private List<ArProductivityWorkFlow> workFlows = null;

	@Transient
	private String workFlowName;

	// @Transient
	// private String sourceName;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

//	@Column(name="team_id", nullable=true)
//	private Integer teamId;
	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "team_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Department team = null;

	@Column(name = "follow_up_date")
	private Date followUpDate;

	public String getPatientAccNo() {
		return patientAccNo;
	}

	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Float getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public String getTlRemark() {
		return tlRemark;
	}

	public void setTlRemark(String tlRemark) {
		this.tlRemark = tlRemark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/*
	 * public String getDataBas() { return dataBas; }
	 *
	 * public void setDataBas(String dataBas) { this.dataBas = dataBas; }
	 */

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(int workFlow) {
		this.workFlow = workFlow;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	/*
	 * public String getSourceName() { return sourceName; }
	 *
	 * public void setSourceName(String sourceName) { this.sourceName =
	 * sourceName; }
	 */

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the timilyFilling
	 */
	public Boolean getTimilyFiling() {
		return timilyFiling;
	}

	/**
	 * @param timilyFilling
	 *            the timilyFilling to set
	 */
	public void setTimilyFiling(Boolean timilyFiling) {
		this.timilyFiling = timilyFiling;
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
	 * @return the workFlows
	 */
	public List<ArProductivityWorkFlow> getWorkFlows() {
		return workFlows;
	}

	/**
	 * @param workFlows
	 *            the workFlows to set
	 */
	public void setWorkFlows(List<ArProductivityWorkFlow> workFlows) {
		this.workFlows = workFlows;
	}

	/**
	 * @return the arDatabase
	 */
	public ArDatabase getArDatabase() {
		return arDatabase;
	}

	/**
	 * @param arDatabase
	 *            the arDatabase to set
	 */
	public void setArDatabase(ArDatabase arDatabase) {
		this.arDatabase = arDatabase;
	}

	/**
	 * @return the team
	 */
	public Department getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(Department team) {
		this.team = team;
	}

	/**
	 * @return the subStatus
	 */
	public Integer getSubStatus() {
		return subStatus;
	}

	/**
	 * @param subStatus
	 *            the subStatus to set
	 */
	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	/**
	 * @return the followUpDate
	 */
	public Date getFollowUpDate() {
		return followUpDate;
	}

	/**
	 * @param followUpDate
	 *            the followUpDate to set
	 */
	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

}
