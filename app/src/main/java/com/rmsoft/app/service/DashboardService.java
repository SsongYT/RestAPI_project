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
import com.rmsoft.app.mapper.SolutionMapper;
import com.rmsoft.app.mapper.SubscribeMapper;
import com.rmsoft.app.vo.ServerVO;
import com.rmsoft.app.vo.SolutionVO;
import com.rmsoft.app.vo.SubscribeVO;

@Service
public class DashboardService {
	
	private final SolutionMapper solutionMapper;
	private final SubscribeMapper subscribeMapper;
	private final MemberMapper memberMapper;
	private final ServerMapper serverMapper;
	
	public DashboardService(SolutionMapper solutionMapper, SubscribeMapper subscribeMapper, MemberMapper memberMapper, ServerMapper serverMapper) {
		this.solutionMapper = solutionMapper;
		this.subscribeMapper = subscribeMapper;
		this.memberMapper = memberMapper;
		this.serverMapper = serverMapper;
	}
	
	// 대시보드 정보얻기
	public ResponseData findDashboardData(String userId) throws SQLException, IOException {
		int memberPK = memberMapper.selectMemberPkByUserId(userId);
		ResponseData responseData = new ResponseData();
		Map<String, Object> mapData = new HashMap<String, Object>();
		
		// 사용량 얻기
		ServerVO serverVO = serverMapper.selectServerVolumeUsage(memberPK);
		// 종료일 얻기
		SubscribeVO subscribeVO = subscribeMapper.selectSubscribeEndDt(memberPK);
		// 총용량 얻기
		SolutionVO solutionVO = solutionMapper.selectSolutionBySolutionPk(subscribeVO.getSolution_no());

		if(serverVO != null && subscribeVO != null && solutionVO != null) {
			mapData.put("solution", solutionVO.getSolution_type());
			mapData.put("volumeUsage", serverVO.getVolume_usage());
			mapData.put("solutionVolume", solutionVO.getSolution_volume());
			mapData.put("endDt", subscribeVO.getEnd_dt());
			
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());

			responseData.setData(mapData);
		} else {
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());

		}
		
		return responseData;
	}

}
