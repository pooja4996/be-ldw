package com.wsa.app.service.lock;

import java.util.List;

import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.request.FilterRequest;
import com.wsa.app.request.StatsOnDragRequest;
import com.wsa.app.response.FilterCategoryData;
import com.wsa.app.response.FilteredResponse;
import com.wsa.app.response.LockDashboardResponse;
import com.wsa.app.response.LockDetailsResponse;
import com.wsa.app.response.LockTrafficResponse;
import com.wsa.app.response.StatisticsResponse;

public interface LockService {

	public List<LockDashboardResponse> getLockDashBoard();
	public FilterCategoryData getFilterCategoryInfo(String type);
	public List<LockDashboardResponse> searchLock(String keyword);
	public FilteredResponse getFilterResult(FilterRequest request);
	public LockDetailsResponse getLockDetails(String lockId) throws NotFoundException;
	public List<LockTrafficResponse> getLockTrafficDetails(FilterRequest request,String lockId);
	public StatisticsResponse getStatsByDrag(StatsOnDragRequest request);

}
