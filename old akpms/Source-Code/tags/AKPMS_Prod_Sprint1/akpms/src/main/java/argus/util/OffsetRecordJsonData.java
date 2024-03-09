/**
 *
 */
package argus.util;


/**
 * @author vishal.joshi
 *
 */
public class OffsetRecordJsonData {

	private Long id;

	private Double amount;

	private String cpt;

	private String dos;

	private Long offsetId;

	/**
	 * @return the offsetId
	 */
	public Long getOffsetId() {
		return offsetId;
	}

	/**
	 * @param offsetId
	 *            the offsetId to set
	 */
	public void setOffsetId(Long offsetId) {
		this.offsetId = offsetId;
	}

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
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
	}

}
