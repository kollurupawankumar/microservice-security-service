package com.pawan.security.model;

import java.util.ArrayList;
import java.util.List;

public class Role {
	
	public static final String ROLE_ADMIN = "role.admin";
    public static final String ROLE_SELLER_ADMIN = "role.seller.admin";
    public static final String ROLE_SELLER = "role.seller";
    public static final String ROLE_BUYER = "role.buyer";

    
    private String code;

    private List<Permission> permissions = new ArrayList<>();

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
    
    

}
