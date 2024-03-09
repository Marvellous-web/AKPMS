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
import javax.persistence.Transient;

@Entity
@Table(name="charge_productivity")
@EntityListeners(value= EntityListener.class)
public class ChargeProductivity extends BaseEntity{

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

	// @Column(name= "post_date")
	// private Date postDate;

	@Column(name= "nuber_of_transactions")
	private Integer numberOfTransactions;

	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "unhold_remarks", columnDefinition = "TEXT")
	private String unholdRemarks;

	@Column( name = "time")
	private Integer time;

	@Column( name = "work_flow")
	private String workFlow;

	@Column(name = "demo_and_verification")
	private String demoAndVerification;

	@Column(name = "demo_review_and_verification")
	private String demoReviewAndVerification;

	@Column(name = "demo_review_only")
	private String demoReviewOnly;

	@Column(name = "productivity_units_account_and_codes")
	private String productivityUnitsAccountAndCodes;

	private String type;

	private Date onHoldOn;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns({ @JoinColumn(name = "onhold_by_id", referencedColumnName = "id", unique = false, nullable = true) })
	private User onHoldBy = null;

	@Transient
	private boolean onHoldFlag = false;

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

	// public Date getPostDate() {
	// return postDate;
	// }
	//
	// public void setPostDate(Date postDate) {
	// this.postDate = postDate;
	// }

	public Integer getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public void setNumberOfTransactions(Integer numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getDemoAndVerification() {
		return demoAndVerification;
	}

	public void setDemoAndVerification(String demoAndVerification) {
		this.demoAndVerification = demoAndVerification;
	}

	public String getDemoReviewAndVerification() {
		return demoReviewAndVerification;
	}

	public void setDemoReviewAndVerification(String demoReviewAndVerification) {
		this.demoReviewAndVerification = demoReviewAndVerification;
	}

	public String getDemoReviewOnly() {
		return demoReviewOnly;
	}

	public void setDemoReviewOnly(String demoReviewOnly) {
		this.demoReviewOnly = demoReviewOnly;
	}

	public String getProductivityUnitsAccountAndCodes() {
		return productivityUnitsAccountAndCodes;
	}

	public void setProductivityUnitsAccountAndCodes(
			String productivityUnitsAccountAndCodes) {
		this.productivityUnitsAccountAndCodes = productivityUnitsAccountAndCodes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	 * @return the onHoldFlag
	 */
	public boolean isOnHoldFlag() {
		return onHoldFlag;
	}

	/**
	 * @param onHoldFlag
	 *            the onHoldFlag to set
	 */
	public void setOnHoldFlag(boolean onHoldFlag) {
		this.onHoldFlag = onHoldFlag;
	}

}
