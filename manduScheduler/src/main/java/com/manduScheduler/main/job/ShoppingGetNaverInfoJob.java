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
public class ShoppingGetNaverInfoJob implements Job{

	@Value("${naver.client.id}")
	private String clientId;
	@Value("${naver.client.secret}")
	private String secret;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			System.out.println("MainThread Start");

			System.out.println("====================");
			System.out.println(clientId);
			System.out.println(secret);
			System.out.println("====================");
			Thread[] thread=new Thread[10];
			
			for(int i=1;i<=1;i++){
				thread[i]=new Thread(new ShoppingThread("Thread"+i, clientId, secret));
				thread[i].start();
				//thread[i].join();
			}
			System.out.println("MainThread End");	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
 
