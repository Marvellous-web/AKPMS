package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProcessManualEmailSubscription {

    private String email;
    private String modificationSummary;
    private String title;

    private String modifiedOn;

    private String modifiedBy;

    private  String action;

}
