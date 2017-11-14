package com.pawan.security.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pawan.security.model.Permission;
import com.pawan.security.model.User;

public interface UserDAO {
	
	User findByEmail(String email) throws DataAccessException;

	List<Permission> findUserPermissions()throws DataAccessException;
	
	User findUserByToken(String token)throws DataAccessException;

	User findByUsernameOrEmail(String username, String email) throws DataAccessException;
	
	User add (User user) throws DataAccessException;
	
	User edit(User user) throws DataAccessException;

}
