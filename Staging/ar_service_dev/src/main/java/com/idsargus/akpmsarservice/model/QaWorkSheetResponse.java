package com.idsargus.akpmsarservice.model;

import com.idsargus.akpmsarservice.model.domain.Department;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QaWorkSheetResponse {

    private Department subDepartment;

    /**
     * QAWorksheet status 0 saved ,1 run, 2 compleate
     */
    private Integer status = 0;

    private Float generalPercentage;

    private Float accountPercentage;

    private Integer billingMonth;

    private Integer billingYear;


    private Date postingDateFrom;

    private Date postingDateTo;

    private Date scanDateFrom;

    private Date scanDateTo;

    private String name;


    @NotNull
    private Boolean deleted = false;


    private String arStatusCode;


    private String departmentName;


}