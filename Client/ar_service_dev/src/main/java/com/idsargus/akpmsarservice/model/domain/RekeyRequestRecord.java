package com.idsargus.akpmsarservice.model.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.idsargus.akpmscommonservice.entity.RekeyRequestWorkFlowEntity;
import lombok.Getter;
import lombok.Setter;

//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author vishal.joshi
 *
 */
//@XStreamAlias("RekeyRequestRecord")
@Entity
@Getter
@Setter
//@EntityListeners(EntityListener.class)
@Table(name = "rekey_request_record")
public class RekeyRequestRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cpt", length = 50)
	private String cpt;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remark;

//	@XStreamOmitField
	@ManyToOne
	@JoinColumn(name = "rekey_request_id", referencedColumnName = "id", unique = false, nullable = false)
	private RekeyRequestWorkFlowEntity rekeyRequestWorkFlow;


	/**
	 * @param rekeyRequestWorkFlow
	 *            the rekeyRequestWorkFlow to set
	 */
	public void setRekeyRequestWorkFlow(
			RekeyRequestWorkFlowEntity rekeyRequestWorkFlow) {
		this.rekeyRequestWorkFlow = rekeyRequestWorkFlow;
	}

}