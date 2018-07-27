package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String LoginGet() {
		return "login";
	}
//	@RequestMapping("/test")
//	public String LoginGet2() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String a = auth.getName();
//		
//		return "index";
//	}	
//	@RequestMapping("/admin")
//	public String LoginGet3() {
//		
//		return "index";
//	}	
}
