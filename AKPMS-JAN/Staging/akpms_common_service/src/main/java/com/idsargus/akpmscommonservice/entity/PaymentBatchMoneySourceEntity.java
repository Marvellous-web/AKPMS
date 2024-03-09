package com.idsargus.akpmscommonservice.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_batch_money_source")

public class PaymentBatchMoneySourceEntity extends BaseAuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "money_source", referencedColumnName = "id", unique = false, nullable = true) })
	private MoneySourceEntity moneySource = null;

	@ManyToOne
	@JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = false)
	private PaymentBatchEntity paymentBatch;

	private Double amount = 0.0;

	

}