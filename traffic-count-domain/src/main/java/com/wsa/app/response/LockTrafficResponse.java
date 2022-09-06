package com.wsa.app.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LockTrafficResponse {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime shippedTime;
	private String direction;
	private String category;

	public LocalDateTime getShippedTime() {
		return shippedTime;
	}

	public void setShippedTime(LocalDateTime shippedTime) {
		this.shippedTime = shippedTime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
