package com.wsa.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wsa.app.domain.Lock;
import com.wsa.app.response.LockInfoDto;

@Repository
public interface LockRepository extends JpaRepository<Lock, String> {

	@Query("SELECT new com.wsa.app.response.LockInfoDto (id, lockName, adminOffice, secondLevelAdmin, waterway) FROM  Lock lock")
	List<LockInfoDto> findLockFilterInfo();
	
	@Query("SELECT l FROM Lock l WHERE l.lockName LIKE %?1%" + " OR l.id LIKE %?1%"
			+ " OR l.adminOffice LIKE %?1%" + " OR l.secondLevelAdmin LIKE %?1%"
			+ " OR l.waterway LIKE %?1%")
	public List<Lock> search(String keyword);
}
