package com.rmsoft.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmsoft.app.etc.ResponseData;

@RestController
public class DashboardController {
	
	@GetMapping("dashboard")
	public ResponseEntity<ResponseData> getData() {
		ResponseData responseData = new ResponseData();
		HttpStatus httpStatus = null;
		
		
		try {
			//데이터 담기
//			JsonObject data = new JsonObject();
//			JsonObject params = new JsonObject();
//			params.addProperty("key", 1);
//			params.addProperty("age", 20);
//			params.addProperty("userNm", "홍길동");
//			data.add("userInfo", params);
			
			
			// 세션 유저 정보로 구독PK 알아와서 대입
			int subscribeNo = 8;
			
			
			URL url = new URL("http://localhost:8081/server/subscribe/" + subscribeNo);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine = in.readLine();
			
//	        ObjectMapper mapper = new ObjectMapper();
//	        Map<String, Object> returnMap = mapper.readValue(inputLine, Map.class);
//
//	        responseData.setCode((String)returnMap.get("code"));
//	        responseData.setMessages((String)returnMap.get("messages"));
//	        responseData.setSolution((String)returnMap.get("solution"));
//	        responseData.setData(returnMap.get("data"));

	        httpStatus = HttpStatus.OK;
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<ResponseData>(responseData, httpStatus);
	}
}
