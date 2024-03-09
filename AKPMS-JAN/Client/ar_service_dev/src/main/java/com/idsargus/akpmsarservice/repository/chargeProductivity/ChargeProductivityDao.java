package com.idsargus.akpmsarservice.repository.chargeProductivity;


import com.idsargus.akpmsarservice.exception.ArgusException;
import com.idsargus.akpmscommonservice.entity.ChargeProductivity;

import java.util.List;
import java.util.Map;

public interface ChargeProductivityDao {

	void saveProductivity(ChargeProductivity chargeProductivity)
			throws ArgusException;

	void updateProductivity(ChargeProductivity chargeProductivity)
			throws ArgusException;

	ChargeProductivity getChargeProductivityById(Long id) throws ArgusException;

	long checkProductivityWithRejectWorkFlowByTicketNumber(long id)
			throws ArgusException;

	/**
	 *
	 * @param conditionMap
	 * @return
	 * @throws ArgusException
	 */
	int totalRecord(Map<String, String> conditionMap)
			throws ArgusException;

	/**
	 *
	 * @param orderClauses
	 * @param whereClauses
	 * @param dependency
	 * @return
	 * @throws ArgusException
	 */
	List<ChargeProductivity> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependency)
			throws ArgusException;

	int totalOnHold() throws ArgusException;

	int totalOnHoldRecord() throws ArgusException;
	
}
