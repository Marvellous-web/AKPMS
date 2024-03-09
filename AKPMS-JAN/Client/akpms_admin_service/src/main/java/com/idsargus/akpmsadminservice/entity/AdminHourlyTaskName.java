package com.idsargus.akpmsadminservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@Table(name = "hourly_task")
//@Table(name = "hourly_taskname")
public class AdminHourlyTaskName extends AdminBaseAuditableEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "department_id")
    private AdminDepartmentEntity department;

    @NotNull
    @Column(name = "chargeable", columnDefinition = "BIT default 1", nullable = false)
    private Boolean chargeable;

    @NotNull
    @Column(name = "enabled", columnDefinition = "BIT default 1", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "deleted", columnDefinition = "BIT default 0", nullable = false)
    private Boolean deleted;

//    //@Getter
////@Setter
////@Entity
////@Table(name = "hourly_taskname")
////public class AdminHourlyTaskName {
////
////    @Id
////    protected String id;
////
////    private String name;
////
////    @Override
////    public String toString() {
////        return getId().toString();
////    }

}
