package argus.util;

import java.util.Date;


public class SubProcessManualJsonData {

	private Long id;

	private String title;

	private String content;

	private String modificationSummary;

	private boolean notification = true;

	private boolean status = true;

	private Long parentId;

	private Long createdBy;

	private Date createdOn;
	
	private double position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
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
