package com.idsargus.akpmsadminservice.config;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;



import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(
      Authentication auth, Object targetDomainObject, Object permission) {
    	log.debug("**********************************************************************************************************************");
        if ((auth == null) ||  !(permission instanceof String)){
            return false;
        }
       
        
        return hasPrivilege(auth, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
      Authentication auth, Serializable targetId, String targetType, Object permission) {
    	log.debug("**********************************************************************************************************************");
        if ((auth == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, 
          permission.toString().toUpperCase());
    }
    
    private boolean hasPrivilege(Authentication auth, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (
            		
              grantedAuth.getAuthority().contains(permission)) {
            	return true;
            }
        }
        return false;
    }
}