package com.rmsoft.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.dto.SubscribeDTO;
import com.rmsoft.app.dto.SubscribeModifyDTO;
import com.rmsoft.app.etc.ExceptionEnum;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.service.SubscribeService;

import jakarta.servlet.http.HttpSession;

@RestController
public class SubscribeController {
	
	private final SubscribeService subscribeService;
	
	public SubscribeController(SubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}
	
	// 솔루션 타입가져오기
	@GetMapping(value = "subscribe/solution")
	public ResponseEntity<ResponseData> findSolutionType() {
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = subscribeService.findSolutionType();
			
			if(responseData.getCode().equals("000")) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
		} catch (SQLException | IOException e) {
			responseData = new ResponseData();
			httpStatus = ExceptionEnum.SQLException.getHttpStatus();
			responseData.setCode(ExceptionEnum.SQLException.getCode());
			responseData.setMessages(ExceptionEnum.SQLException.getMessages());
			
		}
		
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	// 솔루션 선택
	@GetMapping(value = "subscribe/solution/{choiceSolution}")
	public ResponseEntity<ResponseData> findVolumeBySolution(@PathVariable("choiceSolution") String choiceSolution) {
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = subscribeService.findVolumeBySolution(choiceSolution);
			
			if(responseData.getCode().equals("000")) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
		} catch (SQLException | IOException e) {
			responseData = new ResponseData();
			httpStatus = ExceptionEnum.SQLException.getHttpStatus();
			responseData.setCode(ExceptionEnum.SQLException.getCode());
			responseData.setMessages(ExceptionEnum.SQLException.getMessages());
			
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	//구독 신청
	@PostMapping(value = "subscribe")
	public ResponseEntity<ResponseData> inputSubscribe(@RequestBody SubscribeDTO subscribeDTO, HttpSession session) {
		String userId= (String)session.getAttribute("loginMember");
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		if(userId != null) {
			try {
				responseData = subscribeService.inputSubscribe(subscribeDTO, userId);
				if(responseData.getCode().equals("000")) {					
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
				
			} catch (SQLException | IOException e) {
				responseData = new ResponseData();
				httpStatus = ExceptionEnum.SQLException.getHttpStatus();
				responseData.setCode(ExceptionEnum.SQLException.getCode());
				responseData.setMessages(ExceptionEnum.SQLException.getMessages());
			}
			
		} else {
			// 세션 에러
			responseData = new ResponseData();
			httpStatus = HttpStatus.BAD_REQUEST;
			responseData.setCode(ResponseDataEnum.session_fasle.getCode());
			responseData.setMessages(ResponseDataEnum.session_fasle.getMessages());
		}

		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	//구독정보 변경시 데이터 얻기
	@PostMapping(value="subscribe/payment")
	public ResponseEntity<ResponseData> findModifySubscribeData(@RequestBody SubscribeModifyDTO SubscribeModifyDTO, HttpSession session) {
		String userId= (String)session.getAttribute("loginMember");
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		if(userId != null) {
			try {
				
				responseData = subscribeService.findModifySubscribeData(SubscribeModifyDTO, userId);
				if(responseData.getCode().equals("000")) {					
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
				
			} catch (SQLException | IOException e) {
				responseData = new ResponseData();
				httpStatus = ExceptionEnum.SQLException.getHttpStatus();
				responseData.setCode(ExceptionEnum.SQLException.getCode());
				responseData.setMessages(ExceptionEnum.SQLException.getMessages());
			}
			
		} else {
			// 세션 에러
			responseData = new ResponseData();
			httpStatus = HttpStatus.BAD_REQUEST;
			responseData.setCode(ResponseDataEnum.session_fasle.getCode());
			responseData.setMessages(ResponseDataEnum.session_fasle.getMessages());
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	//구독정보 변경
	@PatchMapping(value="subscribe")
	public ResponseEntity<ResponseData> modifySubscribe(@RequestBody SubscribeModifyDTO SubscribeModifyDTO, HttpSession session) {
		String userId= (String)session.getAttribute("loginMember");
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		if(userId != null) {
			try {
				
				responseData = subscribeService.modifySubscribe(SubscribeModifyDTO, userId);
				if(responseData.getCode().equals("000")) {					
					httpStatus = HttpStatus.OK;
				} else {
					httpStatus = HttpStatus.BAD_REQUEST;
				}
				
			} catch (SQLException | IOException e) {
				responseData = new ResponseData();
				httpStatus = ExceptionEnum.SQLException.getHttpStatus();
				responseData.setCode(ExceptionEnum.SQLException.getCode());
				responseData.setMessages(ExceptionEnum.SQLException.getMessages());
			}
			
		} else {
			// 세션 에러
			responseData = new ResponseData();
			httpStatus = HttpStatus.BAD_REQUEST;
			responseData.setCode(ResponseDataEnum.session_fasle.getCode());
			responseData.setMessages(ResponseDataEnum.session_fasle.getMessages());
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
}
