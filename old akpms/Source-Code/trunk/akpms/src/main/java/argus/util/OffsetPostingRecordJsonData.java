/**
 *
 */
package argus.util;


/**
 * @author vishal.joshi
 *
 */
public class OffsetPostingRecordJsonData {

	private Long id;

	private String dosFrom;

	private String dosTo;

	private Double amount;

	private String cpt;

	private Long offsetId;

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
	public String getDosFrom() {
		return dosFrom;
	}

	/**
	 * @param dosFrom
	 *            the dosFrom to set
	 */
	public void setDosFrom(String dosFrom) {
		this.dosFrom = dosFrom;
	}

	/**
	 * @return the dosTo
	 */
	public String getDosTo() {
		return dosTo;
	}

	/**
	 * @param dosTo
	 *            the dosTo to set
	 */
	public void setDosTo(String dosTo) {
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

}
