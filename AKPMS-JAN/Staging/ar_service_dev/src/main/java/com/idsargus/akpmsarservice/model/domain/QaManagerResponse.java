package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmsarservice.dto.QaManagerUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QaManagerResponse {

    private String department;
    private String dateFrom;
    private String dateTo;
    private String team;

    private List<QaManagerUser> qaManagerUserList;
}
