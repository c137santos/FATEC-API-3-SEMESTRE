package com.group.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.backend.cron.CronApi;
import com.group.backend.cron.CronCrawler;

@RestController
@RequestMapping("cron")
public class CronController {
	public final CronApi cronApi;
	public final CronCrawler cronCrawler;

	public CronController(CronApi cronApi, CronCrawler cronCrawler) {
		this.cronApi = cronApi;
		this.cronCrawler = cronCrawler;
	}

	@PostMapping("/api/diaria")
	void triggerAPIDaily () {
		cronApi.dailyAPIRegisterSchedule();
	}

	@PostMapping("/api/semanal")
	void triggerAPIWeekly () {
		cronApi.weeklyAPIRegisterSchedule();
	}
	@PostMapping("/api/mensal")
	void triggerAPIMonthly () {
		cronApi.monthlyAPIRegisterSchedule();
	}

	@PostMapping("/crawler/diaria")
	void triggerCrawlerDaily () {
		cronCrawler.dailyCrawlerRegisterSchedule();
	}

	@PostMapping("/crawler/semanal")
	void triggerCrawlerWeekly () {
		cronCrawler.weeklyCrawlerRegisterSchedule();
	}
	@PostMapping("/crawler/mensal")
	void triggerCrawlerMonthly () {
		cronCrawler.monthlyCrawlerRegisterSchedule();
	}}