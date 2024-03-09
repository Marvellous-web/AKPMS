package argus.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	private Integer sequence;

	private String account;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_batch_id", referencedColumnName = "id", nullable = false)
	private ChargeBatchProcessing chargeBatchProcessing = null;

	private Date dob;

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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

}
