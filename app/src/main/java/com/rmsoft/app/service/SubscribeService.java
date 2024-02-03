package com.rmsoft.app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rmsoft.app.dto.SubscribeDTO;
import com.rmsoft.app.dto.SubscribeModifyDTO;
import com.rmsoft.app.etc.ResponseData;
import com.rmsoft.app.etc.ResponseDataEnum;
import com.rmsoft.app.mapper.MemberMapper;
import com.rmsoft.app.mapper.PaymentMapper;
import com.rmsoft.app.mapper.ServerMapper;
import com.rmsoft.app.mapper.SolutionMapper;
import com.rmsoft.app.mapper.SubscribeMapper;
import com.rmsoft.app.vo.PaymentVO;
import com.rmsoft.app.vo.ServerVO;
import com.rmsoft.app.vo.SolutionVO;
import com.rmsoft.app.vo.SubscribeVO;

@Service
public class SubscribeService {
	
	private final SolutionMapper solutionMapper;
	private final SubscribeMapper subscribeMapper;
	private final MemberMapper memberMapper;
	private final PaymentMapper paymentMapper;
	private final ServerMapper serverMapper;
	
	public SubscribeService(SolutionMapper solutionMapper, SubscribeMapper subscribeMapper, MemberMapper memberMapper, PaymentMapper paymentMapper, ServerMapper serverMapper) {
		this.solutionMapper = solutionMapper;
		this.subscribeMapper = subscribeMapper;
		this.memberMapper = memberMapper;
		this.paymentMapper = paymentMapper;
		this.serverMapper = serverMapper;
	}
	
	//페이지 접속후 솔루션 타입 받아오기
	public ResponseData findSolutionType() throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		List<String> solutionTypeList = solutionMapper.selectSolutionType();
		
		if(solutionTypeList.size() != 0) {
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_true.getSolution());
			responseData.setData(solutionTypeList);
		} else {
			// 에러
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_false.getSolution());
		}
		
		return responseData;
	}
	
	//솔루션 선택시 솔루션 정보(용량,가격 받아오기)
	public ResponseData findVolumeBySolution(String choiceSolution) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		SolutionVO solutionData = solutionMapper.selectSolution(choiceSolution);
		
		if(solutionData != null) {
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_true.getSolution());
			responseData.setData(solutionData);
		} else {
			// 에러
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());
			responseData.setSolution(ResponseDataEnum.basic_false.getSolution());
		}		
		
		return responseData;
	}
	
	//구독정보 저장
	public ResponseData inputSubscribe(SubscribeDTO subscribeDTO, String userId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		int memberPk = memberMapper.selectMemberPkByUserId(userId);
		
		int diffMonths = subscribeDTO.getDiffMonths();
		
		SolutionVO solutionData = solutionMapper.selectSolution(subscribeDTO.getSolutionType());
		int price = (solutionData.getSolution_price() * diffMonths);

		if(subscribeDTO.getSolutionPrice() == price) {
			//VO set
			SubscribeVO subscribeVO = new SubscribeVO();
			subscribeVO.setMember_no(memberPk);
			subscribeVO.setSolution_no(solutionData.getSolution_pk());
			subscribeVO.setStart_dt(dateFormat(subscribeDTO.getStartDate()));
			subscribeVO.setEnd_dt(dateFormat(subscribeDTO.getEndDate()));
			// 구독 저장 (selectKey로 PK값 받아옴)
			subscribeMapper.insertSubscribe(subscribeVO);
			if(subscribeVO.getSubscribe_pk() != 0) {
				// 구독 저장 성공
				PaymentVO paymentVO = new PaymentVO(); 
				paymentVO.setSubscribe_no(subscribeVO.getSubscribe_pk());
				paymentVO.setPayment_type("카드");
				paymentVO.setPayment_st("1");      //1(결제후-디폴트), 0(결제전)
				paymentVO.setPayment_price(price);
				// 결제 저장
				if(paymentMapper.insertPayment(paymentVO) == 1) {
					// 결제 저장 성공
					ServerVO serverVO = new ServerVO();
					serverVO.setSubscribe_no(subscribeVO.getSubscribe_pk());
					
					
					
					// 여기를... REST로???
					// 서버 저장
					if(serverMapper.insertServer(serverVO) == 1) {
						//서버 저장 성공
						responseData.setCode(ResponseDataEnum.basic_true.getCode());
						responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
						responseData.setSolution(ResponseDataEnum.basic_true.getSolution());
					} else {
						// 서버 저장 실패
					}
					
					
					
				} else {
					// 결제 저장 실패
				}
			} else {
				// 구독 저장 실패
			}
		} else {
			// 금액 위변조 의심
		}
		
		
		return responseData;
		
	}
	
	
	//구독정보 변경
	public ResponseData modifySubscribe(SubscribeModifyDTO subscribeModifyDTO, String userId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		int memberPk = memberMapper.selectMemberPkByUserId(userId);
		
		
		return responseData;
	}

	
	//날짜 변환하기
	public LocalDateTime dateFormat(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(date + " 00:00:00", formatter);
		return localDateTime;
	}
}
