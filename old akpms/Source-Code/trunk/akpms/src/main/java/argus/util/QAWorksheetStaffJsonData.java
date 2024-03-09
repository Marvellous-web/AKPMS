package argus.util;

public class QAWorksheetStaffJsonData {

	private Long id;

	private float recordPercentage;

	private String userName;
	
	private String remarks;
	//This should be in QAWorksheet but there is no option so declaring ar status code in this class
	private String arStatusCode;
	
	public QAWorksheetStaffJsonData () {}
	
	public QAWorksheetStaffJsonData (Long id, String userName, float recordPercentage, String remarks) {
		this.id = id;
		this.userName = userName;
		this.recordPercentage = recordPercentage;
		this.remarks = remarks;
	}
	
	public float getRecordPercentage() {
		return recordPercentage;
	}

	public void setRecordPercentage(float recordPercentage) {
		this.recordPercentage = recordPercentage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getArStatusCode() {
		return arStatusCode;
	}

	public void setArStatusCode(String arStatusCode) {
		this.arStatusCode = arStatusCode;
	}
	
}
