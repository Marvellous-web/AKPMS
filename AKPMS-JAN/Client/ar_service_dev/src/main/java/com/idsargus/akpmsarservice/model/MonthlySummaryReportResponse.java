package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MonthlySummaryReportResponse {

    private String day;
    private Integer productivityCount;
    private Integer sampleCount;
    private Integer errorCount;
    private Integer errorRate;
}
