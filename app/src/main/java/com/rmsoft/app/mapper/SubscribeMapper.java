package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.SubscribeVO;

@Mapper
public interface SubscribeMapper {
	
	//구독정보 저장(selectKey로 PK값 반환)
	void insertSubscribe(SubscribeVO subscribeVO) throws SQLException, IOException;
	// 회원FK를 통해 구독정보 가져오기
	SubscribeVO selectSubscribeByMemberPk(int memberPk) throws SQLException, IOException;
	//구독정보 변경(구독종료일)
	void updateSubscribeEndDT(SubscribeVO subscribeVO) throws SQLException, IOException;

}
