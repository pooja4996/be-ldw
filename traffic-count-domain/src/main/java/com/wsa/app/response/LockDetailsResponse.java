package com.wsa.app.response;

public class LockDetailsResponse {

	private String id;
	private String lockName;
	private Double longitude;
	private Double latitude;
	private String adminOffice;
	private String secondLevelAdmin;
	private String waterway;
	private Long year;
	private String imageUrl;
	private String imageOwner;
	private String imageLicence;
	private String webcamUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAdminOffice() {
		return adminOffice;
	}

	public void setAdminOffice(String adminOffice) {
		this.adminOffice = adminOffice;
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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getWebcamUrl() {
		return webcamUrl;
	}

	public void setWebcamUrl(String webcamUrl) {
		this.webcamUrl = webcamUrl;
	}

}
