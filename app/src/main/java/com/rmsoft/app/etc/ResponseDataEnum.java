package com.rmsoft.app.etc;

import lombok.Getter;

@Getter
public enum ResponseDataEnum {
	
	basic_true("000", "성공"),
	basic_false("001", "데이터없음"),
	
	check_id_true("CI000", "사용가능"),
	check_id_false("CI001", "아이디 중복"),
	
	signup_true("S000", "회원가입성공"),
	signup_false("S001", "회원가입실패"),
	
	login_true("L000", "로그인성공"),
	login_fasle_id("L001", "아이디 존재하지않음"),
	login_fasle_password("L002", "비밀번호 불일치");
	
	private String code;
	private String messages;
	
	ResponseDataEnum(String code, String messages) {
		this.code = code;
		this.messages = messages;

	}
	
}
