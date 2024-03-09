package argus.repo.productivity.workflow.paymentposting;

import java.util.List;
import java.util.Map;

import argus.domain.Files;
import argus.domain.PaymentPostingWorkFlow;
import argus.exception.ArgusException;

public interface PaymentPostingWorkflowDao {

	PaymentPostingWorkFlow findById(Long id) throws ArgusException;

	/**
	 *
	 * @param codingCorrectionLogWorkFlow
	 * @throws ArgusException
	 */
	void addPaymentPostingWorkFlow(PaymentPostingWorkFlow paymentPostingWorkFlow)
			throws ArgusException;

	/**
	 * 
	 * @param codingCorrectionLogWorkFlow
	 * @throws ArgusException
	 */
	void updatePaymentPostingWorkFlow(
			PaymentPostingWorkFlow paymentPostingWorkFlow)
			throws ArgusException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws ArgusException
	 */
	List<PaymentPostingWorkFlow> findAllByProductivityId(String id)
			throws ArgusException;

	PaymentPostingWorkFlow findByProductivityId(Long id) throws ArgusException;

	int totalRecord(Map<String, String> conditionMap) throws ArgusException;

	List<PaymentPostingWorkFlow> findAll(Map<String, String> orderClauses,
			Map<String, String> whereClauses, boolean dependancies)
			throws Exception;

	void saveAttachement(Files file) throws ArgusException;

	void updateAttachement(Files file) throws ArgusException;

	void updateStatus(String ids, String ticketNumber, String Status);

	Object countOffset() throws ArgusException;
	
	Object countOffsetPending() throws ArgusException;
	
	Object countOffsetApproved() throws ArgusException;
	
	Object countOffsetCompleted() throws ArgusException;

	List<Object[]> getStatusCount() throws ArgusException;
}
