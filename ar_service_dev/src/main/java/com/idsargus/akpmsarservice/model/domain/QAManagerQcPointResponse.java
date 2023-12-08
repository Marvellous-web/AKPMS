package com.idsargus.akpmsarservice.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QAManagerQcPointResponse {

    private String qcPoint;
    private String name;
    private String errorCount;

}
