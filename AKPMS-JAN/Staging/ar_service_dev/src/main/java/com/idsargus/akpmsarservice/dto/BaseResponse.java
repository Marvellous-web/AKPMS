package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private String message;

    private boolean isDeleted;
}
