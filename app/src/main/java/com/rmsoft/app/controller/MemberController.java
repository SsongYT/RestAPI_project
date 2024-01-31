package com.rmsoft.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.dto.LoginDTO;
import com.rmsoft.app.dto.MemberDTO;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("signup/id/{checkId}")
	public ResponseEntity<ResponseData> hasCheckId(@PathVariable("checkId") String checkId) {
		ResponseData responseData = new ResponseData();
		HttpStatus httpStatus = null;
		
		try {
			if(memberService.checkId(checkId)) {
				responseData.setCode(ResponseDataEnum.check_id_true.getCode());
				responseData.setMessages(ResponseDataEnum.check_id_true.getMessages());
				responseData.setSolution(ResponseDataEnum.check_id_true.getSolution());
			} else {
				responseData.setCode(ResponseDataEnum.check_id_false.getCode());
				responseData.setMessages(ResponseDataEnum.check_id_false.getMessages());
				responseData.setSolution(ResponseDataEnum.check_id_false.getSolution());
			}
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
			
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
//	@GetMapping("signup/email/{checkemail}")
//	public ResponseEntity<ResponseData> hasCheckEmail(@PathVariable("checkemail") String checkemail) {
//		System.out.println("컨트롤러 도착 : " + checkemail);
//		ResponseData responseDto = new ResponseData();
//		
//		if(memberService.checkEmail(checkemail)) {
//			responseDto.setCode("000");
//			responseDto.setMessages("사용가능");
//		} else {
//			responseDto.setCode("001");
//			responseDto.setMessages("사용불가능:중복된값 존재");
//		}
//
//		return new ResponseEntity<ResponseData>(responseDto, HttpStatus.OK);
//	}
	
	@PostMapping("signup")
	public ResponseEntity<ResponseData> inputSignupData(@RequestBody MemberDTO memberDTO) {
		ResponseData responseData = new ResponseData();
		HttpStatus httpStatus = null;
		
		try {
			if(memberService.inputMember(memberDTO)) {
				responseData.setCode(ResponseDataEnum.signup_true.getCode());
				responseData.setMessages(ResponseDataEnum.signup_true.getMessages());
				responseData.setSolution(ResponseDataEnum.signup_true.getSolution());
			} else {
				responseData.setCode(ResponseDataEnum.signup_false.getCode());
				responseData.setMessages(ResponseDataEnum.signup_false.getMessages());
				responseData.setSolution(ResponseDataEnum.signup_false.getSolution());
			}
			
			httpStatus = HttpStatus.OK;
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	@PostMapping("login")
	public ResponseEntity<ResponseData> canLogin(@RequestBody LoginDTO loginDTO, HttpSession session) {
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = memberService.canLoginMember(loginDTO);
			if(responseData.getCode().equals("L000")){
				session.setAttribute("loginMember", loginDTO.getUserId());
			}
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {

			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
			
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
