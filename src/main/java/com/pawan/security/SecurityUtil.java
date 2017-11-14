package com.pawan.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pawan.security.dao.JwtUser;
import com.pawan.security.model.User;

public class SecurityUtil {

	public static UserDetails getLoggedUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
        	return (UserDetails) principal;

        return null;
    }
	
	public static JwtUser getJWTUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
        	
        	UserDetails userDetails = (UserDetails) principal;
        	JwtUser jwtUser = (JwtUser) userDetails;
        	
            return jwtUser;
        }

        return null;
    }
	
	public static User getLoggedUser() {
        return getJWTUser().getDbUser();
    }
	
}