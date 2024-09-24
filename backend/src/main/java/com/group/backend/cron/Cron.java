package com.group.backend.cron;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.backend.domain.ApiPublicaRepository;
import com.group.backend.entity.ApiPublica;
import com.group.backend.entity.ResultApi;
import com.group.backend.domain.ResultApiRepository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cron {
	private final ApiPublicaRepository apiPublicaRepository;
	private final ResultApiRepository resultApiRepository;


	static String DAY_TIME = "PT5.0S";
	static String WEEK_TIME = "PT10.0S";
	static String MONTH_TIME = "PT20.0S";

	public Cron(ApiPublicaRepository apiPublicaRepository, ResultApiRepository resultApiRepository) {
		this.apiPublicaRepository = apiPublicaRepository;
		this.resultApiRepository = resultApiRepository;
		dailyAPIRegisterSchedule();
		weeklyAPIRegisterSchedule();
		monthlyAPIRegisterSchedule();
	}

	public ResultApi saveNew (ApiPublica api, String data) {
		ResultApi result = new ResultApi();
		result.setApiPublica(api);
		result.setResData(new Date());
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

	@Scheduled(cron="0 0 * * * ?")
	public void dailyAPIRegisterSchedule() {
		apiRegister(apiPublicaRepository.getDailyList());
	}

	@Scheduled(cron="0 0 0 * * MON")
	public void weeklyAPIRegisterSchedule() {
		apiRegister(apiPublicaRepository.getWeeklyList());
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void monthlyAPIRegisterSchedule() {
		apiRegister(apiPublicaRepository.getMonthlyList());
	}
}
