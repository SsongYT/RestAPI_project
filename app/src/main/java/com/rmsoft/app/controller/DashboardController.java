package com.rmsoft.app.controller;


import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.etc.ExceptionEnum;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.service.DashboardService;

import jakarta.servlet.http.HttpSession;

@RestController
public class DashboardController {
	
	private final DashboardService dashboardService;
	
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("dashboard/data")
	public ResponseEntity<ResponseData> findDashboardData(HttpSession session) {
		String userId= (String)session.getAttribute("loginMember");
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		if(userId != null) {
			try {
				responseData = dashboardService.findDashboardData(userId);
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
