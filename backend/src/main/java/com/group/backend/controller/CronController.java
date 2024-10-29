package com.group.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.cron.CronApi;

@RestController
@RequestMapping("cron")
public class CronController {
	public final CronApi cron;

	public CronController(CronApi cron) {
		this.cron = cron;
	}

	@PostMapping("diaria")
	void triggerDaily () {
		cron.dailyAPIRegisterSchedule();
	}

	@PostMapping("semanal")
	void triggerWeekly () {
		cron.weeklyAPIRegisterSchedule();
	}
	@PostMapping("mensal")
	void triggerMonthly () {
		cron.monthlyAPIRegisterSchedule();
	}
}