package com.manduScheduler.main.common;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.compress.utils.Lists;

public class Utils {

	public static String paramToQueryString(Map<String, String> paramMap){
		List<NameValuePair> params = Lists.newArrayList();
		if(paramMap != null){
			for(Entry<String, String> paramEntry : paramMap.entrySet()){
				Object value = paramEntry.getValue();
				if(value != null){
					params.add(new BasicNameValuePair(paramEntry.getKey(), value.toString()));
				}
			}
		}
		return URLEncodedUtils.format(params, "UTF-8");
	}
	
}
