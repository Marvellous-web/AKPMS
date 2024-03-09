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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author vishal.joshi
 *
 */
@XStreamAlias("OffsetRecord")
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "offset_record")
public class OffsetRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "amount")
	private Double amount = 0.0;

	@Column(name = "cpt", length = 50)
	private String cpt;

	@Column(name = "dos")
	private Date dos;

	@XStreamOmitField
	@ManyToOne
	@JoinColumn(name = "offset_id", referencedColumnName = "id", unique = false, nullable = false)
	private PaymentProductivityOffset productivityOffset;

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

	/**
	 * @return the dos
	 */
	public Date getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(Date dos) {
		this.dos = dos;
	}

	/**
	 * @return the productivityOffset
	 */
	public PaymentProductivityOffset getProductivityOffset() {
		return productivityOffset;
	}

	/**
	 * @param productivityOffset
	 *            the productivityOffset to set
	 */
	public void setProductivityOffset(
			PaymentProductivityOffset productivityOffset) {
		this.productivityOffset = productivityOffset;
	}

}
