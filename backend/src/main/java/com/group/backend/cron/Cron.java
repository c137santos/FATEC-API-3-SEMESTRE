package com.group.backend.cron;
import com.group.backend.model.ApiPublicaModelMock;
import com.group.backend.model.ApiResultModelMock;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cron {
	static String DAY_TIME = "PT1.0S";
	static String WEEK_TIME = "PT5.0S";
	static String MONTH_TIME = "PT10.0S";	
	public static void apiRegister(ArrayList<ApiPublicaModelMock> apiList) {
		for (ApiPublicaModelMock api : apiList) {
			JSONObject data = ApiConsumer.getData(api.url);
			ApiResultModelMock result = api.getLastResult();
			if (result == null || !(result.payload.toString().equals(data.toString()))) {
				api.storeResult(data);
				System.out.println("Saving new data! Updates - " + api.name + " - Updates: " + data.get("updates"));
			}
		}
	}

	@Scheduled(fixedRateString = "PT1.0S")
	public void dailyAPIRegisterSchedule() {
		apiRegister(ApiPublicaModelMock.getDailyList());
	}

	@Scheduled(fixedRateString = "PT5.0S")
	public void weeklyAPIRegisterSchedule() {
		apiRegister(ApiPublicaModelMock.getWeeklyList());
	}

	@Scheduled(fixedRateString = "PT10.0S")
	public void monthlyAPIRegister() {
		apiRegister(ApiPublicaModelMock.getMonthlyList());
	}
}
