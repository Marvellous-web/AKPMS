package com.idsargus.akpmsarservice.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "user_email_template")
@Setter
@Getter
public class UserEmailTemplateEntity {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name ="email_template_id")
    private Integer emailTemplateId;
    @Column(name= "user_id")
    private Integer userId;
}
