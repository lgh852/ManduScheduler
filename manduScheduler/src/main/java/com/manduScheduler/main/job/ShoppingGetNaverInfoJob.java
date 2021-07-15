package com.manduScheduler.main.job;

import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.manduScheduler.main.common.JDBCTemplate;
import com.manduScheduler.main.common.Utils;
import com.manduScheduler.main.service.SchedulerService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Component
public class ShoppingGetNaverInfoJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			System.out.println("MainThread Start");
			
			Thread[] thread=new Thread[10];
			
			for(int i=1;i<=1;i++){
				thread[i]=new Thread(new ShoppingThread("Thread"+i));
				thread[i].start();
				//thread[i].join();
			}
			System.out.println("MainThread End");	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class ShoppingThread extends Thread {
	
	private String threadName;

	public ShoppingThread(String threadName) {
		this.threadName = threadName;
	}
	
	
	public void run(){
		
		try {
			
			Unirest.setTimeouts(0, 0);
			
			String baseUrl = "https://openapi.naver.com/v1/search/shop.json?";
			HashMap<String,String> param = new HashMap<String,String>();
			
			param.put("query", "비비고 왕교자 350g");
			param.put("sort", "asc");
			param.put("display", "1");
			
			String queryString = Utils.paramToQueryString(param);
			
			HttpResponse<String> response = Unirest.get(baseUrl + queryString)
				    .header("X-Naver-Client-Id", "RsdNOQG91qKNC7RveZ1S") // 여기 x-naver-client-id, x-naver-client-secret 은 property(application.yml)에 두고 쓰는게 좋을거같은데 의견은 ?
				    .header("X-Naver-Client-Secret", "58uVz9H6ap")
				    .asString();
			
			System.out.println(response.getBody()); // 응답결과가 String 형태로 날라오는듯
			
			Gson gson = new Gson();
			
			Map<String, Object> mainBody = gson.fromJson(response.getBody(), Map.class);
			
			List<Map<String, Object>> items = (List<Map<String,Object>>) mainBody.get("items");
			System.out.println(threadName + " : " + "");
			items.get(0);
			Connection conn = new JDBCTemplate().getConnection();
			
			try {
				new SchedulerService().insertProductInfo(conn, items);
				JDBCTemplate.commit(conn);
			} catch(Exception e) {
				e.printStackTrace();
				JDBCTemplate.rollback(conn);
			} finally {
				JDBCTemplate.close(conn);
			}
			

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
 
