package com.idsargus.akpmsarservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {
    private String base64;
    private String name;
    private String url;
    private String contentType;
}
