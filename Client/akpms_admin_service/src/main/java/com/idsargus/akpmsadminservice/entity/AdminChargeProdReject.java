package com.idsargus.akpmsadminservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "charge_productivity_reject")
public class AdminChargeProdReject extends AdminBaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "patient_name")
	private String patientName;

	private String sequence;

	private String account;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({@JoinColumn(name = "location_id", referencedColumnName = "id", unique = false, nullable = true)})
	private AdminLocation location;


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

	@Column(name = "resolved_on")
	private Date resolvedOn = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({@JoinColumn(name = "resloved_by_id", referencedColumnName = "id", unique = false, nullable = true)})
	private User resolvedBy = null;


	@Column(name = "completed_on")
	private Date completedOn = null;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({@JoinColumn(name = "completed_by_id", referencedColumnName = "id", unique = false, nullable = true)})
	private User completedBy = null;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "charge_batch_id", referencedColumnName = "id", nullable = true)
	private AdminChargeBatchProcessing chargeBatchProcessing = null;

	private Date dob;

	@Transient
	private boolean addMore = false;

	@Transient
	private Integer ticketNumber;
	@Transient
	private String locationName;
//
//	@ManyToOne(
//			fetch = FetchType.LAZY
//	)
//	@JoinColumn(
//			name = "doctor_id",
//			referencedColumnName = "id",
//			unique = false,
//			nullable = true
//	)
//	private DoctorEntity doctor;
//	@Transient
//	public String doctorName;
//
//	public String getDoctorName(){
//		return (this.chargeBatchProcessing.getDoctor().getName() == null) ? null : this.chargeBatchProcessing.getDoctor().getName();
//	}
	public String getLocationName() {
		return (this.location == null) ? null : this.location.getName();
	}

	public Integer getTicketNumber() {
		return (this.chargeBatchProcessing == null) ? null : this.chargeBatchProcessing.getId();
	}

}