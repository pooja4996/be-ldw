package com.wsa.app.response;

public class LockInfoDto {

	private String id;
	private String lockName;
	private String adminOffice;
	private String secondLevelAdmin;
	private String waterway;

	public LockInfoDto() {

	}

	public LockInfoDto(String id, String lockName, String adminOffice, String secondLevelAdmin, String waterway) {

		this.id = id;
		this.lockName = lockName;
		this.adminOffice = adminOffice;
		this.secondLevelAdmin = secondLevelAdmin;
		this.waterway = waterway;
	}

	public String getId() {
		return id;
	}

	public String getLockName() {
		return lockName;
	}

	public String getAdminOffice() {
		return adminOffice;
	}

	public String getSecondLevelAdmin() {
		return secondLevelAdmin;
	}

	public String getWaterway() {
		return waterway;
	}

}
