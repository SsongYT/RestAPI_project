package com.rmsoft.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.service.MemberService;

@RestController
public class MemberController {
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("signup/id/{checkid}")
	public ResponseEntity<String> checkId(@PathVariable String checkid, HttpStatus status) {
		ResponseEntity<String> result = null;
		
		if(memberService.checkId(checkid)) {
			result = new ResponseEntity<String>("사용가능", status.OK);
		} else {
			result = new ResponseEntity<String>("사용불가능", status.OK);
		}
		
		return result;
	}
	
	@GetMapping("signup/email/{checkemail}")
	public String checkEmail(@PathVariable String checkemail) {
		return "signup";
	}
	
}
