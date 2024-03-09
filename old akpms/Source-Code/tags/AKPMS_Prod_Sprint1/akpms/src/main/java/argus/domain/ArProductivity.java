/**
 *
 */
package argus.domain;

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
import javax.validation.constraints.NotNull;

/**
 * @author vishal.joshi
 *
 */
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

	private Date dos;

	private String cpt;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "provider_id", referencedColumnName = "id", unique = false, nullable = false) })
	private Doctor doctor = null;

	private String dataBas;

	private Float balanceAmt;

	private int source = 0;

	private int status = 0;

	private int workFlow = 0;

	@Column(name = "tl_remark", columnDefinition = "TEXT")
	private String tlRemark;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@Transient
	private String workFlowName;

	@Transient
	private String sourceName;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

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

	public Date getDos() {
		return dos;
	}

	public void setDos(Date dos) {
		this.dos = dos;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDataBas() {
		return dataBas;
	}

	public void setDataBas(String dataBas) {
		this.dataBas = dataBas;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
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
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}
