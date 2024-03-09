package com.idsargus.akpmsarservice.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "department")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ArDepartmentEntity extends BaseAuditableEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled = true;

    @NotNull
    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private Boolean deleted = false;

    @OneToMany(targetEntity = ArDepartmentEntity.class, mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ArDepartmentEntity> departments = null;

    //@JsonIgnore
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = false) })
    private ArDepartmentEntity parent = null;

    @Transient
    private Integer parentId;

    @Override
    public String toString() {
        return super.getId().toString();
    }

    public Integer getParentId() {
        return (this.parent == null) ? null : this.parent.getId();
    }

}