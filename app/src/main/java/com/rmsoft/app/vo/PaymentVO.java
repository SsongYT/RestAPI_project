package com.rmsoft.app.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentVO {
	private int payment_pk;
	private int subscribe_no;
	private String payment_type;
	private String payment_st;
	private int payment_price;
	private LocalDateTime payment_dt;
}
