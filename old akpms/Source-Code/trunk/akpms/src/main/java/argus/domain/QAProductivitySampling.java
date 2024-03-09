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
import javax.persistence.Transient;

import argus.domain.paymentproductivity.CredentialingAccountingProductivity;
import argus.domain.paymentproductivity.PaymentProductivity;

@Entity
@Table(name = "qa_productivity_sampling")
public class QAProductivitySampling extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 3862421554623983476L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * @Column(name = "productivity_id", nullable = false, unique = false)
	 * private Long productivityId;
	 */

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "qa_worksheet_id", nullable = true, referencedColumnName = "id")
	private QAWorksheet qaWorksheet;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "payment_productivity_id", nullable = true, referencedColumnName = "id")
	private PaymentProductivity paymentProductivity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "charge_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
	private ChargeProductivity chargeProductivity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "ar_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
	private ArProductivity arProductivity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "credentialing_accounting_productivity_id", nullable = true, unique = false, referencedColumnName = "id")
	private CredentialingAccountingProductivity credentialingAccountingProductivity;

	@OneToMany(mappedBy = "qaProductivitySampling", targetEntity = QAWorksheetPatientInfo.class, cascade = {
			CascadeType.DETACH, CascadeType.REFRESH })
	private List<QAWorksheetPatientInfo> qaWorksheetPatientInfos = new ArrayList<QAWorksheetPatientInfo>();

	@OneToMany(mappedBy = "qaProductivitySampling", targetEntity = QCPointChecklist.class, cascade = {
			CascadeType.REFRESH, CascadeType.REMOVE }, orphanRemoval = true)
	private List<QCPointChecklist> qcPointChecklists = new ArrayList<QCPointChecklist>();

	@Column(name = "remarks", nullable = true, unique = false, columnDefinition = "TEXT", length = 400)
	private String remarks;
	
	@Transient
	private Float percentage;
	
	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}


	/*
	 * @Column(name = "batch_percentage", nullable = true) private Float
	 * batchPercentage;
	 * 
	 * @Column(name = "account_count", nullable = true) private Integer
	 * accountCount;
	 */

	@Column(name = "cpt_count", nullable = true)
	private Integer cptCount;

	@Column(name = "hidden", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean hidden = false;

	/*
	 * @ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.REFRESH,
	 * CascadeType.DETACH})
	 * 
	 * @JoinColumn(name = "department_id", nullable = false, unique = false,
	 * referencedColumnName="id") private Department departmentId;
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public Long getProductivityId() { return productivityId; }
	 * 
	 * public void setProductivityId(Long productivityId) { this.productivityId
	 * = productivityId; }
	 */

	public PaymentProductivity getPaymentProductivity() {
		return paymentProductivity;
	}

	public void setPaymentProductivity(PaymentProductivity paymentProductivity) {
		this.paymentProductivity = paymentProductivity;
	}

	public ChargeProductivity getChargeProductivity() {
		return chargeProductivity;
	}

	public void setChargeProductivity(ChargeProductivity chargeProductivity) {
		this.chargeProductivity = chargeProductivity;
	}

	public ArProductivity getArProductivity() {
		return arProductivity;
	}

	public void setArProductivity(ArProductivity arProductivity) {
		this.arProductivity = arProductivity;
	}

	public CredentialingAccountingProductivity getCredentialingAccountingProductivity() {
		return credentialingAccountingProductivity;
	}

	public void setCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity) {
		this.credentialingAccountingProductivity = credentialingAccountingProductivity;
	}

	/*
	 * public Department getDepartmentId() { return departmentId; }
	 * 
	 * public void setDepartmentId(Department departmentId) { this.departmentId
	 * = departmentId; }
	 */

	public QAWorksheet getQaWorksheet() {
		return qaWorksheet;
	}

	public void setQaWorksheet(QAWorksheet qaWorksheet) {
		this.qaWorksheet = qaWorksheet;
	}

	public List<QAWorksheetPatientInfo> getQaWorksheetPatientInfos() {
		return qaWorksheetPatientInfos;
	}

	public void setQaWorksheetPatientInfos(
			List<QAWorksheetPatientInfo> qaWorksheetPatientInfos) {
		this.qaWorksheetPatientInfos = qaWorksheetPatientInfos;
	}

	public List<QCPointChecklist> getQcPointChecklists() {
		return qcPointChecklists;
	}

	public void setQcPointChecklists(List<QCPointChecklist> qcPointChecklists) {
		this.qcPointChecklists = qcPointChecklists;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the cptCount
	 */
	public Integer getCptCount() {
		return cptCount;
	}

	/**
	 * @param cptCount
	 *            the cptCount to set
	 */
	public void setCptCount(Integer cptCount) {
		this.cptCount = cptCount;
	}

}
