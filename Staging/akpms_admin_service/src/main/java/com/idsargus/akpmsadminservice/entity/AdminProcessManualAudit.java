package com.idsargus.akpmsadminservice.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

//new

@Entity
@Table(name = "process_manual_audit")
public class AdminProcessManualAudit {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column (name = "process_manual_id")
		private Long processManualId;
		
		private String title;

		@Column (name = "parent_id")
		private Long parentId;
		
		@Column(columnDefinition = "TEXT")
		private String content;

		@Column(name = "modification_summary", columnDefinition = "TEXT")
		private String modificationSummary;

		@NotNull
		@Column(name = "notification", columnDefinition = "TINYINT(1) DEFAULT '1'")
		private boolean notification = true;

		@NotNull
		@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
		private boolean status = true;

		@NotNull
		@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
		private boolean deleted = false;
		
		private String action;
		
		@ManyToOne(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
		@JoinColumn(name="modified_by", referencedColumnName="id", unique = false)
		private User modifiedBy;
		
		@Column(name= "modified_on")
		private Date modifiedOn;

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

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
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

		public boolean isDeleted() {
			return deleted;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		public Long getProcessManualId() {
			return processManualId;
		}

		public void setProcessManualId(Long processManualId) {
			this.processManualId = processManualId;
		}

		public Long getParentId() {
			return parentId;
		}

		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public User getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(User modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public Date getModifiedOn() {
			return modifiedOn;
		}

		public void setModifiedOn(Date modifiedOn) {
			this.modifiedOn = modifiedOn;
		}

}
