package com.pawan.security.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pawan.security.SecurityJWTUtil;
import com.pawan.security.dao.UserDAO;
import com.pawan.security.exception.UnauthorizedException;
import com.pawan.security.model.JwtAuthenticationDto;
import com.pawan.security.model.JwtResponse;
import com.pawan.security.model.Permission;
import com.pawan.security.model.Role;
import com.pawan.security.model.User;


@RestController
@RequestMapping("/auth")
public class AuthController {
	

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SecurityJWTUtil securityJWTUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	public JwtResponse createAuthenticationToken(
			@ModelAttribute JwtAuthenticationDto jwtAuthenticationDto, Device device)
					throws Exception {

		// Checking username|email and password
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				jwtAuthenticationDto.getUsername(), jwtAuthenticationDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationDto.getUsername());
		String token = securityJWTUtil.generateToken(userDetails, device);
		
		Date expiration = securityJWTUtil.getExpirationDateFromToken(token);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		return new JwtResponse(token, dateFormat.format(expiration));

	}
	
	@RequestMapping(path="/add/default/users",method = RequestMethod.GET)
	public List<User> createDefaultUsers() throws Exception {

		List<User> result = new ArrayList<>();
		for (int i=100; i<= 109 ; i++){
			User user = new User();
			user.setEmail("pawan"+i+"@gmail.com");
			user.setAttempts(i);
			user.setFirstName("Pawan"+i);
			user.setLastLoggedOn(new Date());
			user.setLastName("Kumar");
			String password = "pawan123";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			user.setPassword(hashedPassword);
			user.setRegisteredOn(new Date());
			user.setUsername("pawan"+i+"@gmail.com");
			Permission permission = new Permission();
			permission.setCode(Permission.USER_SELLER_ADMIN);
			user.setPermissions(Collections.singletonList( permission));
			
			Role role = new Role();
			role.setCode(Role.ROLE_SELLER_ADMIN);
			user.setRoles(Collections.singletonList( role));
			result.add(user);
			userDAO.add(user);
		}
		

		return result;
		
	}

	@RequestMapping("/current")
	public UserDetails getCurrent(HttpRequest httpRequest) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String authenticatedUserName = authentication.getName();
		if(authenticatedUserName.equals("anonymousUser"))
			throw new UnauthorizedException(authenticatedUserName);
		else
			return (UserDetails)authentication.getPrincipal();
	}

}
