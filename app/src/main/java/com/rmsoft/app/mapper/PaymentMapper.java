package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.PaymentVO;

@Mapper
public interface PaymentMapper {
	//결제정보 저장
	int insertPayment(PaymentVO paymentVO) throws SQLException, IOException;

}
