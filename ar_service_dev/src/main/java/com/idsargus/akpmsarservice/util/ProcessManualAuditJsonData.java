package com.idsargus.akpmsarservice.util;

public class ProcessManualAuditJsonData {

	

	private long id;
		
	private String modificationSummary;
	
	private String modifiedBy;
	
	private String modifiedOn;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getModificationSummary() {
		return modificationSummary;
	}

	public void setModificationSummary(String modificationSummary) {
		this.modificationSummary = modificationSummary;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
