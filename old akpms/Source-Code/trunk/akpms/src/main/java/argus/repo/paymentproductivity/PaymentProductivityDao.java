package argus.repo.paymentproductivity;

import java.util.List;
import java.util.Map;

import argus.domain.paymentproductivity.PaymentProductivity;
import argus.exception.ArgusException;

public interface PaymentProductivityDao {

	/**
    *
    * @param id
    * @return
    */
	PaymentProductivity findById(Long id)  throws ArgusException;
	/**
	 *
	 * @param name
	 * @return
	 */
	PaymentProductivity findByName(String name) throws ArgusException;

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
     List<PaymentProductivity> findAll(Map<String,String> orderClauses, Map<String,String> whereClauses ,boolean dependancies)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void addPaymentProductivity(PaymentProductivity paymentProductivity)  throws ArgusException;

    /**
     *
     * @param Productivity
     */
     void updatePaymentProductivity(PaymentProductivity paymentProductivity)  throws ArgusException;


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
      int deletePaymentProductivity(Long id)  throws ArgusException;

      /**
	 *
	 * @return the record of ERA and NON ERA
	 *
	 */
      List<Object[]> getAllProductivityType()  throws ArgusException;

	/**
	 *
	 * @param batchId
	 * @return
	 */
	PaymentProductivity findByTicketNo(Long ticketNo) throws ArgusException;


	/**
	 *
	 * @param conditionMap
	 * @return
	 */
	// int totalRecordForNonEraReport(Map<String, String> conditionMap)
	// throws ArgusException;

	/**
	 *
	 * @param conditionMap
	 * @return
	 */
	// int totalRecordForCapReport(Map<String, String> conditionMap)
	// throws ArgusException;

	/**
	 *
	 * @param conditionMap
	 * @return
	 */
	// int totalRecordForEraReport(Map<String, String> conditionMap)
	// throws ArgusException;

	/**
	 * @param name
	 *            It is the name of the Productivity to be searched
	 * @param id
	 *            It is the id of the Productivity to be searched
	 */
	List<PaymentProductivity> findAllForReport(
			Map<String, String> orderClauses, Map<String, String> whereClauses
			) throws ArgusException;

	/**
	 *
	 * @return
	 * @throws ArgusException
	 */
	List<Object[]> getAllProductivityByWorkFlow() throws ArgusException;

	/**
	 *
	 * @return
	 * @throws ArgusException
	 */
	int getTotalOffsetRecord() throws ArgusException;

	int getTotalProductivityOffsetRecord(Long batchId)
			throws ArgusException;

	public List<PaymentProductivity> findAllForPdfReport(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws ArgusException;
}
