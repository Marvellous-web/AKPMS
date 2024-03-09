package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

    private int number;
    private int totalElements;
    private int totalPages;
    private int size;

    public PaginationInfo(int number, int totalElements, int totalPages, int size) {
        this.number = number;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
    }
}
