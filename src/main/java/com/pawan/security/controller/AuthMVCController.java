package com.pawan.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pawan.security.model.JwtAuthenticationDto;


@Controller
@RequestMapping("/auth")
public class AuthMVCController {
	@RequestMapping(value = "/new", method = RequestMethod.GET) 
	public String displayLogin(Model model) { 
	    model.addAttribute("jwtAuthenticationDto", new JwtAuthenticationDto()); 
	    return "sample"; 
	}
}
