package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BulkChargeProdRejTicket implements Serializable {

    private List<String> chargeProRejectIds;
   // private String remarks;


    private String modifiedBy;
    private String modifiedOn;
    private String  dummyCpt;
    private String remarks;
    private  String resolution;
    private String dateOfFirstRequestToDoctorOffice;

    private String status;
    private String resolvedBy;

    private String resolvedOn;
    private String completedOn;
    private String completedById;
    private String dateOfSecondRequestToDoctorOffice;

}
