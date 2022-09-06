package com.wsa.app.response;

public class LockNameAndId {

	private String id;
	private String name;

	public LockNameAndId(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
