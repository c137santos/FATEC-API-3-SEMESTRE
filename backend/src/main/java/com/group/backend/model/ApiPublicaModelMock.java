package com.group.backend.model;

import java.util.ArrayList;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class ApiPublicaModelMock {
	public Long id;
	public String name;
	public String frequency;
	public String url;
	public LocalDateTime lastCraw;
	public Boolean active;
	ArrayList<ApiResultModelMock> mockResultList = new ArrayList<>();

	public ApiPublicaModelMock(Long id, String name, String frequency, String url) {
		this.id = id;
		this.name = name;
		this.frequency = frequency;
		this.url = url;
		this.lastCraw = LocalDateTime.now();
		this.active = true;
	}

	static ApiPublicaModelMock dailyMock = new ApiPublicaModelMock((long) 1, "Daily Mock", "diaria", "http://localhost:3000");
	static ApiPublicaModelMock weeklyMock = new ApiPublicaModelMock((long) 1, "Weekly Mock", "semanal", "http://localhost:3000");
	static ApiPublicaModelMock monthlyMock = new ApiPublicaModelMock((long) 1, "Monthly Mock", "mensal", "http://localhost:3000");

	public static ArrayList<ApiPublicaModelMock> getDailyList() {
		ArrayList<ApiPublicaModelMock> list = new ArrayList<ApiPublicaModelMock>();
		
		list.add(dailyMock);

		return list;
	}

	public static ArrayList<ApiPublicaModelMock> getWeeklyList() {
		ArrayList<ApiPublicaModelMock> list = new ArrayList<ApiPublicaModelMock>();
		
		list.add(weeklyMock);

		return list;
	}

	public static ArrayList<ApiPublicaModelMock> getMonthlyList() {
		ArrayList<ApiPublicaModelMock> list = new ArrayList<ApiPublicaModelMock>();
		
		list.add(monthlyMock);

		return list;
	}

	public void storeResult(JSONObject data) {
		mockResultList.add(new ApiResultModelMock(this.id, data));
		mockResultList.sort((a, b) -> b.date.compareTo(a.date));
	}

	public ApiResultModelMock getLastResult() {
		if(mockResultList.size() > 0) {
		}
		for (Integer i = 0; i < mockResultList.size();) {
			ApiResultModelMock result = mockResultList.get(i);
			return result;
		}
		return null;
	}
}
