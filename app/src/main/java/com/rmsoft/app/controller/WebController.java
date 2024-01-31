package com.rmsoft.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	// 인덱스 페이지 이동
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}
	
	//회원가입 페이지 이동
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
	//로그인 페이지 이동
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
}
