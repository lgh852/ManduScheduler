package com.manduScheduler.main.thread;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.manduScheduler.main.common.JDBCTemplate;
import com.manduScheduler.main.common.Utils;
import com.manduScheduler.main.service.SchedulerService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ShoppingThread extends Thread {
	
	private String baseUrl = "https://openapi.naver.com/v1/search/shop.json?";
	
	private String threadName;

	private String clientId;

	private String secret;
	
	private String query; 
	
	public ShoppingThread(String threadName, String clientId, String secret, String query) {
		this.threadName = threadName;
		this.clientId = clientId;
		this.secret = secret;
		this.query = query;
	}
	
	
	public void run(){ 
		
		try {
			System.out.println("============================================================================================");
			System.out.println("threadName : " +threadName);
			
			Unirest.setTimeouts(0, 0);
			Gson gson = new Gson();
			int totalCnt = 0;
			int display = 100;
			
			HashMap<String,String> param = new HashMap<String,String>();
			
			param.put("query", query);
			param.put("sort", "asc");
			param.put("display", String.valueOf(display));
			
			for(int start = 1; start <= 10 && ((start-1) * display) <= totalCnt ; start++) {
				System.out.println("-------------------------START-----------------------------------");
				param.put("start", String.valueOf(start));
				System.out.println("query : " + query);
				System.out.println("start : " + start);
				System.out.println("display : " + display);
				
				String queryString = Utils.paramToQueryString(param);
				HttpResponse<String> response = Unirest.get(baseUrl + queryString)
					    .header("X-Naver-Client-Id", clientId) // 여기 x-naver-client-id, x-naver-client-secret 은 property(application.yml)에 두고 쓰는게 좋을거같은데 의견은 ?
					    .header("X-Naver-Client-Secret", secret)
					    .asString();
				
				Map<String, Object> mainBody = gson.fromJson(response.getBody(), Map.class);
				List<Map<String, String>> items = (List<Map<String,String>>) mainBody.get("items");
				Connection conn = new JDBCTemplate().getConnection();

				totalCnt = (int) Double.parseDouble(String.valueOf(mainBody.get("total")));
				
				System.out.println("totalCnt : " + totalCnt);
				
				try {
					new SchedulerService().insertProductInfo(conn, items);
					JDBCTemplate.commit(conn);
				} catch(Exception e) {
					e.printStackTrace();
					JDBCTemplate.rollback(conn);
				} finally {
					JDBCTemplate.close(conn);
				}
				
				System.out.println("-------------------------END-----------------------------------");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
 