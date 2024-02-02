package com.rmsoft.app.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SolutionVO {
	private int solution_pk;
	private String solution_type;
	private long solution_volume;
	private int solution_price;
}
