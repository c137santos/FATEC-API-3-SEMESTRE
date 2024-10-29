package com.group.backend.cron;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.backend.domain.ResultApiRepository;
import com.group.backend.entity.ApiPublica;
import com.group.backend.entity.ResultApi;

public class ApiConsumer {

	ResultApiRepository resultApiRepository;
	public ApiConsumer(ResultApiRepository resultApiRepository) {
		this.resultApiRepository = resultApiRepository;
	}

	public static String getData(String url) {
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			String data = restTemplate.getForObject(url, String.class);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	public ResultApi saveNew (ApiPublica api, String data) {
		ResultApi result = new ResultApi();
		result.setApiPublica(api);
		result.setResData(LocalDate.now());
		result.setResPayload(data);
		return resultApiRepository.save(result);
	}

	public void apiRegister(ArrayList<ApiPublica> apiList) {
		for (ApiPublica api : apiList) {
			String data = ApiConsumer.getData(api.getUrl());
			if (data == null) continue;

			ResultApi lastResult = resultApiRepository.getLastFromResId(api.getId());
			if(lastResult == null) {
				saveNew(api, data);
				continue;
			}

			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode dataNode = mapper.readTree(data);
				JsonNode lastPayloadNode = mapper.readTree(lastResult.getResPayload());
				if (!dataNode.equals(lastPayloadNode)) {

					saveNew(api, data);
	
					System.out.println("Saving new data! Updates - " + api.getNome());
				}
			} catch (Exception e) {}

			
		}
	}

}
