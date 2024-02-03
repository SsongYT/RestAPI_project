package com.rmsoft.app.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.service.ServerService;

@RestController
public class ServerController {
	
	private final ServerService serverService;
	
	public ServerController(ServerService serverService) {
		this.serverService = serverService;
	}
	
	@GetMapping("server/subscribe/{subscribePK}")
	public ResponseEntity<ResponseData> findVolumeUsage(@PathVariable("subscribePK") int subscribePK) {
		ResponseData responseData = null;
		HttpStatus httpStatus = null;
		
		try {
			responseData = serverService.findVolumeUsage(subscribePK);
			
			if(responseData.getCode().equals("000")){
				//성공시 로직
			} else {
				// 실패시 로직
			}
			
			httpStatus = HttpStatus.OK;
			
		} catch (SQLException | IOException e) {

			e.printStackTrace();
			
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
