package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Getter
@Setter
public class QAReportRequest {

    private String fromDate;
    private  String toDate;
    private String subDepartment;
    private String createdBy;
    private String status;
    private String department;
}
