package argus.repo.paymentbatch;

import java.util.List;
import java.util.Map;

import argus.domain.PaymentBatch;
import argus.exception.ArgusException;

public interface PaymentBatchDao {

	PaymentBatch findById(Long id, boolean dependency) throws ArgusException;


	Long addPaymentBatch(PaymentBatch paymentBatch)
			throws ArgusException;


	void updatePaymentBatch(PaymentBatch paymentBatch)  throws ArgusException;

	List<PaymentBatch> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException;

	int totalRecord(Map<String, String> whereClauses);

	public List<PaymentBatch> findAllForReport(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException;
	
}
