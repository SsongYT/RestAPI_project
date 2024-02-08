package com.rmsoft.app.etc;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rmsoft.app.mapper.ServerMapper;
import com.rmsoft.app.mapper.SubscribeMapper;

@EnableScheduling
@SpringBootApplication
public class Scheduler {
	
	private final SubscribeMapper subscribeMapper;
	private final ServerMapper serverMapper;
	
	public Scheduler(SubscribeMapper subscribeMapper, ServerMapper serverMapper) {
		this.subscribeMapper = subscribeMapper;
		this.serverMapper = serverMapper;
	}
	
	@Scheduled(cron= "* * * * * *")
	private void autoSubscribeUseSt() {
		try {
			
			int chageY = subscribeMapper.schedulerUpdateSubscribeUseStY();
			int chageN = subscribeMapper.schedulerUpdateSubscribeUseStN();
			int delectServer = serverMapper.schedulerDelectServer();
			// 로그를 남겨서 데이터화시키기
			System.out.println("Y로변경 :  " + chageY);
			System.out.println("N로변경 :  " + chageN);
			System.out.println("삭제된 서버 :  " + delectServer);
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
