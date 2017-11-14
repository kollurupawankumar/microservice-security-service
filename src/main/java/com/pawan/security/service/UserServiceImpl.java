package com.pawan.security.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pawan.security.SecurityUtil;
import com.pawan.security.dao.UserDAO;
import com.pawan.security.model.Permission;
import com.pawan.security.model.Role;
import com.pawan.security.model.User;

@Service
public class UserServiceImpl  implements UserService{

	
	@Autowired
	private UserDAO userDao;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    
	
	
	@Override
	public User findByEmail(String email){
		return userDao.findByEmail(email);
	}
	
	@Override
	public User findByUsernameOrEmail(String username, String email){
		return userDao.findByUsernameOrEmail(username, email);
	}
	
	public User add(User user){
		
		User checkUser = findByEmail(user.getEmail());
		
		
		//setting default username, if username is empty
    	if(StringUtils.isEmpty(user.getUsername())){
    		String[] splictedEmail = user.getEmail().split("@");
    		if(splictedEmail.length >= 2) user.setUsername(splictedEmail[0]);
    	}
    		
        //encode password
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	user.setRegisteredOn(new Date());
    	user.setLastPasswordResetDate(new Date());
        
    	for(Role r : user.getRoles()){
    	
    		Role role;
			try {
				role = r;
				 //set user_permission
	            List<Permission> permissions = role.getPermissions();
	            for(Permission permission : permissions){
	            	user.getPermissions().add(permission);
	            }
	            
			} catch (Exception e) {
				return null;
			}
    		
    	}
    	
    	
        
        return userDao.add(checkUser);
    	
    	
    }
	
	@Override
    public User edit(User user){
    	
		User savedUser = SecurityUtil.getLoggedUser();
		if (savedUser != null){
			user.setId(savedUser.getId());
			user.setEmail(savedUser.getEmail());
			user.setUsername(savedUser.getUsername());
			user.setPassword(savedUser.getPassword());
			user.setRoles(savedUser.getRoles());
			user.setPermissions(savedUser.getPermissions());
			user.setRegisteredOn(savedUser.getRegisteredOn());
			user.setLastPasswordResetDate(savedUser.getLastPasswordResetDate());
			user.setLastLoggedOn(savedUser.getLastLoggedOn());
			user.setAttempts(savedUser.getAttempts());
						
			return userDao.edit(user);
		}
		return null;
    }
	
	@Override
    public Boolean changePassword(String oldPassword, String newPassword){

		User loggedUser = SecurityUtil.getLoggedUser();
		
       if(passwordEncoder.matches(oldPassword, loggedUser.getPassword())){
    	   User savedUser = userDao.findByEmail(loggedUser.getId());
    	   
			   savedUser.setPassword(passwordEncoder.encode(newPassword));
    		   savedUser.setLastPasswordResetDate(new Date());
    		   userDao.edit(savedUser);
    		   return true;
    	   
       }else
    	   return null;
    }





	@Override
	public User findUserByToken(String token){
		try{
			return userDao.findUserByToken(token);
		}catch (Exception e) {
			return null;
		}
	}


	
}
