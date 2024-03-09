/**
 *
 */
package argus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author vishal.joshi
 *
 */
@XStreamAlias("RekeyRequestRecord")
@Entity
@EntityListeners(EntityListener.class)
@Table(name = "rekey_request_record")
public class RekeyRequestRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cpt", length = 50)
	private String cpt;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

	@XStreamOmitField
	@ManyToOne
	@JoinColumn(name = "rekey_request_id", referencedColumnName = "id", unique = false, nullable = false)
	private RekeyRequestWorkFlow rekeyRequestWorkFlow;

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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the rekeyRequestWorkFlow
	 */
	public RekeyRequestWorkFlow getRekeyRequestWorkFlow() {
		return rekeyRequestWorkFlow;
	}

	/**
	 * @param rekeyRequestWorkFlow
	 *            the rekeyRequestWorkFlow to set
	 */
	public void setRekeyRequestWorkFlow(
			RekeyRequestWorkFlow rekeyRequestWorkFlow) {
		this.rekeyRequestWorkFlow = rekeyRequestWorkFlow;
	}

}
