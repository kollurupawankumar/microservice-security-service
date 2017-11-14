package com.pawan.security.model;

public class Permission {
	
	public static String USER = "USER";
	public static String USER_ADMIN = "USER_ADMIN";
	public static String USER_SELLER_ADMIN = "USER_SELLER_ADMIN";
	public static String USER_SELLER = "USER_SELLER";
	public static String USER_BUYER = "USER_BUYER";
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	



}
