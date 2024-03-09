/**
 *
 */
package argus.domain.paymentproductivity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import argus.domain.EntityListener;
import argus.util.Constants;

/**
 * @author vishal.joshi
 *
 */
@Entity
@Table(name = "offset_posting_record")
@EntityListeners(EntityListener.class)
public class OffsetPostingRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dos_from")
	private Date dosFrom;

	@Column(name = "dos_to")
	private Date dosTo;

	@Column(name = "amount")
	private Double amount = 0.0;

	@Column(name = "cpt", length = Constants.HUNDRED)
	private String cpt;

	@ManyToOne
	@JoinColumn(name = "offset_manager_id", referencedColumnName = "id", unique = false, nullable = false)
	private PaymentPostingByOffSetManager offSetManager;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dosFrom
	 */
	public Date getDosFrom() {
		return dosFrom;
	}

	/**
	 * @param dosFrom
	 *            the dosFrom to set
	 */
	public void setDosFrom(Date dosFrom) {
		this.dosFrom = dosFrom;
	}

	/**
	 * @return the dosTo
	 */
	public Date getDosTo() {
		return dosTo;
	}

	/**
	 * @param dosTo
	 *            the dosTo to set
	 */
	public void setDosTo(Date dosTo) {
		this.dosTo = dosTo;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the offSetManager
	 */
	public PaymentPostingByOffSetManager getOffSetManager() {
		return offSetManager;
	}

	/**
	 * @param offSetManager
	 *            the offSetManager to set
	 */
	public void setOffSetManager(PaymentPostingByOffSetManager offSetManager) {
		this.offSetManager = offSetManager;
	}

	/**
	 * @return the cpt
	 */
	public String getCpt() {
		return cpt;
	}

	/**
	 * @param cpt
	 *            the cpt to set
	 */
	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

}
