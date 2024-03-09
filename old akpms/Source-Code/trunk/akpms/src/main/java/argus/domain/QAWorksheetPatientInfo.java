package argus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "qa_worksheet_patient_info")
public class QAWorksheetPatientInfo extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -7370823622555907742L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumn(name = "qa_productivity_sampling_id", nullable = false, unique = false)
	private QAProductivitySampling qaProductivitySampling;

	// @OneToMany(fetch = FetchType.LAZY, targetEntity = QCPointChecklist.class,
	// cascade = {
	// CascadeType.REMOVE, CascadeType.REFRESH })
	// @JoinColumn(name = "qa_worksheet_patient_info_id", nullable = false)
	@OneToMany(mappedBy = "qaWorksheetPatientInfo", fetch = FetchType.LAZY, cascade = {
			CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
	private List<QCPointChecklist> qcPointChecklist = new ArrayList<QCPointChecklist>();

	@Column(name = "cpt_codes_demo", nullable = true, unique = false)
	private String cptCodesDemo;

	@Column(name = "remarks", nullable = true, unique = false, columnDefinition="TEXT", length=400)
	private String remarks;
	/* for joing the tables (many-to-many) */
	// @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
	// CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	// @JoinTable(name = "qc_point_checklist", joinColumns = { @JoinColumn(name
	// = "qa_worksheet_patient_info_id") }, inverseJoinColumns = {
	// @JoinColumn(name = "qc_point_id") })
	// private List<QcPoint> qcPoints = null;

	@Column(name = "patient_name", nullable = true)
	private String patientName;

	@Column(name = "account_number", nullable = true)
	private String accountNumber;

	@Column(name = "sr_no", nullable = true)
	private String srNo;

	@Column(name = "cheque", nullable = true)
	private String check;

	@Column(name = "transaction", nullable = true)
	private String transaction;

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

	public QAProductivitySampling getQaProductivitySampling() {
		return qaProductivitySampling;
	}

	public void setQaProductivitySampling(
			QAProductivitySampling qaProductivitySampling) {
		this.qaProductivitySampling = qaProductivitySampling;
	}

	// public List<QCPointChecklist> getQcPointChecklistInfos() {
	// return qcPointChecklistInfos;
	// }
	//
	// public void setQcPointChecklistInfos(
	// List<QCPointChecklist> qcPointChecklistInfos) {
	// this.qcPointChecklistInfos = qcPointChecklistInfos;
	// }

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public List<QCPointChecklist> getQcPointChecklist() {
		return qcPointChecklist;
	}

	public void setQcPointChecklist(List<QCPointChecklist> qcPointChecklist) {
		this.qcPointChecklist = qcPointChecklist;
	}

	public String getCptCodesDemo() {
		return cptCodesDemo;
	}

	public void setCptCodesDemo(String cptCodesDemo) {
		this.cptCodesDemo = cptCodesDemo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the srNo
	 */
	public String getSrNo() {
		return srNo;
	}

	/**
	 * @param srNo
	 *            the srNo to set
	 */
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}

}
