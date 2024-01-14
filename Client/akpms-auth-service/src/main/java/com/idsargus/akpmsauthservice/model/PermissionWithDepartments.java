package com.idsargus.akpmsauthservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PermissionWithDepartments {


    String permissionId;
    String departmentId;
//    String description;
//    String name;
    boolean read;
    boolean write;
    boolean delete;
    boolean update;
}
