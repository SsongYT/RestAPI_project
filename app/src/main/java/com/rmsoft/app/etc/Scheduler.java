package com.rmsoft.app.etc;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rmsoft.app.mapper.SubscribeMapper;

@Component
public class Scheduler {
	
	private final SubscribeMapper subscribeMapper;
	
	public Scheduler(SubscribeMapper subscribeMapper) {
		this.subscribeMapper = subscribeMapper;
	}
	
	@Scheduled(cron= "0 30 0 * * *")
	private void autoSubscribeUseSt() {
		try {
			
			int chageY = subscribeMapper.schedulerSubscribeUseStY();
			int chageN = subscribeMapper.schedulerSubscribeUseStN();
			System.out.println("Y로변경 :  " + chageY);
			System.out.println("N로변경 :  " + chageN);
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
