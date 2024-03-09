package argus.util;

public class QAWorksheetDoctorJsonData {

	private Long id;

	private float recordPercentage;

	private String doctorName;

	private String remarks;
	// This should be in QAWorksheet but there is no option so declaring ar
	// status code in this class
	// private String arStatusCode;

	public QAWorksheetDoctorJsonData() {
	}

	public QAWorksheetDoctorJsonData(Long id, String userName,
			float recordPercentage, String remarks) {
		this.id = id;
		this.doctorName = userName;
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
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName
	 *            the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/*
	 * public String getArStatusCode() { return arStatusCode; }
	 * 
	 * public void setArStatusCode(String arStatusCode) { this.arStatusCode =
	 * arStatusCode; }
	 */

}
