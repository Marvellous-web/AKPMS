/**
 *
 */
package argus.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author vishal.joshi
 *
 */
@Entity
@Table(name = "payment_batch_money_source")
@EntityListeners(EntityListener.class)
public class PaymentBatchMoneySource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "money_source", referencedColumnName = "id", unique = false, nullable = true) })
	private MoneySource moneySource = null;

	@ManyToOne
	@JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = false)
	private PaymentBatch paymentBatch;

	private Double amount = 0.0;

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
	 * @return the moneySource
	 */
	public MoneySource getMoneySource() {
		return moneySource;
	}

	/**
	 * @param moneySource
	 *            the moneySource to set
	 */
	public void setMoneySource(MoneySource moneySource) {
		this.moneySource = moneySource;
	}

	/**
	 * @return the paymentBatch
	 */
	public PaymentBatch getPaymentBatch() {
		return paymentBatch;
	}

	/**
	 * @param paymentBatch
	 *            the paymentBatch to set
	 */
	public void setPaymentBatch(PaymentBatch paymentBatch) {
		this.paymentBatch = paymentBatch;
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

}
