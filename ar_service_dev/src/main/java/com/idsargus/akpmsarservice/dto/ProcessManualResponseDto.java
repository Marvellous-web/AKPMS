package com.idsargus.akpmsarservice.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Data;

@Data
public class ProcessManualResponseDto {
    private Integer id;
    private String title;
    private Double position;
    private String content;
    private String modificationSummary;
    private boolean notification = true;
    private boolean status = true;
    private Date createdOn;

    private Date modifiedOn;

    private String ModifiedBy;

    private String createdBy;
    private List<ProcessManualResponseDto> childProcessList;
    private List<Integer> departmentList;

    public ProcessManualResponseDto() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getPosition() {
        return this.position;
    }

    public String getContent() {
        return this.content;
    }

    public String getModificationSummary() {
        return this.modificationSummary;
    }

    public boolean isNotification() {
        return this.notification;
    }

    public boolean isStatus() {
        return this.status;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public List<ProcessManualResponseDto> getChildProcessList() {
        return this.childProcessList;
    }

    public List<Integer> getDepartmentList() {
        return this.departmentList;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setPosition(final Double position) {
        this.position = position;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setModificationSummary(final String modificationSummary) {
        this.modificationSummary = modificationSummary;
    }

    public void setNotification(final boolean notification) {
        this.notification = notification;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setChildProcessList(final List<ProcessManualResponseDto> childProcessList) {
        this.childProcessList = childProcessList;
    }

    public void setDepartmentList(final List<Integer> departmentList) {
        this.departmentList = departmentList;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ProcessManualResponseDto)) {
            return false;
        } else {
            ProcessManualResponseDto other = (ProcessManualResponseDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isNotification() != other.isNotification()) {
                return false;
            } else if (this.isStatus() != other.isStatus()) {
                return false;
            } else {
                label112: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label112;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label112;
                    }

                    return false;
                }

                label105: {
                    Object this$position = this.getPosition();
                    Object other$position = other.getPosition();
                    if (this$position == null) {
                        if (other$position == null) {
                            break label105;
                        }
                    } else if (this$position.equals(other$position)) {
                        break label105;
                    }

                    return false;
                }

                Object this$title = this.getTitle();
                Object other$title = other.getTitle();
                if (this$title == null) {
                    if (other$title != null) {
                        return false;
                    }
                } else if (!this$title.equals(other$title)) {
                    return false;
                }

                label91: {
                    Object this$content = this.getContent();
                    Object other$content = other.getContent();
                    if (this$content == null) {
                        if (other$content == null) {
                            break label91;
                        }
                    } else if (this$content.equals(other$content)) {
                        break label91;
                    }

                    return false;
                }

                Object this$modificationSummary = this.getModificationSummary();
                Object other$modificationSummary = other.getModificationSummary();
                if (this$modificationSummary == null) {
                    if (other$modificationSummary != null) {
                        return false;
                    }
                } else if (!this$modificationSummary.equals(other$modificationSummary)) {
                    return false;
                }

                label77: {
                    Object this$createdOn = this.getCreatedOn();
                    Object other$createdOn = other.getCreatedOn();
                    if (this$createdOn == null) {
                        if (other$createdOn == null) {
                            break label77;
                        }
                    } else if (this$createdOn.equals(other$createdOn)) {
                        break label77;
                    }

                    return false;
                }

                label70: {
                    Object this$childProcessList = this.getChildProcessList();
                    Object other$childProcessList = other.getChildProcessList();
                    if (this$childProcessList == null) {
                        if (other$childProcessList == null) {
                            break label70;
                        }
                    } else if (this$childProcessList.equals(other$childProcessList)) {
                        break label70;
                    }

                    return false;
                }

                Object this$departmentList = this.getDepartmentList();
                Object other$departmentList = other.getDepartmentList();
                if (this$departmentList == null) {
                    if (other$departmentList != null) {
                        return false;
                    }
                } else if (!this$departmentList.equals(other$departmentList)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProcessManualResponseDto;
    }

    public int hashCode() {
       // int PRIME = true;
        int result = 1;
        result = result * 59 + (this.isNotification() ? 79 : 97);
        result = result * 59 + (this.isStatus() ? 79 : 97);
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $position = this.getPosition();
        result = result * 59 + ($position == null ? 43 : $position.hashCode());
        Object $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $modificationSummary = this.getModificationSummary();
        result = result * 59 + ($modificationSummary == null ? 43 : $modificationSummary.hashCode());
        Object $createdOn = this.getCreatedOn();
        result = result * 59 + ($createdOn == null ? 43 : $createdOn.hashCode());
        Object $childProcessList = this.getChildProcessList();
        result = result * 59 + ($childProcessList == null ? 43 : $childProcessList.hashCode());
        Object $departmentList = this.getDepartmentList();
        result = result * 59 + ($departmentList == null ? 43 : $departmentList.hashCode());
        return result;
    }

    public String toString() {
        return "ProcessManualResponseDto(id=" + this.getId() + ", title=" + this.getTitle() + ", position=" + this.getPosition() + ", content=" + this.getContent() + ", modificationSummary=" + this.getModificationSummary() + ", notification=" + this.isNotification() + ", status=" + this.isStatus() + ", createdOn=" + this.getCreatedOn() + ", childProcessList=" + this.getChildProcessList() + ", departmentList=" + this.getDepartmentList() + ")";
    }
}
