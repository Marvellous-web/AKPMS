package argus.repo.paymentproductivity;

import java.util.List;
import java.util.Map;

import argus.domain.paymentproductivity.PaymentProdQueryToTL;
import argus.exception.ArgusException;

public interface PaymentProdQueryToTLDao
{

	/**
    *
    * @param id
    * @return
    */
	PaymentProdQueryToTL findById(Long id)  throws ArgusException;
	/**
	 *
	 * @param name
	 * @return
	 */
	PaymentProdQueryToTL findByName(String name) throws ArgusException;

    /**
     *
     * @param orderClauses key=(orderBy,sortBy,offset,limit)
     * @param whereClauses
     * @return
     */

    /**
     * @param name It is the name of the Productivity to be searched
     * @param id It is the id of the Productivity to be searched
     */
     List<PaymentProdQueryToTL> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void addPaymentProdQueryToTL(PaymentProdQueryToTL paymentProdQueryToTL)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void updatePaymentProdQueryToTL(PaymentProdQueryToTL paymentProdQueryToTL)  throws ArgusException;


    /**
     *
     * @param conditionMap
     * @return
     */
     int totalRecord(Map<String,String> conditionMap)  throws ArgusException;

     /**
      * delete single productivity
      * @param ids
      * @return
      */
      int deletePaymentProdQueryToTL(Long id)  throws ArgusException;

	/**
	 * 
	 * @param prodId
	 * @return
	 * @throws ArgusException
	 */
	PaymentProdQueryToTL findQueryToTLByProdId(long prodId)
			throws ArgusException;

}
