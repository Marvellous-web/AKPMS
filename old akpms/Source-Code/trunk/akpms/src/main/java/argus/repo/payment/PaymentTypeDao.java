/**
 *
 */
package argus.repo.payment;

import java.util.List;
import java.util.Map;

import argus.domain.PaymentType;
import argus.exception.ArgusException;

/**
 * @author vishal.joshi
 *
 */
public interface PaymentTypeDao {
	/**
	 *
	 * @param id
	 * @return
	 */
	PaymentType findById(Long id) throws ArgusException;

	/**
	 *
	 * @param name
	 * @return
	 */
	PaymentType findByName(String name) throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @return
	 */
	List<PaymentType> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses) throws ArgusException;

	/**
	 *
	 * @param Payment
	 */
	void addPaymentType(PaymentType paymentType) throws ArgusException;

	/**
	 *
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	/**
	 *
	 * @param ids
	 *            (Comma separated ids)
	 */
	int deletePaymentType(List<Long> ids) throws ArgusException;

	/**
	 *
	 * @param id
	 *            (Payment id)
	 * @param status
	 *            (activate: true/inactivate: false)
	 * @return
	 * @throws Exception
	 */
	int changeStatus(long id, boolean status) throws ArgusException;

	/**
	 * to update existing object
	 *
	 * @param Payment
	 */
	void updatePaymentType(PaymentType paymentType) throws ArgusException;

	/**
	 *
	 * @param name
	 * @param type
	 * @return Payment
	 */
	// PaymentType findByNameAndType(String name, String type);
	//
	List<Object[]> getPaymentTypeStats() throws ArgusException;
}
