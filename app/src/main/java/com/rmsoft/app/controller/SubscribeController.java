package com.rmsoft.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.service.SubscribeService;

@RestController
@RequestMapping(value = "/subscribe/*")
public class SubscribeController {
	
	private final SubscribeService subscribeService;
	
	public SubscribeController(SubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}
	
	@GetMapping(value = "solution/{choiceSolution}")
	public ResponseEntity<ResponseData> findVolumeBySolution(@PathVariable("choiceSolution") String choiceSolution) {
		System.out.println(choiceSolution);
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = subscribeService.findVolumeBySolution(choiceSolution);
			
			if(responseData.getCode().equals("000")) {
				httpStatus = HttpStatus.OK;
			} else {
				//에러
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
