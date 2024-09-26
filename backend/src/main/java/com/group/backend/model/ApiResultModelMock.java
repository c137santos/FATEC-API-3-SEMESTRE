package com.group.backend.model;

import java.time.LocalDateTime;

import org.json.JSONObject;
public class ApiResultModelMock {
	static Integer idCounter = 0;

	public Long id;
	public Long apiId;
	public LocalDateTime date;
	public JSONObject payload;

	public ApiResultModelMock(Long apiId, JSONObject payload) {
		this.id = (long) idCounter++;
		this.apiId = apiId;
		this.date = LocalDateTime.now();
		this.payload = payload;
	}
}
