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
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "charge_productivity_reject")
@EntityListeners(value = EntityListener.class)
public class ChargeProdReject extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "patient_name")
	private String patientName;

	private String sequence;

	private String account;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "location_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Location location;

	@Column(name = "date_of_service")
	private Date dateOfService;

	@Column(name = "reason_to_reject")
	private String reasonToReject;

	@Column(name = "insurance_type")
	private String insuranceType;

	@Column(columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "remarks2", columnDefinition = "TEXT")
	private String remarks2;

	@Column(name = "date_of_first_request_to_doctor_office")
	private Date dateOfFirstRequestToDoctorOffice;

	@Column(name = "date_of_second_request_to_doctor_office")
	private Date dateOfSecondRequestToDoctorOffice;

	@Column(name = "dummy_cpt")
	private boolean dummyCpt = false;

	private boolean resolved = false;

	private String status = "Pending";

	@Column(name = "work_flow")
	private Integer workFlow = 0;

	@Column(name="resolved_on")
	private Date resolvedOn = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "resloved_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private User resolvedBy = null;

	@Column(name="completed_on")
	private Date completedOn = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "completed_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private User completedBy = null;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "charge_batch_id", referencedColumnName = "id", nullable = false)
	private ChargeBatchProcessing chargeBatchProcessing = null;

	private Date dob;

	@Transient
	private boolean addMore = false;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getDateOfService() {
		return dateOfService;
	}

	public void setDateOfService(Date dateOfService) {
		this.dateOfService = dateOfService;
	}

	public String getReasonToReject() {
		return reasonToReject;
	}

	public void setReasonToReject(String reasonToReject) {
		this.reasonToReject = reasonToReject;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks2() {
		return remarks2;
	}

	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}

	public Date getDateOfFirstRequestToDoctorOffice() {
		return dateOfFirstRequestToDoctorOffice;
	}

	public void setDateOfFirstRequestToDoctorOffice(
			Date dateOfFirstRequestToDoctorOffice) {
		this.dateOfFirstRequestToDoctorOffice = dateOfFirstRequestToDoctorOffice;
	}

	public Date getDateOfSecondRequestToDoctorOffice() {
		return dateOfSecondRequestToDoctorOffice;
	}

	public void setDateOfSecondRequestToDoctorOffice(
			Date dateOfSecondRequestToDoctorOffice) {
		this.dateOfSecondRequestToDoctorOffice = dateOfSecondRequestToDoctorOffice;
	}

	public boolean isDummyCpt() {
		return dummyCpt;
	}

	public void setDummyCpt(boolean dummyCpt) {
		this.dummyCpt = dummyCpt;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public ChargeBatchProcessing getChargeBatchProcessing() {
		return chargeBatchProcessing;
	}

	public void setChargeBatchProcessing(
			ChargeBatchProcessing chargeBatchProcessing) {
		this.chargeBatchProcessing = chargeBatchProcessing;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
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

	public Date getResolvedOn() {
		return resolvedOn;
	}

	public void setResolvedOn(Date resolvedOn) {
		this.resolvedOn = resolvedOn;
	}

	public User getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(User resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	public User getCompletedBy() {
		return completedBy;
	}

	public void setCompletedBy(User completedBy) {
		this.completedBy = completedBy;
	}

	/**
	 * @return the workFlow
	 */
	public Integer getWorkFlow() {
		return workFlow;
	}

	/**
	 * @param workFlow the workFlow to set
	 */
	public void setWorkFlow(Integer workFlow) {
		this.workFlow = workFlow;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
