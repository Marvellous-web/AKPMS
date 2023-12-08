package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedResponse<T> {

    private List<T> processmanual;
    private PaginationInfo paginationInfo;

    public PaginatedResponse(List<T> processmanual, PaginationInfo paginationInfo) {
        this.processmanual = processmanual;
        this.paginationInfo = paginationInfo;
    }
}
