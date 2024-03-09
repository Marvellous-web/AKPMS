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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "charge_productivity")
@EntityListeners(value = EntityListener.class)
public class ChargeProductivity extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "unhold_remarks", columnDefinition = "TEXT")
	private String unholdRemarks;

	@Column(name = "time")
	private Float time;

	@Column(name = "work_flow")
	private String workFlow;

	private Date onHoldOn;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "onhold_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private User onHoldBy = null;

	@Column(name = "scan_date")
	@Temporal(TemporalType.DATE)
	private Date scanDate;

	@NotNull
	@Column(name = "onhold", columnDefinition = "tinyint(1) default '0'")
	private boolean onHold = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Date getOnHoldOn() {
		return onHoldOn;
	}

	/**
	 * @param onHoldOn
	 *            the onHoldOn to set
	 */
	public void setOnHoldOn(Date onHoldOn) {
		this.onHoldOn = onHoldOn;
	}

	/**
	 * @return the onHoldBy
	 */
	public User getOnHoldBy() {
		return onHoldBy;
	}

	/**
	 * @param onHoldBy
	 *            the onHoldBy to set
	 */
	public void setOnHoldBy(User onHoldBy) {
		this.onHoldBy = onHoldBy;
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
