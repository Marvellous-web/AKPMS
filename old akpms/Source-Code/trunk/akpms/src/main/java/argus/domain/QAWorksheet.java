package argus.domain;

import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import argus.util.Constants;

@Entity
@Table(name = "qa_worksheet")
public class QAWorksheet extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH})
	@JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
	private Department department;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH})
	@JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
	private Department subDepartment;

	/** QAWorksheet status 0 saved ,1 run, 2 compleate*/
	@Column(name = "status")
	private Integer status = 0;

	@Column(name = "general_percentage")
	private Float generalPercentage;

	@Column(name = "account_percentage")
	private Float accountPercentage;

	@Column(name = "billing_month")
	private Integer billingMonth;

	@Column(name = "billing_year")
	private Integer billingYear;

	@Column(name = "posting_date_from")
	private Date postingDateFrom;

	@Column(name = "posting_date_to")
	private Date postingDateTo;
	
	@Column(name = "scan_date_from")
	private Date scanDateFrom;
	
	@Column(name = "scan_date_to")
	private Date scanDateTo;
	
	public Date getScanDateFrom() {
		return scanDateFrom;
	}

	public void setScanDateFrom(Date scanDateFrom) {
		this.scanDateFrom = scanDateFrom;
	}

	public Date getScanDateTo() {
		return scanDateTo;
	}

	public void setScanDateTo(Date scanDateTo) {
		this.scanDateTo = scanDateTo;
	}

	@Column(name = "name")
	private String name;
	
	/* Field added  for Genrating QA report (QC Point Filtered by Created user Id)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private User createdBy;*/

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private Boolean deleted = false;

	@OneToMany(targetEntity = QAWorksheetStaff.class ,mappedBy = "qaWorksheet",fetch=FetchType.EAGER)
	private List<QAWorksheetStaff> qaWorksheetStaffs = new ArrayList<QAWorksheetStaff>();

	private Short type = Constants.QA_WORKSEET_TYPE_GENERAL;

	// @Null
	// @Column(name = "multi_dept")
	// private String multiDept;

	@Column(name="ar_status_code")
	private String arStatusCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getGeneralPercentage() {
		return generalPercentage;
	}

	public void setGeneralPercentage(Float generalPercentage) {
		this.generalPercentage = generalPercentage;
	}

	public Integer getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(Integer billingMonth) {
		this.billingMonth = billingMonth;
	}

	public Integer getBillingYear() {
		return billingYear;
	}

	public void setBillingYear(Integer billingYear) {
		this.billingYear = billingYear;
	}

	public Date getPostingDateFrom() {
		return postingDateFrom;
	}

	public void setPostingDateFrom(Date postingDateFrom) {
		this.postingDateFrom = postingDateFrom;
	}

	public Date getPostingDateTo() {
		return postingDateTo;
	}

	public void setPostingDateTo(Date postingDateTo) {
		this.postingDateTo = postingDateTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(Department subDepartment) {
		this.subDepartment = subDepartment;
	}

	public List<QAWorksheetStaff> getQaWorksheetStaffs() {
		return qaWorksheetStaffs;
	}

	public void setQaWorksheetStaffs(List<QAWorksheetStaff> qaWorksheetStaffs) {
		this.qaWorksheetStaffs = qaWorksheetStaffs;
	}

	public String getArStatusCode() {
		return arStatusCode;
	}

	public void setArStatusCode(String arStatusCode) {
		this.arStatusCode = arStatusCode;
	}

	public Float getAccountPercentage() {
		return accountPercentage;
	}

	public void setAccountPercentage(Float accountPercentage) {
		this.accountPercentage = accountPercentage;
	}

	/**
	 * @return the multiDept
	 */
	// public String getMultiDept() {
	// return multiDept;
	// }

	/**
	 * @param multiDept
	 *            the multiDept to set
	 */
	// public void setMultiDept(String multiDept) {
	// this.multiDept = multiDept;
	// }

}
