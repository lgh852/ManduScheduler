package com.manduScheduler.main.thread;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.manduScheduler.main.common.JDBCTemplate;
import com.manduScheduler.main.common.Utils;
import com.manduScheduler.main.service.SchedulerService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ShoppingThread extends Thread {
	
	private String threadName;

	private String clientId;

	private String secret;
	
	public ShoppingThread(String threadName, String clientId, String secret) {
		this.threadName = threadName;
		this.clientId = clientId;
		this.secret = secret;
	}
	
	
	public void run(){
		
		try {
			
			Unirest.setTimeouts(0, 0);
			
			String baseUrl = "https://openapi.naver.com/v1/search/shop.json?";
			HashMap<String,String> param = new HashMap<String,String>();
			
			param.put("query", "비비고 왕교자 350g");
			param.put("sort", "asc");
			param.put("display", "100");
			
			String queryString = Utils.paramToQueryString(param);
			HttpResponse<String> response = Unirest.get(baseUrl + queryString)
				    .header("X-Naver-Client-Id", clientId) // 여기 x-naver-client-id, x-naver-client-secret 은 property(application.yml)에 두고 쓰는게 좋을거같은데 의견은 ?
				    .header("X-Naver-Client-Secret", secret)
				    .asString();
			
			System.out.println(response.getBody()); // 응답결과가 String 형태로 날라오는듯
			
			Gson gson = new Gson();
			
			Map<String, Object> mainBody = gson.fromJson(response.getBody(), Map.class);
			
			List<Map<String, String>> items = (List<Map<String,String>>) mainBody.get("items");
			System.out.println(threadName + " : " + "");
			
			System.out.println(items.get(0));
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
 