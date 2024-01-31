package com.rmsoft.app.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.rmsoft.app.dto.LoginDTO;
import com.rmsoft.app.dto.MemberDTO;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.mapper.MemberMapper;

@Service
public class MemberService {
	
	private final MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	public boolean checkId(String checkId) throws SQLException, IOException {
		boolean reselt = false;
		if(memberMapper.selectMemberById(checkId) == 0) {
			reselt = true;
		}
		return reselt;
	}
	
//	public boolean checkEmail(String checkemail) {
//		boolean reselt = false;
//		
//		//디비 where = {checkid} 로우가 있으면 사용불가능 , 없으면 사용가능
//		if(!checkemail.equals("aaa")) {
//			reselt = true;
//		}
//		
//		return reselt;
//	}

	public boolean inputMember(MemberDTO memberDTO) throws SQLException, IOException {
		boolean reselt = false;

		if(memberMapper.insertMember(memberDTO) == 1) {
			reselt = true;
		}
		
		return reselt;
	}

	public ResponseData canLoginMember(LoginDTO loginDTO) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();

		if(memberMapper.selectLoginById(loginDTO) == 1) {
			if(memberMapper.selectLoginMember(loginDTO) == 1) {
				responseData.setCode(ResponseDataEnum.login_true.getCode());
				responseData.setMessages(ResponseDataEnum.login_true.getMessages());
				responseData.setSolution(ResponseDataEnum.login_true.getSolution());
				
			} else {
				responseData.setCode(ResponseDataEnum.login_fasle_password.getCode());
				responseData.setMessages(ResponseDataEnum.login_fasle_password.getMessages());
				responseData.setSolution(ResponseDataEnum.login_fasle_password.getSolution());
			}
		} else {
			responseData.setCode(ResponseDataEnum.login_fasle_id.getCode());
			responseData.setMessages(ResponseDataEnum.login_fasle_id.getMessages());
			responseData.setSolution(ResponseDataEnum.login_fasle_id.getSolution());
		}
		
		return responseData;
	}

}
