package com.wsa.app.response;

import java.util.List;
import java.util.Set;

public class FilterCategoryData {
	private List<LockNameAndId> locks;
	private Set<String> adminOffice;
	private Set<String> seconLevelAdminOffice;
	private Set<String> waterway;
	private List<String> shipCategory;

	public List<LockNameAndId> getLocks() {
		return locks;
	}

	public void setLocks(List<LockNameAndId> locks) {
		this.locks = locks;
	}

	public Set<String> getAdminOffice() {
		return adminOffice;
	}

	public void setAdminOffice(Set<String> adminOffice) {
		this.adminOffice = adminOffice;
	}

	public Set<String> getSeconLevelAdminOffice() {
		return seconLevelAdminOffice;
	}

	public void setSeconLevelAdminOffice(Set<String> seconLevelAdminOffice) {
		this.seconLevelAdminOffice = seconLevelAdminOffice;
	}

	public Set<String> getWaterway() {
		return waterway;
	}

	public void setWaterway(Set<String> waterway) {
		this.waterway = waterway;
	}

	public List<String> getShipCategory() {
		return shipCategory;
	}

	public void setShipCategory(List<String> shipCategory) {
		this.shipCategory = shipCategory;
	}

}
