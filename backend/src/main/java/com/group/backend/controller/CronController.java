package com.group.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.cron.Cron;

@RestController
@RequestMapping("cron")
public class CronController {
	public final Cron cron;

	public CronController(Cron cron) {
		this.cron = cron;
	}

	@PostMapping("diaria")
	void triggerDaily () {
		cron.dailyAPIRegisterSchedule();
	}

	@PostMapping("semanal")
	void triggerWeekly () {
		cron.dailyAPIRegisterSchedule();
	}
	@PostMapping("anual")
	void triggerMonthly () {
		cron.dailyAPIRegisterSchedule();
	}
}