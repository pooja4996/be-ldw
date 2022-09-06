package com.wsa.app.service.lock;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsa.app.constant.CommonConstatnt;
import com.wsa.app.constant.FilterCategories;
import com.wsa.app.constant.ShipCategory;
import com.wsa.app.domain.Lock;
import com.wsa.app.domain.Ship;
import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.repository.LockRepository;
import com.wsa.app.repository.ShipRepository;
import com.wsa.app.request.FilterRequest;
import com.wsa.app.request.StatsOnDragRequest;
import com.wsa.app.response.FilterCategoryData;
import com.wsa.app.response.FilteredResponse;
import com.wsa.app.response.LockDashboardResponse;
import com.wsa.app.response.LockDetailsResponse;
import com.wsa.app.response.LockInfoDto;
import com.wsa.app.response.LockNameAndId;
import com.wsa.app.response.LockTrafficResponse;
import com.wsa.app.response.ShipCategoryCount;
import com.wsa.app.response.StatisticsResponse;

@Service
public class LockServiceImpl implements LockService {

	@Autowired
	private LockRepository lockRepository;

	@Autowired
	private ShipRepository shipRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<LockDashboardResponse> getLockDashBoard() {
		String pattern = "dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String day = simpleDateFormat.format(new Date());
		if (day.equals("31")) {
			day = "30";
		}
		String date = "2021-11-" + day;
		String time1 = "00:00:00";
		String time2 = "23:59:59";
		List<Ship> ships = shipRepository.findAllByShippedTimeBetween(
				LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time1)),
				LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time2)));
		HashMap<String, LockDashboardResponse> getShipsAtLock = getShipsAtLocks(ships);
		return setDashboardResponse(getShipsAtLock);
	}

	private List<LockDashboardResponse> setDashboardResponse(HashMap<String, LockDashboardResponse> getShipsAtLock) {
		List<LockDashboardResponse> response = new ArrayList<>();
		for (Map.Entry<String, LockDashboardResponse> i : getShipsAtLock.entrySet()) {
			response.add(i.getValue());
		}
		return response;
	}

	private HashMap<String, LockDashboardResponse> getShipsAtLocks(List<Ship> ships) {
		HashMap<String, LockDashboardResponse> map = new HashMap<>();
		for (Ship i : ships) {
			if (map.containsKey(i.getLock().getId())) {
				LockDashboardResponse lockResponse = map.get(i.getLock().getId());
				if (i.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION)) {
					lockResponse.setUpward(lockResponse.getUpward() + 1);
				} else {
					lockResponse.setDownward(lockResponse.getDownward() + 1);
				}
				lockResponse.setTotal(lockResponse.getTotal() + 1);

				map.put(i.getLock().getId(), duplicateLockCountIncrement(i, lockResponse));
			} else {
				map.put(i.getLock().getId(), initAsFirstResponse(i));
			}
		}
		return map;

	}

	private LockDashboardResponse duplicateLockCountIncrement(Ship ship, LockDashboardResponse response) {
		int downward = 0;
		int upward = 0;
		if (ship.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION)) {
			upward++;
		} else {
			downward++;
		}
		int total = downward + upward;
		List<ShipCategoryCount> shipCategoriesCount = response.getShipCategoryCount();
		ShipCategoryCount count = shipCategoriesCount.stream()
				.filter(obj -> obj.getCategory().equals(ship.getCategory())).findFirst().orElse(null);
		if (count != null) {
			int index = response.getShipCategoryCount().indexOf(count);
			count.setDownward(count.getDownward() + downward);
			count.setUpward(count.getUpward() + upward);
			count.setTotal(count.getTotal() + total);
			shipCategoriesCount.set(index, count);
		}
		response.setShipCategoryCount(shipCategoriesCount);
		return response;
	}

	private LockDashboardResponse initAsFirstResponse(Ship ship) {
		LockDashboardResponse lockResponse = new LockDashboardResponse();
		Lock lock = ship.getLock();
		lockResponse.setLockId(lock.getId());
		lockResponse.setLockName(lock.getLockName());
		lockResponse.setLatitude(lock.getLatitude());
		lockResponse.setLongitude(lock.getLongitude());
		lockResponse.setAdminOffice(lock.getAdminOffice());
		lockResponse.setWaterway(lock.getWaterway());
		lockResponse.setSecondLevelAdmin(lock.getSecondLevelAdmin());
		lockResponse.setYear(lock.getYear());
		lockResponse.setImageUrl(lock.getImageUrl());
		lockResponse.setImageLicence(lock.getImageLicence());
		lockResponse.setImageOwner(lock.getImageOwner());
		ShipCategoryCount frachtschiff = new ShipCategoryCount(ShipCategory.Frachtschiff.name(), 0, 0, 0);
		ShipCategoryCount kleinfahrzeug = new ShipCategoryCount(ShipCategory.Kleinfahrzeug.name(), 0, 0, 0);
		ShipCategoryCount sportboot = new ShipCategoryCount(ShipCategory.Sportboot.name(), 0, 0, 0);
		if (ship.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION)) {
			lockResponse.setUpward(1);
			lockResponse.setDownward(0);
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Frachtschiff.name())) {
				frachtschiff.setUpward(lockResponse.getUpward());
				frachtschiff.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Kleinfahrzeug.name())) {
				kleinfahrzeug.setUpward(lockResponse.getUpward());
				kleinfahrzeug.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Sportboot.name())) {
				sportboot.setUpward(lockResponse.getUpward());
				sportboot.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
		} else {
			lockResponse.setDownward(1);
			lockResponse.setUpward(0);
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Frachtschiff.name())) {
				frachtschiff.setDownward(lockResponse.getDownward());
				frachtschiff.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Kleinfahrzeug.name())) {
				kleinfahrzeug.setDownward(lockResponse.getDownward());
				kleinfahrzeug.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
			if (ship.getCategory().equalsIgnoreCase(ShipCategory.Sportboot.name())) {
				sportboot.setDownward(lockResponse.getDownward());
				sportboot.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
			}
		}
		lockResponse.setTotal(lockResponse.getUpward() + lockResponse.getDownward());
		lockResponse.setShipCategoryCount(Arrays.asList(frachtschiff, kleinfahrzeug, sportboot));
		return lockResponse;
	}

	@Override
	public FilterCategoryData getFilterCategoryInfo(String type) {
		FilterCategoryData response = new FilterCategoryData();
		List<LockInfoDto> lockInfo = lockRepository.findLockFilterInfo();
		Set<String> adminOffices = new HashSet<>();
		Set<String> adminSecondLevelOffices = new HashSet<>();
		Set<String> waterWays = new HashSet<>();
		List<LockNameAndId> locks = new ArrayList<>();
		for (LockInfoDto obj : lockInfo) {
			adminOffices.add(obj.getAdminOffice());
			adminSecondLevelOffices.add(obj.getSecondLevelAdmin());
			waterWays.add(obj.getWaterway());
			locks.add(new LockNameAndId(obj.getId(), obj.getLockName()));
		}
		response.setAdminOffice(adminOffices);
		response.setLocks(locks);
		response.setSeconLevelAdminOffice(adminSecondLevelOffices);
		response.setWaterway(waterWays);
		if (type != null && type.equalsIgnoreCase(CommonConstatnt.FILTER_TYP)) {
			response.setShipCategory(Arrays.asList(ShipCategory.Frachtschiff.name(), ShipCategory.Kleinfahrzeug.name(),
					ShipCategory.Sportboot.name()));
			response.getLocks().clear();
		}
		return response;
	}

	@Override
	public List<LockDashboardResponse> searchLock(String keyword) {
		List<Lock> locks = lockRepository.search(keyword);
		return locks.stream().map(this::of).collect(Collectors.toList());
	}

	private LockDashboardResponse of(Lock lock) {
		LockDashboardResponse result = null;
		if (lock != null) {
			int up = 0;
			int down = 0;
			result = new LockDashboardResponse();
			result.setLockId(lock.getId());
			result.setLockName(lock.getLockName());
			result.setAdminOffice(lock.getAdminOffice());
			result.setWaterway(lock.getWaterway());
			result.setSecondLevelAdmin(lock.getSecondLevelAdmin());
			result.setImageUrl(lock.getImageUrl());
			result.setLatitude(lock.getLatitude());
			result.setLongitude(lock.getLongitude());
			result.setYear(lock.getYear());
			result.setImageLicence(lock.getImageLicence());
			result.setImageOwner(lock.getImageOwner());
			List<Ship> ships = lock.getShips();
			down = ships.stream().filter(obj -> obj.getDirection().equalsIgnoreCase(CommonConstatnt.DOWN_DIRECTION))
					.collect(Collectors.toList()).size();
			up = ships.stream().filter(obj -> obj.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION))
					.collect(Collectors.toList()).size();
			result.setUpward(up);
			result.setDownward(down);
			result.setTotal(up + down);

			down = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Frachtschiff.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.DOWN_DIRECTION))
					.collect(Collectors.toList()).size();
			up = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Frachtschiff.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION))
					.collect(Collectors.toList()).size();
			ShipCategoryCount frachtschiff = new ShipCategoryCount(ShipCategory.Frachtschiff.name(), up, down,
					up + down);

			down = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Kleinfahrzeug.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.DOWN_DIRECTION))
					.collect(Collectors.toList()).size();
			up = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Kleinfahrzeug.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION))
					.collect(Collectors.toList()).size();
			ShipCategoryCount kleinfahrzeug = new ShipCategoryCount(ShipCategory.Kleinfahrzeug.name(), up, down,
					up + down);

			down = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Sportboot.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.DOWN_DIRECTION))
					.collect(Collectors.toList()).size();
			up = ships.stream()
					.filter(obj -> obj.getCategory().equalsIgnoreCase(ShipCategory.Sportboot.name())
							&& obj.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION))
					.collect(Collectors.toList()).size();
			ShipCategoryCount sportboot = new ShipCategoryCount(ShipCategory.Sportboot.name(), up, down, up + down);

			result.setShipCategoryCount(Arrays.asList(frachtschiff, sportboot, kleinfahrzeug));

		}
		return result;
	}

	@Override
	public FilteredResponse getFilterResult(FilterRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ship> criteriaQuery = criteriaBuilder.createQuery(Ship.class);
		Root<Ship> shipRoot = criteriaQuery.from(Ship.class);
		Predicate predicate =criteriaBuilder.and(getPrredicate(request, shipRoot, criteriaBuilder, false, null).toArray(new Predicate[0]));
		criteriaQuery.where(predicate);
		List<Ship> ships = entityManager.createQuery(criteriaQuery).getResultList();
		HashMap<String, LockDashboardResponse> getShipsAtLock = getShipsAtLocks(ships);
		FilteredResponse filteredResponse = new FilteredResponse();
		filteredResponse.setLockList(setDashboardResponse(getShipsAtLock));
		filteredResponse.setChartsData(getStats(ships));
		return filteredResponse;
	}

	private List<Predicate> getPrredicate(FilterRequest request, Root<Ship> shipRoot, CriteriaBuilder criteriaBuilder,
			boolean isTrafficOverview, String lockId) {
		List<Predicate> predicates = new ArrayList<>();
		LocalDateTime endDate = request.getEndDate();
		if (endDate == null) {
			endDate = LocalDateTime.now();
			request.setEndDate(endDate);
		}
		if (isTrafficOverview && Objects.nonNull(lockId)) {

			predicates.add(criteriaBuilder.equal(shipRoot.get("lock").get("id"), lockId));
		}

		// Applied StartDate and endDate filter
		if (Objects.nonNull(request.getStartDate())) {
			predicates.add(criteriaBuilder.between(shipRoot.get(FilterCategories.shippedTime.name()),
					request.getStartDate(), request.getEndDate()));
		}

		// Applied Ship category filter
		if (request.getCategory() != null && !request.getCategory().isEmpty()) {
			predicates.add(inClauseBuild(shipRoot, criteriaBuilder, request.getCategory(),
					FilterCategories.category.name(), false));
		}
		// Applied Ship Direction filter
		if (Objects.nonNull(request.getDirection())) {
			predicates.add(
					criteriaBuilder.equal(shipRoot.get(FilterCategories.direction.name()), request.getDirection()));
		}
		// Applied Lock waterway filter
		if (request.getWaterway() != null && !request.getWaterway().isEmpty()) {
			predicates.add(inClauseBuild(shipRoot, criteriaBuilder, request.getWaterway(),
					FilterCategories.waterway.name(), true));
		}
		// Applied Lock Admin office filter
		if (request.getAdminOffice() != null && !request.getAdminOffice().isEmpty()) {
			predicates.add(inClauseBuild(shipRoot, criteriaBuilder, request.getAdminOffice(),
					FilterCategories.adminOffice.name(), true));
		}
		// Applied Lock 2nd level Admin office filter
		if (request.getSecondLevelOffice() != null && !request.getSecondLevelOffice().isEmpty()) {
			predicates.add(inClauseBuild(shipRoot, criteriaBuilder, request.getSecondLevelOffice(),
					FilterCategories.secondLevelAdmin.name(), true));
		}
		return predicates;
		//return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private In<String> inClauseBuild(Root<Ship> shipRoot, CriteriaBuilder criteriaBuilder, List<String> values,
			String property, boolean isLockProperty) {
		In<String> inClause = null;
		if (isLockProperty) {
			inClause = criteriaBuilder.in(shipRoot.get("lock").get(property));
		} else {
			inClause = criteriaBuilder.in(shipRoot.get(property));
		}
		for (String str : values) {
			inClause.value(str);
		}
		return inClause;
	}

	private StatisticsResponse getStats(List<Ship> filteredShips) {
		List<ShipCategoryCount> tree = new ArrayList<>();
		HashMap<String, HashMap<String, Integer>> line = new HashMap<>();
		long up = filteredShips.stream().filter(s -> s.getDirection().equalsIgnoreCase(CommonConstatnt.UP_DIRECTION))
				.count();
		long down = filteredShips.stream()
				.filter(s -> s.getDirection().equalsIgnoreCase(CommonConstatnt.DOWN_DIRECTION)).count();

		Arrays.asList(ShipCategory.values()).forEach(cat -> {
			HashMap<String, Integer> catline = new HashMap<>();
			int totalCountByships = (int) filteredShips.stream()
					.filter(sbyc -> sbyc.getCategory().equalsIgnoreCase(cat.toString())).count();
			
			ShipCategoryCount scnt = new ShipCategoryCount(cat.toString(), 0, 0, totalCountByships);
			tree.add(scnt);

			Map<LocalDate, List<Ship>> entries = filteredShips.stream()
					.filter(sbyc -> sbyc.getCategory().equalsIgnoreCase(cat.toString()))
					.collect(Collectors.groupingBy(ship -> ship.getShippedTime().toLocalDate()));
			for (Map.Entry<LocalDate, List<Ship>> entry : entries.entrySet()) {
				catline.put(entry.getKey().toString(), entry.getValue().size());
			}

			line.put(cat.toString(), catline);

		});
		return new StatisticsResponse(up, down, tree, line);
	}

	@Override
	public LockDetailsResponse getLockDetails(String lockId) throws NotFoundException {
		Lock lock = lockRepository.findById(lockId).orElse(null);
		if (lock == null) {
			throw new NotFoundException();
		}
		return convertIntoDetails(lock);
	}

	private LockDetailsResponse convertIntoDetails(Lock lock) {
		LockDetailsResponse result = null;
		if (lock != null) {
			result = new LockDetailsResponse();
			result.setId(lock.getId());
			result.setLockName(lock.getLockName());
			result.setAdminOffice(lock.getAdminOffice());
			result.setWaterway(lock.getWaterway());
			result.setSecondLevelAdmin(lock.getSecondLevelAdmin());
			result.setImageUrl(lock.getImageUrl());
			result.setLatitude(lock.getLatitude());
			result.setLongitude(lock.getLongitude());
			result.setYear(lock.getYear());
			result.setImageLicence(lock.getImageLicence());
			result.setImageOwner(lock.getImageOwner());
			result.setWebcamUrl(lock.getWebcamUrl());
		}
		return result;
	}

	@Override
	public List<LockTrafficResponse> getLockTrafficDetails(FilterRequest request, String lockId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ship> criteriaQuery = criteriaBuilder.createQuery(Ship.class);
		Root<Ship> shipRoot = criteriaQuery.from(Ship.class);
		Predicate predicate = criteriaBuilder.and(getPrredicate(request, shipRoot, criteriaBuilder, true, lockId).toArray(new Predicate[0])); 
		criteriaQuery.where(predicate);
		List<Ship> ships = entityManager.createQuery(criteriaQuery).getResultList();
		return ships.stream().map(this::of).collect(Collectors.toList());
	}

	private LockTrafficResponse of(Ship ship) {
		LockTrafficResponse response = null;
		if (ship != null) {
			response = new LockTrafficResponse();
			response.setCategory(ship.getCategory());
			response.setDirection(ship.getDirection());
			response.setShippedTime(ship.getShippedTime());
		}
		return response;
	}

	@Override
	public StatisticsResponse getStatsByDrag(StatsOnDragRequest request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ship> criteriaQuery = criteriaBuilder.createQuery(Ship.class);
		Root<Ship> shipRoot = criteriaQuery.from(Ship.class);
		List<Predicate> predicates =getPrredicate(request.getFilterRequest(), shipRoot, criteriaBuilder, false, null);
		if ( request.getLocksIds()!= null && ! request.getLocksIds().isEmpty()) {
			predicates.add(inClauseBuild(shipRoot, criteriaBuilder, request.getLocksIds(),
					FilterCategories.id.name(), true));
		}
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
		List<Ship> ships = entityManager.createQuery(criteriaQuery).getResultList();
		return getStats(ships);
	}
}