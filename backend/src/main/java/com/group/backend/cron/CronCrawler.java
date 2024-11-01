package com.group.backend.cron;

import org.springframework.scheduling.annotation.Scheduled;

import com.group.backend.crawler.PortaisCrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CronCrawler {
    PortaisCrawler portaisCrawler;

	static String DAY_TIME = "PT5.0S";
	static String WEEK_TIME = "PT10.0S";
	static String MONTH_TIME = "PT20.0S";

	@Autowired
	public CronCrawler(PortaisCrawler portaisCrawler) {
		this.portaisCrawler = portaisCrawler;
	}

	@Scheduled(cron="0 0 * * * ?")
    @EventListener(ApplicationReadyEvent.class)
	public void dailyCrawlerRegisterSchedule() {
		portaisCrawler.startCrawlForFrequency("diariamente");
	}

	@Scheduled(cron="0 0 0 * * MON")
    @EventListener(ApplicationReadyEvent.class)
	public void weeklyCrawlerRegisterSchedule() {
		portaisCrawler.startCrawlForFrequency("semanalmente");
	}

	@Scheduled(cron = "0 0 1 * * ?")
    @EventListener(ApplicationReadyEvent.class)
	public void monthlyCrawlerRegisterSchedule() {
		portaisCrawler.startCrawlForFrequency("mensalmente");
	}
}
