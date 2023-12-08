package com.idsargus.akpmsauthservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccessSessionResponse {

    private boolean isAccessAllowed;
    private String msg;
}
