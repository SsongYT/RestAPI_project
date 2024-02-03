package com.rmsoft.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscribeModifyDTO {
	private String solutionType;
	private String startDate;
	private String endDate;
}
