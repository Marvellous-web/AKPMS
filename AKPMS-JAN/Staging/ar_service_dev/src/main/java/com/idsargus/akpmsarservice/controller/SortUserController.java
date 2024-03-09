package com.idsargus.akpmsarservice.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortUserController {

    public void orderByColumnUser(@RequestParam("columnName") String columnName,
                                  @RequestParam("orderBy") String orderBy){

    }
}
