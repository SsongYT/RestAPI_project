package com.rmsoft.app.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscribeVO {
	private int subscribe_pk;
	private int member_no;
	private int solution_no;
	private LocalDateTime start_dt;
	private LocalDateTime end_dt;
}
