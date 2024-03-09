//package com.idsargus.akpmsarservice.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ProfileController {
//
//    @Autowired
//    CustomUserDetailsService service;
//    @GetMapping("/myprofile/{email}")
//    public UserDetails getProfile(@PathVariable String email){
//        return service.loadUserByUsername(email);
//
//    }
//}