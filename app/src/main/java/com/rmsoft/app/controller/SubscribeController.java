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

import com.rmsoft.app.dto.SubscribeDTO;
import com.rmsoft.app.etc.ResponseData;
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
				//에러코드
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
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
				//에러코드
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
	@PostMapping(value = "subscribe")
	public ResponseEntity<ResponseData> inputSubscribe(@RequestBody SubscribeDTO subscribeDTO, HttpSession session) {
		String userId= (String)session.getAttribute("loginMember");
		ResponseData responseData = null;
		HttpStatus httpStatus = null;

		try {
			responseData = subscribeService.inputSubscribe(subscribeDTO, userId);
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {

			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
	
}
