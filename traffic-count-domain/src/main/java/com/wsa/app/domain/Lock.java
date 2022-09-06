package com.wsa.app.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "locks")
public class Lock {

	@Id
	@Column(name = "lock_id")
	private String id;

	@Column(name = "lock_name")
	private String lockName;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "admin_office")
	private String adminOffice;

	@Column(name = "second_level_admin")
	private String secondLevelAdmin;

	@Column(name = "waterway")
	private String waterway;

	@Column(name = "construction_year")
	private Long year;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "image_owner")
	private String imageOwner;

	@Column(name = "image_licence")
	private String imageLicence;

	@Column(name = "webcam_url")
	private String webcamUrl;

	@JsonIgnore
	@OneToMany(mappedBy = "lock", cascade = CascadeType.ALL)
	private List<Ship> ships;

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

	public String getWebcamUrl() {
		return webcamUrl;
	}

	public void setWebcamUrl(String webcamUrl) {
		this.webcamUrl = webcamUrl;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
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

}
