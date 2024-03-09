package argus.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="payment_posting_workflow")
public class PaymentPostingWorkFlow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumns({ @JoinColumn(name = "ar_productivity_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ArProductivity arProductivity;

	@Column(name="cpt")
	private String cpt;

	@Column(name="billed_amount")
	private Double billedAmount;

	@Column(name="primary_amount")
	private Double primaryAmount;

	@Column(name="secondary_amount")
	private Double secondaryAmount;

	@Column(name="contractual_adj")
	private Double contractualAdj;

	@Column(name="bulk_payment_amount")
	private Double bulkPaymentAmount;

	@Column(name="patient_response")
	private Double patientResponse;

	@Column(name="check_issue_date")
	private Date checkIssueDate;

	@Column(name="check_no")
	private String checkNo;

	@Column(name="check_cashed_date")
	private Date checkCashedDate;


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

	public Double getPatientResponse() {
		return patientResponse;
	}

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


}
