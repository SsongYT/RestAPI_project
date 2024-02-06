package com.rmsoft.app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.mapper.MemberMapper;
import com.rmsoft.app.mapper.ServerMapper;
import com.rmsoft.app.vo.ServerVO;

@Service
public class DashboardService {
	
	private final ServerMapper serverMapper;
	private final MemberMapper memberMapper;
	
	public DashboardService(ServerMapper serverMapper, MemberMapper memberMapper) {
		this.serverMapper = serverMapper;
		this.memberMapper = memberMapper;
	}
	
	// 서버테이블 저장
	public ResponseData inputServer(ServerVO serverVO) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		
		if(serverMapper.insertServer(serverVO) == 1) {
			//서버 저장 성공
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());

		} else {
			// 서버 저장 실패
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());

		}
		
		return responseData;
	}
	
	// 서버테이블 사용량 얻기
	public ResponseData findVolumeUsage(String userId) throws SQLException, IOException {
		int memberPK = memberMapper.selectMemberPkByUserId(userId);
		
		ResponseData responseData = new ResponseData();
		Map<String, Integer> mapData = new HashMap<String, Integer>();
		
		ServerVO serverVO = serverMapper.selectServerVolumeUsage(memberPK);
		System.out.println(serverVO);
		if(serverVO != null) {
			mapData.put("voulumeUsage", serverVO.getVolume_usage());
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());

			responseData.setData(mapData);
		} else {
			//실패 로직(검색 안됨)
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());

		}
		
		return responseData;
	}

}
