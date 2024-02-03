package com.rmsoft.app.etc;

import lombok.Getter;

@Getter
public enum ResponseDataEnum {
	
	basic_true("000", "성공", null),
	basic_false("001", "데이터없음", "데이터정보를 확인해주세요."),
	
	check_id_true("CI000", "사용가능", "사용가능합니다."),
	check_id_false("CI001", "아이디 중복", "다른 아이디를 사용해주세요."),
	
	signup_true("S000", "회원가입성공", "로그인페이지로 이동합니다."),
	signup_false("S001", "회원가입실패", "다시 시도해주세요."),
	
	login_true("L000", "로그인성공", "메인페이지로 이동합니다."),
	login_fasle_id("L001", "아이디 존재하지않음", "다른 아이디로 시도해주세요."),
	login_fasle_password("L002", "비밀번호 불일치", "비밀번호를 확인해주세요.");
	
	private String code;
	private String messages;
	private String solution;
	
	ResponseDataEnum(String code, String messages, String solution) {
		this.code = code;
		this.messages = messages;
		this.solution = solution;
	}
	
}
