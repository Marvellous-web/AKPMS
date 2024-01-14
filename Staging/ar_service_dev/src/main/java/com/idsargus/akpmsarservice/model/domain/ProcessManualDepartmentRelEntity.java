package com.idsargus.akpmsarservice.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "process_manual_department_rel")
public class ProcessManualDepartmentRelEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    protected Integer id;
    @Column(name ="process_manual_id")
    private String processManualId;
    @Column(name = "department_id")
    private String departmentId;
}
