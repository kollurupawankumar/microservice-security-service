package com.pawan.security.service;

import com.pawan.security.model.User;

public interface UserService {
	
	User findByEmail(String email);
	
	User findByUsernameOrEmail(String username, String email);
    
	User edit(User user);
	
	User add(User user);

	User findUserByToken(String token);

	Boolean changePassword(String oldPassword, String newPassword);
	
}