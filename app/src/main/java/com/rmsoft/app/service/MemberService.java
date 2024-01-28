package com.rmsoft.app.service;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
	public Boolean checkId(String checkid) {
		Boolean reselt = false;
		
		//디비 where = {checkid} 로우가 있으면 사용불가능 , 없으면 사용가능
		if(!checkid.equals("aaa")) {
			reselt = true;
		}
		
		return reselt;
	}
}
