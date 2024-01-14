package com.idsargus.akpmsarservice.dto;

public class DepartmentListResponseDto {
    private Integer Id;
    private String name;
    private long parentId;

    public DepartmentListResponseDto() {
    }

    public Integer getId() {
        return this.Id;
    }

    public String getName() {
        return this.name;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setId(final Integer Id) {
        this.Id = Id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParentId(final long parentId) {
        this.parentId = parentId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DepartmentListResponseDto)) {
            return false;
        } else {
            DepartmentListResponseDto other = (DepartmentListResponseDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getParentId() != other.getParentId()) {
                return false;
            } else {
                Object this$Id = this.getId();
                Object other$Id = other.getId();
                if (this$Id == null) {
                    if (other$Id != null) {
                        return false;
                    }
                } else if (!this$Id.equals(other$Id)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DepartmentListResponseDto;
    }

    public int hashCode() {
        //int PRIME = true;
        int result = 1;
        long $parentId = this.getParentId();
        result = result * 59 + (int)($parentId >>> 32 ^ $parentId);
        Object $Id = this.getId();
        result = result * 59 + ($Id == null ? 43 : $Id.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "DepartmentListResponseDto(Id=" + this.getId() + ", name=" + this.getName() + ", parentId=" + this.getParentId() + ")";
    }
}

