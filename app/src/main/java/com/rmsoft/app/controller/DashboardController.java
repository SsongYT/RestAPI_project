package com.rmsoft.app.controller;


import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.service.DashboardService;

import jakarta.servlet.http.HttpSession;

@RestController
public class DashboardController {
	
	private final DashboardService serverService;
	
	public DashboardController(DashboardService serverService, HttpSession session) {
		this.serverService = serverService;
	}
	
	@GetMapping("dashboard")
	public ResponseEntity<ResponseData> getData() {
		//세션에러 방지용
		//String userId= (String)session.getAttribute("loginMember");
		String userId = "test5";
				
		ResponseData responseData = new ResponseData();
		HttpStatus httpStatus = null;
		
		try {
			responseData = serverService.findVolumeUsage(userId);
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}
	
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
