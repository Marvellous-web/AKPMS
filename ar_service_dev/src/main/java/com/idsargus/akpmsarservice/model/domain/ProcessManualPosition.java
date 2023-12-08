package com.idsargus.akpmsarservice.model.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="process_manual_position")
public class ProcessManualPosition {

	@Column(name="parent_id")
	private Integer parentId;
	//private Long parentId;

	@Id
	@Column(name="section_id")
	private Integer sectionId;
	//private Long sectionId;


	private Integer position;
	//private Long position;
	

	public Integer getParentId() {
		return parentId;
	}

//	public Long getParentId() {
//		return parentId;
//	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
//	public void setParentId(Long parentId) {
//		this.parentId = parentId;

	public Integer getSectionId() {
		return sectionId;
	}


//	public Long getSectionId() {
//		return sectionId;
//	}
	
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
//
//	public void setSectionId(Long sectionId) {
//		this.sectionId = sectionId;
//	}

	public Integer getPosition() {
		return position;
	}

//	public Long getPosition() {
//		return position;
//	}
	public void setPosition(Integer position) {
		this.position = position;
	}
//	public void setPosition(Long position) {
//		this.position = position;
//	}
}