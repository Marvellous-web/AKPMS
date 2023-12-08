package com.idsargus.akpmsarservice.model;

import com.idsargus.akpmsarservice.model.domain.Department;
import com.idsargus.akpmsarservice.model.domain.QcPoint;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QCPointChildrenOnlyResponse {
    private String name;
    private QcPoint parent = null;;

    private Department department;

    private boolean status = true;
    private boolean deleted = false;

    private String description;
}
