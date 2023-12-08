package com.idsargus.akpmsarservice.dto.request;

import com.idsargus.akpmsarservice.model.domain.Department;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessManualRequest {

    private String title;
    private String status;
    private String content;
    private String notification;
    private List<Department> departments;
}
