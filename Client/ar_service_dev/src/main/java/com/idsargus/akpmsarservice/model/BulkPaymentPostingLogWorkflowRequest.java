package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BulkPaymentPostingLogWorkflowRequest implements Serializable {

    /*
     String status,
											@Param("offSet") String offSet,
											@Param("doctor") Integer doctor,
											@Param("insurance") Integer insurance,
											@Param("team") Integer team,
     */
    private List<Integer> pplIds;

    private String status;
    private String offSet;
    private Integer doctor;
    private Integer insurance;
    private Integer team;

}
