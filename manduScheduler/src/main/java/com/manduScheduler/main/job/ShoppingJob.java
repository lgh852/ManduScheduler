package com.manduScheduler.main.job;

import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.manduScheduler.main.common.JDBCTemplate;
import com.manduScheduler.main.common.Utils;
import com.manduScheduler.main.service.SchedulerService;
import com.manduScheduler.main.thread.ShoppingThread;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
@Component
public class ShoppingJob implements Job{

	@Value("${naver.client.id}")
	private String clientId;
	@Value("${naver.client.secret}")
	private String secret;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			Thread[] thread=new Thread[10];
			String[] query = {"비비고 왕교자 350g","비비고 군만두 450", "하림 치킨너겟 1kg"};
			for(int i=1;i<query.length;i++){
				System.out.println("========= ShoppingThread : "+query[i]+" execute =========== ");
				thread[i]=new Thread(new ShoppingThread("Thread : " + query[i], clientId, secret, query[i]));
				thread[i].start();
				thread[i].join();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
 
