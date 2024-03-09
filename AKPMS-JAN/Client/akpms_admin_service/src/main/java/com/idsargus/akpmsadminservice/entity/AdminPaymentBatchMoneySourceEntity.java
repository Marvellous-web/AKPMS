package com.idsargus.akpmsadminservice.entity;


import javax.persistence.*;

@Entity
@Table(name = "payment_batch_money_source")

public class AdminPaymentBatchMoneySourceEntity extends AdminBaseAuditableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumns({ @JoinColumn(name = "money_source", referencedColumnName = "id", unique = false, nullable = true) })
	private AdminMoneySourceEntity moneySource = null;

	@ManyToOne
	@JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = false)
	private AdminPaymentBatchEntity paymentBatch;

	private Double amount = 0.0;

	

}