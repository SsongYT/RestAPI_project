package com.rmsoft.app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rmsoft.app.dto.SolutionDTO;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.mapper.SolutionMapper;

@Service
public class SubscribeService {
	
	private final SolutionMapper solutionMapper;
	
	public SubscribeService(SolutionMapper solutionMapper) {
		this.solutionMapper = solutionMapper;
	}

	public ResponseData findVolumeBySolution(String choiceSolution) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		List<SolutionDTO> solutionList = solutionMapper.selectSolution(choiceSolution);
		
		if(solutionList.size() != 0) {
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_true.getSolution());
			responseData.setData(solutionList);
		} else {
			// 에러
		}
		
		System.out.println(responseData);
		
		
		return responseData;
	}
}
