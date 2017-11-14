package com.pawan.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecurityAuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	SecurityJWTUtil securityJWTUtil;
	
	private String tokenHeader="Authorization";
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {
		String authToken = request.getHeader(this.tokenHeader);
		System.out.println("The auth token is "+authToken);
        String username = securityJWTUtil.getUsernameFromToken(authToken);

        logger.info("checking authentication user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (securityJWTUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
		
	}

}
