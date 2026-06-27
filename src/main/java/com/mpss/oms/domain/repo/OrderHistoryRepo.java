package com.mpss.oms.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mpss.oms.domain.entity.OrderHistory;

public interface OrderHistoryRepo extends JpaRepository<OrderHistory, Long> {

	@Query("SELECT oh FROM OrderHistory oh where oh.order.id = :id")
	List<OrderHistory> findAllWithId(Long id);
}
