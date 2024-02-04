package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.SolutionVO;

@Mapper
public interface SolutionMapper {
	
	//페이지접속후 솔루션 타입 가져오기
	List<String> selectSolutionType() throws SQLException, IOException;
	//솔루션 선택시 솔루션 정보 가져오기
	SolutionVO selectSolutionBySolutionType(String choiceSolution) throws SQLException, IOException;
	// 솔루션 PK 번호로 솔루션 정보 가져오기
	SolutionVO selectSolutionBySolutionPk(int solutionNo) throws SQLException, IOException;	


}
