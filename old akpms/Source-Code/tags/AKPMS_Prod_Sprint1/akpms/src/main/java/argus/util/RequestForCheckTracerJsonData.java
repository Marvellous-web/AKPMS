package argus.util;


public class RequestForCheckTracerJsonData {

	private Long id;

	private String patientName;

	private String patientAccNo;

	private String checkIssueDate;

	private String cashedDate;

	private String checkNo;

	private Float checkAmount;

	private Long attachmentId;

	private String attachmentName;

	private Long arProdId;

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

	public String getCheckIssueDate() {
		return checkIssueDate;
	}

	public void setCheckIssueDate(String checkIssueDate) {
		this.checkIssueDate = checkIssueDate;
	}

	public String getCashedDate() {
		return cashedDate;
	}

	public void setCashedDate(String cashedDate) {
		this.cashedDate = cashedDate;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public Float getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Float checkAmount) {
		this.checkAmount = checkAmount;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Long getArProdId() {
		return arProdId;
	}

	public void setArProdId(Long arProdId) {
		this.arProdId = arProdId;
	}

}
