package com.idsargus.akpmsarservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserMenu {


    private Integer id;
    private String linkName;

    private boolean hasSubMenu;
    List<UserMenu> subMenu;
    private String linkUrl;
    private String linkImage;

    private List<Map<String,Object>> permissionList;
}