package com.group.backend.cron;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class ApiConsumer {
	public static JSONObject getData(String url) {
		
		RestTemplate restTemplate = new RestTemplate();
		String data = restTemplate.getForObject(url, String.class);
		return new JSONObject(data);
	}
}
