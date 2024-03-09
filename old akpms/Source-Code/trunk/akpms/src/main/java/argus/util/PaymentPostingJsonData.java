package argus.util;


public class PaymentPostingJsonData {

	private Long id;

	private String patientName;

	private String patientAccNo;

	private String batchNo;

	private String seqNo;

	private String provider;

	private String insurance;

	private String team;

	private Long arProdId;

	private String cpt;

	private Double billedAmount;

	private Double primaryAmount;

	private Double secondaryAmount;

	private Double contractualAdj;

	private Double bulkPaymentAmount;

	private Double patientResponse;

	private String checkIssueDate;

	private String checkNo;

	private String checkCashedDate;

	private String addressCheckSend;

	private Long copyCancelCheckAttId;

	private String copyCancelCheckAttName;

	private Long eobAttId;

	private String eobAttName;

	private String status;
	
	private String modifiedOn;
	
	private String modifiedBy;

	private String dos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientAccNo() {
		return patientAccNo;
	}

	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Long getArProdId() {
		return arProdId;
	}

	public void setArProdId(Long arProdId) {
		this.arProdId = arProdId;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public Double getPrimaryAmount() {
		return primaryAmount;
	}

	public void setPrimaryAmount(Double primaryAmount) {
		this.primaryAmount = primaryAmount;
	}

	public Double getSecondaryAmount() {
		return secondaryAmount;
	}

	public void setSecondaryAmount(Double secondaryAmount) {
		this.secondaryAmount = secondaryAmount;
	}

	public Double getContractualAdj() {
		return contractualAdj;
	}

	public void setContractualAdj(Double contractualAdj) {
		this.contractualAdj = contractualAdj;
	}

	public Double getBulkPaymentAmount() {
		return bulkPaymentAmount;
	}

	public void setBulkPaymentAmount(Double bulkPaymentAmount) {
		this.bulkPaymentAmount = bulkPaymentAmount;
	}


	/**
	 * @return the patientResponse
	 */
	public Double getPatientResponse() {
		return patientResponse;
	}

	/**
	 * @param patientResponse the patientResponse to set
	 */
	public void setPatientResponse(Double patientResponse) {
		this.patientResponse = patientResponse;
	}

	public String getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(String checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}


	public String getCheckCashedDate() {
		return checkCashedDate;
	}

	public void setCheckCashedDate(String checkCashedDate) {
		this.checkCashedDate = checkCashedDate;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}


	public String getAddressCheckSend() {
		return addressCheckSend;
	}

	public void setAddressCheckSend(String addressCheckSend) {
		this.addressCheckSend = addressCheckSend;
	}


	public String getCopyCancelCheckAttName() {
		return copyCancelCheckAttName;
	}

	public void setCopyCancelCheckAttName(String copyCancelCheckAttName) {
		this.copyCancelCheckAttName = copyCancelCheckAttName;
	}


	public Long getCopyCancelCheckAttId() {
		return copyCancelCheckAttId;
	}

	public void setCopyCancelCheckAttId(Long copyCancelCheckAttId) {
		this.copyCancelCheckAttId = copyCancelCheckAttId;
	}

	public Long getEobAttId() {
		return eobAttId;
	}

	public void setEobAttId(Long eobAttId) {
		this.eobAttId = eobAttId;
	}

	public String getEobAttName() {
		return eobAttName;
	}

	public void setEobAttName(String eobAttName) {
		this.eobAttName = eobAttName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the insurance
	 */
	public String getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
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
