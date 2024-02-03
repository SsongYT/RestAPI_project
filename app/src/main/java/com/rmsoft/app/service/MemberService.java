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
	
	public ResponseData hasMemberId(String checkId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		if(memberMapper.selectMemberById(checkId) == 0) {
			responseData.setCode(ResponseDataEnum.check_id_true.getCode());
			responseData.setMessages(ResponseDataEnum.check_id_true.getMessages());
			responseData.setSolution(ResponseDataEnum.check_id_true.getSolution());
		} else {
			responseData.setCode(ResponseDataEnum.check_id_false.getCode());
			responseData.setMessages(ResponseDataEnum.check_id_false.getMessages());
			responseData.setSolution(ResponseDataEnum.check_id_false.getSolution());
		}
		
		return responseData;
	}

	public ResponseData inputMember(MemberDTO memberDTO) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		
		if(memberMapper.insertMember(memberDTO) == 1) {
			responseData.setCode(ResponseDataEnum.signup_true.getCode());
			responseData.setMessages(ResponseDataEnum.signup_true.getMessages());
			responseData.setSolution(ResponseDataEnum.signup_true.getSolution());
		} else {
			responseData.setCode(ResponseDataEnum.signup_false.getCode());
			responseData.setMessages(ResponseDataEnum.signup_false.getMessages());
			responseData.setSolution(ResponseDataEnum.signup_false.getSolution());
		}
		
		return responseData;
	}
	
	public ResponseData deleteMember(int deletePK) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		
		if(memberMapper.deleteMember(deletePK) == 1) {
			//성공
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_true.getSolution());
		} else {
			//실패
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_false.getSolution());
		}
		
		return responseData;
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
