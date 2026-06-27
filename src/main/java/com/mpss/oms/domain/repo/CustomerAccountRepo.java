package com.mpss.oms.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpss.oms.domain.entity.CustomerAccount;

public interface CustomerAccountRepo extends JpaRepository<CustomerAccount, Long> {

}
