package com.idsargus.akpmsauthservice.controller;

import com.idsargus.akpmsauthservice.config.CustomSessionRegistry;
import com.idsargus.akpmsauthservice.model.UserAccessSessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth")
public class RestrictUserSessionController {


    @Autowired
    private CustomSessionRegistry customSessionRegistry;

    @Autowired
    private HttpServletRequest request;
    @GetMapping("/restricted")
    public ResponseEntity<UserAccessSessionResponse> restrictsEndpoints(@RequestParam("username") String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication!= null && authentication.isAuthenticated();
        UserAccessSessionResponse userAccessSessionResponse = new UserAccessSessionResponse();
//        String authToken = request.getHeaders("Authorization").nextElement().toString();
        if(!isLoggedIn){
            userAccessSessionResponse.setAccessAllowed(false);
            userAccessSessionResponse.setMsg("Access denied. Active session exists in another tab ");
         return ResponseEntity.status(HttpStatus.FORBIDDEN)
                 .body(userAccessSessionResponse);
        } else{
            userAccessSessionResponse.setAccessAllowed(true);
            userAccessSessionResponse.setMsg("Access Allowed.");
            return ResponseEntity.ok(userAccessSessionResponse);
        }
    }

//    private String extractUsernameFromRequest(HttpServletRequest request){
//        String authorizedRequest  = request.getHeader("Authorization");
//        String token  = authorizedRequest.replace("Bearer", "");
//
//    }
}
