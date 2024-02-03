package com.rmsoft.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.dto.LoginDTO;
import com.rmsoft.app.dto.MemberDTO;
import com.rmsoft.app.etc.ResponseData;
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
			responseData = memberService.hasMemberId(checkId);
	
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	@PostMapping("signup")
	public ResponseEntity<ResponseData> inputSignupData(@RequestBody MemberDTO memberDTO) {
		ResponseData responseData = new ResponseData();
		HttpStatus httpStatus = null;
		
		try {
			responseData = memberService.inputMember(memberDTO);
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	@DeleteMapping("signup/{deletePK}")
	public ResponseEntity<ResponseData> deleteMember(@PathVariable("deletePK") int deletePK) {
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = memberService.deleteMember(deletePK);
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
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
		}
			
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
