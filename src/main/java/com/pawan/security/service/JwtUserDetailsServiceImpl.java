package com.pawan.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pawan.security.dao.JwtUser;
import com.pawan.security.model.Permission;
import com.pawan.security.model.User;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {


	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails;

		User user;

		try {
			
			
			//username may be email
			user = userService.findByUsernameOrEmail(username, username);

			if (user == null) {
				throw new Exception(username+ " not knowm");
			}

			userDetails = new JwtUser(
					user
					, getAuthorities(user)
					, true);
			
			if (userDetails.getAuthorities().isEmpty()) {
				throw new UsernameNotFoundException(username);
			}

			return userDetails;

		} catch (Exception e) {
			
			throw new UsernameNotFoundException(username);
		}

	}

	public Collection<GrantedAuthority> getAuthorities(User user) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		for(Permission permission : user.getPermissions()){
			authList.add(new SimpleGrantedAuthority("ROLE_" + permission.getCode()));
		}
		return authList;

	}

}