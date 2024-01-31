package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.dto.LoginDTO;
import com.rmsoft.app.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	//ID 중복체크
	int selectMemberById(String checkId) throws SQLException, IOException;
	// 회원가입
	int insertMember(MemberDTO memberDTO) throws SQLException, IOException;
	//아이디 존재유무확인
	int selectLoginById(LoginDTO loginDTO) throws SQLException, IOException;
	//로그인 시도
	int selectLoginMember(LoginDTO loginDTO) throws SQLException, IOException;
	
}
