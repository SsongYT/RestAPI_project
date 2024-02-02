package com.rmsoft.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscribeDTO {
	private String solutionType;
	private String subscribeDate;
	private int solutionPrice;
	private int diffMonths;
}
