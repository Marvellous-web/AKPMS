package com.idsargus.akpmscommonservice.entity;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charge_productivity")
public class ChargeProductivity extends BaseAuditableEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumns(@JoinColumn(name = "ticket_number", referencedColumnName = "id", nullable = false, unique = false))
	private ChargeBatchProcessing ticketNumber;

	@Column(name = "productivity_type")
	private String productivityType;

	@Column(name = "t1")
	private Integer t1 = 0;// ce:ffs, demo:new customer, coding:number of a/c

	@Column(name = "t2")
	private Integer t2 = 0;// ce:cap, demo:existing customer, coding:number of
							// ICDs

	@Column(name = "t3")
	private Integer t3 = 0;// ce:, demo:, coding:number of CPTs

	@Column(name= "t4")
	private Integer t4 = 0;

	@Column(name = "t5")
	private Integer t5 = 0;

	@Column(name = "number_of_reviews")
    private Integer numberOfReviews;

	@Column(name = "number_of_reports" )
    private Integer numberOfReports;
	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "unhold_remarks", columnDefinition = "TEXT")
	private String unholdRemarks;

	@Column(name = "time")
	private Float time;

	@Column(name = "work_flow")
	private String workFlow;

	@Column(name = "on_hold_on")
	private String onHoldOn;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "onhold_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private UserEntity onHoldBy = null;

	@Column(name = "scan_date")
	@Temporal(TemporalType.DATE)
	private Date scanDate;

	@NotNull
	@Column(name = "onhold", columnDefinition = "tinyint(1) default '0'")
	private boolean onHold = false;


//	@JoinColumn(
//			name = "posted_by",
//			referencedColumnName = "id",
//			nullable = true,
//			unique = false
//	)
//	private UserEntity postedBy;
	@Transient
	private String providerName;

	public String getProviderName()
	{
		return (this.ticketNumber.getDoctor()== null) ? null : this.ticketNumber.getDoctor().getName();
	}
	public ChargeBatchProcessing getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(ChargeBatchProcessing ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getProductivityType() {
		return productivityType;
	}

	public void setProductivityType(String productivityType) {
		this.productivityType = productivityType;
	}

	public String getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(String workFlow) {
		this.workFlow = workFlow;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Float getTime() {
		return time;
	}

	public void setTime(Float time) {
		this.time = time;
	}

	public String getUnholdRemarks() {
		return unholdRemarks;
	}

	public void setUnholdRemarks(String unholdRemarks) {
		this.unholdRemarks = unholdRemarks;
	}

	/**
	 * @return the onHoldOn
	 */
	public String getOnHoldOn() {
		return onHoldOn;
	}

	/**
	 * @param onHoldOn
	 *            the onHoldOn to set
	 */
	public void setOnHoldOn(String onHoldOn) {
		this.onHoldOn = onHoldOn;
	}

	/**
	 * @return the t1
	 */
	public Integer getT1() {
		return t1;
	}

	/**
	 * @param t1
	 *            the t1 to set
	 */
	public void setT1(Integer t1) {
		this.t1 = t1;
	}

	/**
	 * @return the t2
	 */
	public Integer getT2() {
		return t2;
	}

	/**
	 * @param t2
	 *            the t2 to set
	 */
	public void setT2(Integer t2) {
		this.t2 = t2;
	}

	/**
	 * @return the t3
	 */
	public Integer getT3() {
		return t3;
	}

	/**
	 * @param t3
	 *            the t3 to set
	 */
	public void setT3(Integer t3) {
		this.t3 = t3;
	}

	/**
	 * @return the scanDate
	 */
	public Date getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate
	 *            the scanDate to set
	 */
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return the onHold
	 */
	public boolean isOnHold() {
		return onHold;
	}

	/**
	 * @param onHold
	 *            the onHold to set
	 */
	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

}