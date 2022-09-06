package com.wsa.app.response;

import java.util.HashMap;
import java.util.List;

public class StatisticsResponse {

	private Long up;
	private Long down;
	private List<ShipCategoryCount> tree;
	private HashMap<String,HashMap<String,Integer>> line;
	
	
	public StatisticsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getUp() {
		return up;
	}


	public void setUp(Long up) {
		this.up = up;
	}


	public Long getDown() {
		return down;
	}


	public void setDown(Long down) {
		this.down = down;
	}


	public List<ShipCategoryCount> getTree() {
		return tree;
	}


	public void setTree(List<ShipCategoryCount> tree) {
		this.tree = tree;
	}


	public HashMap<String, HashMap<String, Integer>> getLine() {
		return line;
	}


	public void setLine(HashMap<String, HashMap<String, Integer>> line) {
		this.line = line;
	}


	public StatisticsResponse(Long up, Long down, List<ShipCategoryCount> tree,
			HashMap<String, HashMap<String, Integer>> line) {
		super();
		this.up = up;
		this.down = down;
		this.tree = tree;
		this.line = line;
	}





	
	
	
	
	

}
