package com.wsa.app.response;

public class ShipCategoryCount {

	private String category;
	private int upward;
	private int downward;
	private int total;
	
	public ShipCategoryCount(String category, int upward, int downward, int total) {
		this.category = category;
		this.upward = upward;
		this.downward = downward;
		this.total = total;
	}

	public String getCategory() {
		return category;
	}

	public int getUpward() {
		return upward;
	}

	public int getDownward() {
		return downward;
	}

	public int getTotal() {
		return total;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setUpward(int upward) {
		this.upward = upward;
	}

	public void setDownward(int downward) {
		this.downward = downward;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
