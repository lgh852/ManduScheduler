package com.manduScheduler.main.job;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Component
public class ShoppingGetNaverInfoJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			Unirest.setTimeouts(0, 0);
			
			String baseUrl = "https://openapi.naver.com/v1/search/shop.json";
			String queryString = "?query="+URLEncoder.encode("비비고왕교자")+"&sort=asc&display=100";
			
			HttpResponse<String> response = Unirest.get(baseUrl + queryString)
				    .header("X-Naver-Client-Id", "RsdNOQG91qKNC7RveZ1S") // 여기 x-naver-client-id, x-naver-client-secret 은 property(application.yml)에 두고 쓰는게 좋을거같은데 의견은 ?
				    .header("X-Naver-Client-Secret", "58uVz9H6ap")
				    .asString();
			
			System.out.println(response.getBody()); // 응답결과가 String 형태로 날라오는듯
			
			Gson gson = new Gson();
			
			Map<String, Object> mainBody = gson.fromJson(response.getBody(), Map.class);
			
			List<Map<String, Object>> items = (List<Map<String,Object>>) mainBody.get("items");
			
			System.out.println(items.get(0)); // 이다음 부터는 이제 db에 데이터 추려서 db에 적재하는 용도로 사용하면 될듯
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
