package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkPaymentProductivityOffsetRequest {

    public List<Integer> ids;
    public String status;
    public String remark;
    public String offsetRemark;


}
