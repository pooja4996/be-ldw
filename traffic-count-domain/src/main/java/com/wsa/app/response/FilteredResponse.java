package com.wsa.app.response;

import java.util.List;

public class FilteredResponse {
	private List<LockDashboardResponse> lockList;
	private StatisticsResponse chartsData;

	public List<LockDashboardResponse> getLockList() {
		return lockList;
	}

	public void setLockList(List<LockDashboardResponse> lockList) {
		this.lockList = lockList;
	}

	public StatisticsResponse getChartsData() {
		return chartsData;
	}

	public void setChartsData(StatisticsResponse chartsData) {
		this.chartsData = chartsData;
	}

}
