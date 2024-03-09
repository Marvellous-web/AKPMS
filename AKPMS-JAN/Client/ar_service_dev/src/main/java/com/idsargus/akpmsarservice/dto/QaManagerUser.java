package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QaManagerUser {
    
    /*
    
    Name  0
   QaWorkSheetId
   userID
   userName
   totalTransaction
   qaAccount 

     */

    private String name;
    private String qaWorkSheetId;
    private String userId;
    private String userName;
    private String totalTransaction;
    private String qaAccount;

    private String error;

    private String errorPercentage;


    /*
         For table info

         DEPARTMENT: ACCOUNTING DEPARTMENT
FROM THE DATE: 04/01/2015 TO 04/04/2023
TEAM: ALL
     */




}
