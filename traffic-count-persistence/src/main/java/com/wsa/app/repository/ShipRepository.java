package com.wsa.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wsa.app.domain.Ship;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {

	@Query("SELECT S FROM Ship S WHERE S.lock.id=:id")
	List<Ship> findAllByLockId(String id);

	List<Ship> findAllByShippedTimeBetween(@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
			@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime);

}
