package com.idsargus.akpmsarservice.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.idsargus.akpmscommonservice.entity.ArProductivityEntity;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "files")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArFiles extends BaseAuditableEntity
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	public Integer id;
	private String name;

	private String type;

	@Column(name = "system_name")
	private String systemName;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;


	@Column(name="iscopy_cancel_check")
	private boolean isCopyCancelCheck;
	@Column(name="is_eob")
	private boolean isEob;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

//	 @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,
//	 CascadeType.REMOVE})
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "process_manual_id", referencedColumnName = "id")
	private ProcessManual processManual;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumns({ @JoinColumn(name = "coding_correction_workflow_id", referencedColumnName = "id") })
//	private CodingCorrectionLogWorkFlow codingCorrectionLogWorkFlow;

	@Transient
	//@Column(name=  "process_manual_id")
	private String processManualId;
	@Column(name ="coding_correction_workflow_id")
	private String codingCorrectionId;
	@Column(name ="payment_posting_workflow_id")
	private String paymentPostingWorkFlowId;

	@Column(name ="reject_log_id")
	private String rejectLogId;

	@Column(name ="refund_request_id")
	private String refundRequestId;
	@Transient
	private MultipartFile attachedFile;

	@Transient
	private String url;

	@Transient
	private String createdById;

	@Transient
	private String modifiedById;
	@Transient
	private Integer subProcessManualId;
	public ArFiles(){

	}
	public ArFiles(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public ArFiles(MultipartFile attachedFile,  Integer id , String name){
		this.attachedFile = attachedFile;
		this.id = id;
		this.name = name;

	}
//	public Integer getSubProcessManualId() {
//		return (this.processManual == null) ? null : this.processManual.getId();
//	}

//	public void setSubProcessManualId(Integer subProcessManualId) {
//		this.subProcessManualId = subProcessManualId;
//	}

	
}