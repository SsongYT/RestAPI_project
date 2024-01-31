package com.rmsoft.app.etc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
	private String code;
	private String messages;
	private String solution;
	private Object data;
}
