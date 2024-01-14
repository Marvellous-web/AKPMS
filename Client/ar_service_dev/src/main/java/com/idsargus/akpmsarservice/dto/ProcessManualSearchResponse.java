package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessManualSearchResponse {

    public String title;
    public String id;
    public List<ParentProcessManualResponse> perentList;
}
