/**
 *
 */
package argus.util;

/**
 * @author rajiv.k
 *
 */
public class RefundRequestWorkFlowJsonData {
	private Long id;

	private String patientName;

	private String patientAccNo;

	private String team;

	private Double totalAmount;

	private String dos;

	private String reason;

	private String remark;

	private String status;

	private String responsibleParty;

	private Long arProdId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName
	 *            the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the patientAccNo
	 */
	public String getPatientAccNo() {
		return patientAccNo;
	}

	/**
	 * @param patientAccNo
	 *            the patientAccNo to set
	 */
	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the dos
	 */
	public String getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(String dos) {
		this.dos = dos;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getResponsibleParty() {
		return responsibleParty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param responsibleParty
	 *            the responsibleParty to set
	 */
	public void setResponsibleParty(String responsibleParty) {
		this.responsibleParty = responsibleParty;
	}

	public Long getArProdId() {
		return arProdId;
	}

	public void setArProdId(Long arProdId) {
		this.arProdId = arProdId;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

}
