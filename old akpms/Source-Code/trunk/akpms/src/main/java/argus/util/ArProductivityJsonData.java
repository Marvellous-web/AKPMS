package argus.util;


public class ArProductivityJsonData {

	private Long id;

	private String patientAccNo;

	private String patientName;

	private String dos;

	private String cpt;

	private String insurance;

	private String doctor;

	private String dataBas;

	private String team;

	private Float balanceAmt;

	private String source = "";

	private String statusCode = "";

	private int workflowId = 0;

	private String tlRemark;

	private String remark;

	private String workFlowName;

	private String workFlows;

	private String createdBy;

	private String createdOn;

	private String followUpDate;
	
	private String productivityType;

	public String getProductivityType() {
		return productivityType;
	}

	public void setProductivityType(String productivityType) {
		this.productivityType = productivityType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientAccNo() {
		return patientAccNo;
	}

	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDataBas() {
		return dataBas;
	}

	public void setDataBas(String dataBas) {
		this.dataBas = dataBas;
	}

	public Float getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public String getTlRemark() {
		return tlRemark;
	}

	public void setTlRemark(String tlRemark) {
		this.tlRemark = tlRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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
	 * @return the workflowId
	 */
	public int getWorkflowId() {
		return workflowId;
	}

	/**
	 * @param workflowId
	 *            the workflowId to set
	 */
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}

	/**
	 * @return the workFlows
	 */
	public String getWorkFlows() {
		return workFlows;
	}

	/**
	 * @param workFlows
	 *            the workFlows to set
	 */
	public void setWorkFlows(String workFlows) {
		this.workFlows = workFlows;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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

	/**
	 * @return the followUpDate
	 */
	public String getFollowUpDate() {
		return followUpDate;
	}

	/**
	 * @param followUpDate
	 *            the followUpDate to set
	 */
	public void setFollowUpDate(String followUpDate) {
		this.followUpDate = followUpDate;
	}

}
