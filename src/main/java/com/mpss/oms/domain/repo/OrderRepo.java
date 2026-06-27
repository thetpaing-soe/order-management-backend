package com.mpss.oms.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpss.oms.domain.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o JOIN FETCH o.customer c WHERE c.username = :name")
	List<Order> findAllWithUserName(String name);

}
