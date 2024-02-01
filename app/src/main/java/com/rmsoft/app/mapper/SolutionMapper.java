package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.dto.SolutionDTO;

@Mapper
public interface SolutionMapper {

	List<SolutionDTO> selectSolution(String choiceSolution) throws SQLException, IOException;

}
