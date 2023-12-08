package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import com.idsargus.akpmscommonservice.entity.BaseIdEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "qc_point")
public class QcPoint extends BaseIdEntity {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1;

    // @Pattern(regexp = "[A-Za-z ]*", message =
    // "must contain only letters and spaces")
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

//    @OneToMany(targetEntity = QcPoint.class, mappedBy = "parent", fetch = FetchType.LAZY)
//    private List<QcPoint> qcPoints = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
    private QcPoint parent = null;

    // @ManyToOne(cascade={CascadeType.REFRESH, CascadeType.DETACH},
    // fetch=FetchType.LAZY, targetEntity = Department.class)
    // @JoinColumn(name = "department_id", referencedColumnName = "id", unique =
    // false, nullable = false)
    // private Department department;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
            CascadeType.REFRESH })
    @JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
    private Department department;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(
            name = "created_by",
            referencedColumnName = "id",
            insertable = true,
            updatable = true
    )})
    private UserEntity createdBy;

    @OneToOne(fetch = FetchType.EAGER
            , cascade = { CascadeType.DETACH,
            CascadeType.REFRESH })
    @JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
    private Department subDepartment;

    @NotNull
    @Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
    private boolean status = true;

    @NotNull
    @Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean deleted = false;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Transient
    private long parentId;

    @Transient
    private long childCount;




}

