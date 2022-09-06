package com.wsa.app.response;

import java.util.List;

public class LockDashboardResponse {

	private String lockId;
	private String lockName;
	private Double latitude;
	private Double longitude;
	private String adminOffice;
	private String secondLevelAdmin;
	private String waterway;
	private String imageUrl;
	private String imageOwner;
	private String imageLicence;
	private long year;
	private int upward;
	private int downward;
	private int total;
	private List<ShipCategoryCount> shipCategoryCount;

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAdminOffice() {
		return adminOffice;
	}

	public void setAdminOffice(String adminOffice) {
		this.adminOffice = adminOffice;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public int getUpward() {
		return upward;
	}

	public void setUpward(int up) {
		this.upward = up;
	}

	public int getDownward() {
		return downward;
	}

	public void setDownward(int down) {
		this.downward = down;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<ShipCategoryCount> getShipCategoryCount() {
		return shipCategoryCount;
	}

	public void setShipCategoryCount(List<ShipCategoryCount> shipCategoryCount) {
		this.shipCategoryCount = shipCategoryCount;
	}

	public String getImageOwner() {
		return imageOwner;
	}

	public void setImageOwner(String imageOwner) {
		this.imageOwner = imageOwner;
	}

	public String getImageLicence() {
		return imageLicence;
	}

	public void setImageLicence(String imageLicence) {
		this.imageLicence = imageLicence;
	}

	public String getSecondLevelAdmin() {
		return secondLevelAdmin;
	}

	public void setSecondLevelAdmin(String secondLevelAdmin) {
		this.secondLevelAdmin = secondLevelAdmin;
	}

	public String getWaterway() {
		return waterway;
	}

	public void setWaterway(String waterway) {
		this.waterway = waterway;
	}

}
