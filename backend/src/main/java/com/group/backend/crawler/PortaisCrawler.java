package com.group.backend.crawler;

public class PortaisCrawler {

    public static void main(String[] args) {
        String exampleUrl = "https://thenewscc.beehiiv.com/";

        ControllerCrawler controllerCrawler = new ControllerCrawler();

        try {
            controllerCrawler.startCrawlWithSeed(exampleUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}