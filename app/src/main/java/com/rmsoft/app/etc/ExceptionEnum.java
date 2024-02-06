package com.rmsoft.app.etc;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
	
	SQLException(HttpStatus.INTERNAL_SERVER_ERROR, "E005", "서버에 문제가 있습니다."),
	IOException(HttpStatus.INTERNAL_SERVER_ERROR, "E005", "서버에 문제가 있습니다.");
	
	private HttpStatus httpStatus;
	private String code;
	private String messages;
	
	ExceptionEnum(HttpStatus httpStatus, String code, String messages) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.messages = messages;
	}
	
}
