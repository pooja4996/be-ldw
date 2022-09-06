package com.wsa.app.request;

import java.util.List;

public class StatsOnDragRequest {
	private List<String> locksIds;
	private FilterRequest filterRequest;
	public List<String> getLocksIds() {
		return locksIds;
	}
	public void setLocksIds(List<String> locksIds) {
		this.locksIds = locksIds;
	}
	public FilterRequest getFilterRequest() {
		return filterRequest;
	}
	public void setFilterRequest(FilterRequest filterRequest) {
		this.filterRequest = filterRequest;
	}
}
