package com.pawan.security.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="User")
public class User {
	
	@Id
	private String id;
	
	private String email;
    
	private String username;

    private String password;
    
    private String firstName;

    private String lastName;
    

    private Date lastLoggedOn;

    private Date registeredOn;

    private Integer attempts;

    private List<Role> roles = new ArrayList<>();

    private List<Permission> permissions = new ArrayList<>();
    
    
    private Date lastPasswordResetDate;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getLastLoggedOn() {
		return lastLoggedOn;
	}


	public void setLastLoggedOn(Date lastLoggedOn) {
		this.lastLoggedOn = lastLoggedOn;
	}


	public Date getRegisteredOn() {
		return registeredOn;
	}


	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}


	public Integer getAttempts() {
		return attempts;
	}


	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public List<Permission> getPermissions() {
		return permissions;
	}


	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}


	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}


	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
    
    
    
    

}
