package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.ServerVO;

@Mapper
public interface ServerMapper {
	
	// 서버정보 저장
	int insertServer(ServerVO serverVO) throws SQLException, IOException;



}
