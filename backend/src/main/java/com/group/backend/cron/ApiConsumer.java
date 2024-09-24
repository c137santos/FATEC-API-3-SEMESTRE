package com.group.backend.cron;

import org.springframework.web.client.RestTemplate;

public class ApiConsumer {
	public static String getData(String url) {
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			String data = restTemplate.getForObject(url, String.class);
			return data;
		} catch (Exception e) {
			return null;
		}
	}
}
