package com.wsa.app.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FilterRequest {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
	private List<String> category;
	private String direction;
	private List<String> adminOffice;
	private List<String> waterway;
	private List<String> secondLevelOffice;

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public List<String> getAdminOffice() {
		return adminOffice;
	}

	public void setAdminOffice(List<String> adminOffice) {
		this.adminOffice = adminOffice;
	}

	public List<String> getWaterway() {
		return waterway;
	}

	public void setWaterway(List<String> waterway) {
		this.waterway = waterway;
	}

	public List<String> getSecondLevelOffice() {
		return secondLevelOffice;
	}

	public void setSecondLevelOffice(List<String> secondLevelOffice) {
		this.secondLevelOffice = secondLevelOffice;
	}

}
