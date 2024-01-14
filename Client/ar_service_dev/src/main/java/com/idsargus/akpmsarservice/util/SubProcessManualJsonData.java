package com.idsargus.akpmsarservice.util;

import java.util.Date;


//new
public class SubProcessManualJsonData {


	private Integer id;

	private String title;

	private String content;

	private String modificationSummary;

	private boolean notification = true;

	private boolean status = true;

	private Integer parentId;

	private Integer createdBy;

	private Date createdOn;
	
	private double position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModificationSummary() {
		return modificationSummary;
	}

	public void setModificationSummary(String modificationSummary) {
		this.modificationSummary = modificationSummary;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double d) {
		this.position = d;
	}	

}
