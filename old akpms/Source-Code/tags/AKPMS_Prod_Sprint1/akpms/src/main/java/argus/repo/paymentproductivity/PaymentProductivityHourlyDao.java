package argus.repo.paymentproductivity;

import java.util.List;
import java.util.Map;

import argus.domain.paymentproductivity.PaymentProductivityHourly;
import argus.exception.ArgusException;

public interface PaymentProductivityHourlyDao {

	PaymentProductivityHourly findById(Long id) throws ArgusException;

	void addPaymentProductivityHourly(
			PaymentProductivityHourly paymentProductivityHourly)
			throws ArgusException;

	void updatePaymentProductivityHourly(
			PaymentProductivityHourly paymentProductivityHourly)
			throws ArgusException;

	int totalRecord(Map<String, String> whereClauses);

	List<PaymentProductivityHourly> findAll(
			Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws Exception;

}
