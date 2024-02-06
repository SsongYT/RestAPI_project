package com.rmsoft.app.etc;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rmsoft.app.mapper.ServerMapper;
import com.rmsoft.app.mapper.SubscribeMapper;

@Component
public class Scheduler {
	
	private final SubscribeMapper subscribeMapper;
	private final ServerMapper serverMapper;
	
	public Scheduler(SubscribeMapper subscribeMapper, ServerMapper serverMapper) {
		this.subscribeMapper = subscribeMapper;
		this.serverMapper = serverMapper;
	}
	
	@Scheduled(cron= "0 30 0 * * *")
	private void autoSubscribeUseSt() {
		try {
			
			int chageY = subscribeMapper.schedulerUpdateSubscribeUseStY();
			int chageN = subscribeMapper.schedulerUpdateSubscribeUseStN();
			int delectServer = serverMapper.schedulerDelectServer();
			
			System.out.println("Y로변경 :  " + chageY);
			System.out.println("N로변경 :  " + chageN);
			System.out.println("삭제된 서버 :  " + delectServer);
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
