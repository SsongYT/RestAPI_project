package com.rmsoft.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscribeModifyDTO {
	private String modifySolutionType;
	private String modifyStartDate;
	private String modifyEndDate;
	private int modifySolutionPrice;
	private int computePrice;
}
