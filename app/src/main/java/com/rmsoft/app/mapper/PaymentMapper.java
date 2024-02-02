package com.rmsoft.app.mapper;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.rmsoft.app.vo.PaymentVO;

@Mapper
public interface PaymentMapper {

	int insertPayment(PaymentVO paymentVO) throws SQLException, IOException;

}
