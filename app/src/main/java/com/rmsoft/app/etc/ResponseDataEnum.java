package com.rmsoft.app.etc;

import lombok.Getter;

@Getter
public enum ResponseDataEnum {
	//커스텀
	basic_true("000", "성공"),
	basic_false("001", "실패"),
	
	check_false_id("C01", "아이디 중복"),
	
	login_fasle_id("L01", "아이디 존재하지않음"),
	login_fasle_password("L02", "비밀번호 불일치"),
	
	price_fasle("P01", "결제금액 불일치"),
	
	session_fasle("S01", "로그인정보 없음");

	private String code;
	private String messages;
	
	ResponseDataEnum(String code, String messages) {
		this.code = code;
		this.messages = messages;
	}
	
}
