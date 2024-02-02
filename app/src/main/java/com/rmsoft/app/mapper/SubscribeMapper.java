package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.SubscribeVO;

@Mapper
public interface SubscribeMapper {
	
	//구독정보 저장(selectKey로 PK값 반환)
	void insertSubscribe(SubscribeVO subscribeVO) throws SQLException, IOException;

}
