package com.group.backend.cron;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.group.backend.domain.ApiPublicaRepository;
import com.group.backend.domain.ResultApiRepository;
@Component
public class CronApi {
	private final ApiPublicaRepository apiPublicaRepository;
	ApiConsumer apiConsumer;

	static String DAY_TIME = "PT5.0S";
	static String WEEK_TIME = "PT10.0S";
	static String MONTH_TIME = "PT20.0S";

	public CronApi(ApiPublicaRepository apiPublicaRepository, ResultApiRepository resultApiRepository) {
		this.apiPublicaRepository = apiPublicaRepository;
		apiConsumer = new ApiConsumer(resultApiRepository);
	}

	@Scheduled(cron="0 0 * * * ?")
    @EventListener(ApplicationReadyEvent.class)
	public void dailyAPIRegisterSchedule()
	{
		apiConsumer.apiRegister(apiPublicaRepository.getDailyList());
	}

	@Scheduled(cron="0 0 0 * * MON")
    @EventListener(ApplicationReadyEvent.class)
	public void weeklyAPIRegisterSchedule() {
		apiConsumer.apiRegister(apiPublicaRepository.getWeeklyList());
	}

	@Scheduled(cron = "0 0 1 * * ?")
    @EventListener(ApplicationReadyEvent.class)
	public void monthlyAPIRegisterSchedule() {
		apiConsumer.apiRegister(apiPublicaRepository.getMonthlyList());
	}
}
