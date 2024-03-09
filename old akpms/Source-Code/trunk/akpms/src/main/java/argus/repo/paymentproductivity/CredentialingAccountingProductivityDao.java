/**
 *
 */
package argus.repo.paymentproductivity;

import java.util.List;
import java.util.Map;

import argus.domain.paymentproductivity.CredentialingAccountingProductivity;
import argus.exception.ArgusException;

/**
 * @author rajiv.k
 *
 */
public interface CredentialingAccountingProductivityDao {

	/**
	 * 
	 * @param id
	 * @return
	 */
	CredentialingAccountingProductivity findById(Long id) throws ArgusException;

	/**
	 * 
	 * @param name
	 * @return
	 */
	CredentialingAccountingProductivity findByName(String name)
			throws ArgusException;

	/**
	 * 
	 * @param orderClauses
	 *            key=(orderBy,sortBy,offset,limit)
	 * @param whereClauses
	 * @return
	 */

	/**
	 * @param name
	 *            It is the name of the Productivity to be searched
	 * @param id
	 *            It is the id of the Productivity to be searched
	 */
	List<CredentialingAccountingProductivity> findAll(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException;

	/**
	 * 
	 * @param Productivity
	 */
	void addCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException;

	/**
	 * 
	 * @param Productivity
	 */
	void updateCredentialingAccountingProductivity(
			CredentialingAccountingProductivity credentialingAccountingProductivity)
			throws ArgusException;

	/**
	 * 
	 * @param conditionMap
	 * @return
	 */
	int totalRecord(Map<String, String> conditionMap) throws ArgusException;
	
	/**
	 * @param name
	 *            It is the name of the Productivity to be searched
	 * @param id
	 *            It is the id of the Productivity to be searched
	 */
	List<CredentialingAccountingProductivity> findAllJPQL(
			Map<String, String> orderClauses, Map<String, String> whereClauses,
			boolean dependancies) throws ArgusException;
	
}
