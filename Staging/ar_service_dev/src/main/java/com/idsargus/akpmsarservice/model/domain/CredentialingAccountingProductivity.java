package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "credentialing_accounting_productivity")
public class CredentialingAccountingProductivity extends BaseAuditableEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "date_recd")
    private Date dateRecd;

    @Column(name = "credentialing_task")
    private String credentialingTask;

    @Column(name = "task_completed")
    private Date taskCompleted;

    @Column(name = "time")
    private Integer time;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

   }