package com.mpss.oms.domain.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpss.oms.domain.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {
	
	Optional<Account> findByUsername(String username);

}
