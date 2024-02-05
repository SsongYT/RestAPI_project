package com.rmsoft.app.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jakarta.transaction.Transactional;

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
			responseData.setData(solutionTypeList);
		} else {
			// 에러
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());

		}
		
		return responseData;
	}
	
	//솔루션 선택시 솔루션 정보(용량,가격 받아오기)
	public ResponseData findVolumeBySolution(String choiceSolution) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		SolutionVO solutionData = solutionMapper.selectSolutionBySolutionType(choiceSolution);
		
		if(solutionData != null) {
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setData(solutionData);
		} else {
			// 에러
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());

		}		
		
		return responseData;
	}
	
	//구독정보 저장
	public ResponseData inputSubscribe(SubscribeDTO subscribeDTO, String userId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		int memberPk = memberMapper.selectMemberPkByUserId(userId);
		
		int diffMonths = compareMonth(dateFormat(subscribeDTO.getStartDate()), dateFormat(subscribeDTO.getEndDate()));
		
		SolutionVO solutionData = solutionMapper.selectSolutionBySolutionType(subscribeDTO.getSolutionType());
		int price = (solutionData.getSolution_price() * diffMonths);

		if(subscribeDTO.getSolutionPrice() == price) {
			//VO set
			SubscribeVO subscribeVO = new SubscribeVO();
			subscribeVO.setMember_no(memberPk);
			subscribeVO.setSolution_no(solutionData.getSolution_pk());
			subscribeVO.setStart_dt(dateFormat(subscribeDTO.getStartDate()));
			subscribeVO.setEnd_dt(dateFormat(subscribeDTO.getEndDate()));
			subscribeVO.setUse_st('Y');
			// 구독 저장 (selectKey로 PK값 받아옴)
			subscribeMapper.insertSubscribe(subscribeVO);
			if(subscribeVO.getSubscribe_pk() != 0) {
				// 구독 저장 성공
				PaymentVO paymentVO = new PaymentVO(); 
				paymentVO.setSubscribe_no(subscribeVO.getSubscribe_pk());
				paymentVO.setPayment_type("카드");
				paymentVO.setPayment_st("Y");      //1(결제후-디폴트), 0(결제전)
				paymentVO.setPayment_price(price);
				paymentVO.setPayment_dt(LocalDateTime.now());
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
	
	//구독정보 변경시 데이터 얻기
	public ResponseData findModifySubscribeData(SubscribeModifyDTO subscribeModifyDTO, String userId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		int memberPk = memberMapper.selectMemberPkByUserId(userId);
		// 기존 구독정보
		SubscribeVO subscribeVO = subscribeMapper.selectSubscribeByMemberPk(memberPk);
		// 기존 구독의 솔루션 정보
		SolutionVO solutionVO =  solutionMapper.selectSolutionBySolutionPk(subscribeVO.getSolution_no());
		
		int computePrice = computePrice(subscribeModifyDTO, subscribeVO, solutionVO);		
		
		if(computePrice != -1) {
			//변경될 금액이 있다.	
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("computePrice", computePrice);
			
			responseData.setCode(ResponseDataEnum.basic_true.getCode());
			responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
			responseData.setData(map);
			
		} else {
			// 변경이 없는데 온것 예외처리
			responseData.setCode(ResponseDataEnum.basic_false.getCode());
			responseData.setMessages(ResponseDataEnum.basic_false.getMessages());
		}
		
		return responseData;
	}
	
	//구독정보 변경
	@Transactional(rollbackOn = Exception.class)
	public ResponseData modifySubscribe(SubscribeModifyDTO subscribeModifyDTO, String userId) throws SQLException, IOException {
		ResponseData responseData = new ResponseData();
		boolean isVerify = false;
		
		//유저 PK 얻기
		int memberPk = memberMapper.selectMemberPkByUserId(userId);

		// 구독정보 업데이트용 VO 얻기
		SubscribeVO subscribeVO = subscribeMapper.selectSubscribeByMemberPk(memberPk);
		
		//결제 정보 인설트용 VO 생성
		PaymentVO paymentVO = new PaymentVO();
		
		// subscribeModifyDTO에 따라 구분
		if(subscribeModifyDTO.getModifySolutionType() == null && subscribeModifyDTO.getModifyStartDate() == null) {
			// 취소
			// 구독을 다음달에 끝나도록 변경
			subscribeVO.setEnd_dt(now1Month());
			// 결제정보 설정(기존 구독정보의 PK)
			paymentVO.setSubscribe_no(subscribeVO.getSubscribe_pk());
			isVerify= true;
			
		} else if(subscribeModifyDTO.getModifySolutionType() == null && subscribeModifyDTO.getModifyStartDate() != null) {
			// 기간 변경
			//기존 구독정보 끝나는 날짜 변경
			subscribeVO.setEnd_dt(dateFormat(subscribeModifyDTO.getModifyEndDate()));
			// 결제정보 설정(기존 구독정보의 PK)
			paymentVO.setSubscribe_no(subscribeVO.getSubscribe_pk());	
			isVerify= true;
			
		} else if(subscribeModifyDTO.getModifySolutionType() != null) {
			// 변경할 솔루션의 정보 얻기
			SolutionVO modifySolutionVO = solutionMapper.selectSolutionBySolutionType(subscribeModifyDTO.getModifySolutionType());
			
			// 새로운 구독정보 인설트용VO
			SubscribeVO subscribeModifyVO = new SubscribeVO();
			subscribeModifyVO.setMember_no(memberPk);
			subscribeModifyVO.setSolution_no(modifySolutionVO.getSolution_pk());
			subscribeModifyVO.setUse_st('N');
			
			if(subscribeModifyDTO.getModifyStartDate() == null) {
				// 혹시모를 예외 -> 디폴트값 : 다음달 설정
				subscribeVO.setEnd_dt(now1Month());	
				subscribeModifyVO.setStart_dt(now1Month());
				subscribeModifyVO.setEnd_dt(subscribeVO.getEnd_dt());
				
			} else if(subscribeModifyDTO.getModifyStartDate() != null) {		
				// 종류 변경 , 기간 변경 or 기간 변경X
				subscribeVO.setEnd_dt(dateFormat(subscribeModifyDTO.getModifyStartDate()));
				subscribeModifyVO.setStart_dt(dateFormat(subscribeModifyDTO.getModifyStartDate()));
				subscribeModifyVO.setEnd_dt(dateFormat(subscribeModifyDTO.getModifyEndDate()));
			}
			
			// 1. 새로운 구독정보 인설트
			subscribeMapper.insertSubscribe(subscribeModifyVO);
			System.out.println(subscribeModifyVO);
			if(subscribeModifyVO.getSubscribe_pk() != 0) {
				// 결제정보 설정 (새로 인설트한 구독정보의 PK )
				paymentVO.setSubscribe_no(subscribeModifyVO.getSubscribe_pk());
				System.out.println(paymentVO);
				isVerify= true;
			}
			
		} 
		
		if(isVerify) {
			// 2. 기존 구독정보 업데이트
			if(subscribeMapper.updateSubscribeEndDT(subscribeVO) == 1) {
				paymentVO.setPayment_type("카드");
				paymentVO.setPayment_st("Y");      //1(결제후-디폴트), 0(결제전)
				paymentVO.setPayment_price(subscribeModifyDTO.getComputePrice());
				paymentVO.setPayment_dt(LocalDateTime.now());
				// 3. 결제 정보 인설트
				if(paymentMapper.insertPayment(paymentVO) == 1) {
					responseData.setCode(ResponseDataEnum.basic_true.getCode());
					responseData.setMessages(ResponseDataEnum.basic_true.getMessages());
					
				} else {
					// 결제 정보 인설트 실패 예외
					throw new SQLException();
				}
			} else {
				// 구독정보 업데이트 실패 예외
				throw new SQLException();
			}
		} else {
			// 결제정보 인설트에 따른 예외처리
			throw new SQLException();
		}
		
		return responseData;
	}

	
	//날짜 변환하기
	public LocalDateTime dateFormat(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(date + " 00:00:00", formatter);
		return localDateTime;
	}
	
	// 현재날짜 +1달후 구하기
	public LocalDateTime now1Month() {
		LocalDate now = LocalDate.now();
		LocalDate now1Month = now.plusMonths(1);
		return dateFormat(now1Month.toString());
	}
	
	// 월 차이 구하기
	public int compareMonth(LocalDateTime A, LocalDateTime B) {
		
	    int monthA = A.getYear() * 12 + A.getMonthValue();
	    int monthB = B.getYear() * 12 + B.getMonthValue();
	    
	    return monthA - monthB;
	}
	
	// 금액 계산하는 메서드
	public int computePrice(SubscribeModifyDTO subscribeModifyDTO, SubscribeVO subscribeVO, SolutionVO solutionVO) throws SQLException, IOException {
		int computePrice = -1;
		boolean isCompute = false;

		//타입 변경 유무 확인
		if(subscribeModifyDTO.getModifySolutionType() == null || subscribeModifyDTO.getModifySolutionType().equals(solutionVO.getSolution_type())) {
			//타입 변경 X
			subscribeModifyDTO.setModifySolutionType(solutionVO.getSolution_type());
			subscribeModifyDTO.setModifySolutionPrice(solutionVO.getSolution_price());
		} else {
			//타입 변경
			// 변경할 솔루션의 정보
			SolutionVO modifySolutionVO = solutionMapper.selectSolutionBySolutionType(subscribeModifyDTO.getModifySolutionType());
			// 얻어온 솔루션가격 set
			subscribeModifyDTO.setModifySolutionPrice(modifySolutionVO.getSolution_price());
			
			isCompute = true;
		}
		
		LocalDateTime modifyStartDate = null;
		LocalDateTime modifyEndDate = null;
		
		//기간 변경유무
		if(subscribeModifyDTO.getModifyEndDate() == null || dateFormat(subscribeModifyDTO.getModifyEndDate()).equals(subscribeVO.getEnd_dt())) {
			// 기간 변경 X
			modifyStartDate = now1Month();
			modifyEndDate = subscribeVO.getEnd_dt();
		} else {
			//시간 변경
			modifyStartDate = dateFormat(subscribeModifyDTO.getModifyStartDate());
			modifyEndDate = dateFormat(subscribeModifyDTO.getModifyEndDate());	
			isCompute = true;
		}

		// 금액 계산
		if(isCompute) {
			int compareMonthA = compareMonth(subscribeVO.getEnd_dt(), modifyStartDate);
			int compareMonthB = compareMonth(modifyEndDate, subscribeVO.getEnd_dt());
			
			int price = subscribeModifyDTO.getModifySolutionPrice() - solutionVO.getSolution_price();

			computePrice = (compareMonthA * price) + (compareMonthB * subscribeModifyDTO.getModifySolutionPrice());

		}
		
		return computePrice;
	}
	
}
