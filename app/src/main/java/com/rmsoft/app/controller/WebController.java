package com.rmsoft.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

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
	
	// 로그아웃후 인덱스로 이동
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		
		session.invalidate();
		
		return "index";
	}
	
	//구독하기 페이지 이동
	@GetMapping("/subscribe")
	public String subscribePage() {
		return "subscribe";
	}
}
