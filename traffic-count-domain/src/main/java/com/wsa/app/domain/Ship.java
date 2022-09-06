package com.wsa.app.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ships")
public class Ship {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "shipped_time")
	private LocalDateTime shippedTime;

	@Column(name = "direction")
	private String direction;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lock_id")
	private Lock lock;

	@Column(name = "category")
	private String category;

	public Ship() {

	}

	public Ship(int id, LocalDateTime shippedTime, String direction, String category) {

		this.id = id;
		this.shippedTime = shippedTime;
		this.direction = direction;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public LocalDateTime getShippedTime() {
		return shippedTime;
	}

	public void setShippedTime(LocalDateTime shippedTime) {
		this.shippedTime = shippedTime;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

}
