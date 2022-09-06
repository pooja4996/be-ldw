package com.wsa.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.exception.handler.GlobalExceptionHandler;
import com.wsa.app.request.FilterRequest;
import com.wsa.app.request.StatsOnDragRequest;
import com.wsa.app.response.FilterCategoryData;
import com.wsa.app.response.FilteredResponse;
import com.wsa.app.response.LockDashboardResponse;
import com.wsa.app.response.LockDetailsResponse;
import com.wsa.app.response.LockTrafficResponse;
import com.wsa.app.response.StatisticsResponse;
import com.wsa.app.service.lock.LockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MapInfoController extends GlobalExceptionHandler {

	@Autowired
	private LockService lockService;

	@GetMapping("/map/filter")
	public ResponseEntity<FilterCategoryData> getFilterCategoryData(
			@RequestParam(name = "type", required = false) String type) {
		return new ResponseEntity<>(lockService.getFilterCategoryInfo(type), HttpStatus.OK);
	}

	@GetMapping("/locks")
	public ResponseEntity<List<LockDashboardResponse>> searchLocks(@RequestParam(name = "search") String keyword) {
		return new ResponseEntity<>(lockService.searchLock(keyword), HttpStatus.OK);
	}

	@GetMapping("/locks/dashboard")
	public ResponseEntity<List<LockDashboardResponse>> getLockDashboradData() {
		return new ResponseEntity<>(lockService.getLockDashBoard(), HttpStatus.OK);
	}

	@PostMapping("/locks/filter")
	public ResponseEntity<FilteredResponse> getFilterResult(@RequestBody FilterRequest request) {
		return new ResponseEntity<>(lockService.getFilterResult(request), HttpStatus.OK);

	}

	@GetMapping("/getLocks")
	public ResponseEntity<LockDetailsResponse> getAllLocks(@RequestParam(name = "lockId") String lockId)
			throws NotFoundException {
		return new ResponseEntity<>(lockService.getLockDetails(lockId), HttpStatus.OK);
	}
	
	@PostMapping("/locks/{lockId}/traffic-overview")
	public ResponseEntity<List<LockTrafficResponse>> getTrafficDetails(@PathVariable("lockId")String lockId,@RequestBody FilterRequest request) {
		return new ResponseEntity<>(lockService.getLockTrafficDetails(request,lockId), HttpStatus.OK);
	}
	
	@PostMapping("/getStatsOnDrag")
	public ResponseEntity<StatisticsResponse> getStatsByDrag(@RequestBody StatsOnDragRequest request ){
		return new ResponseEntity<StatisticsResponse>(lockService.getStatsByDrag(request),HttpStatus.OK);
	}
}
