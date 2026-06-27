package com.mpss.oms.controller.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.controller.admin.input.OrderStatusUpdateForm;
import com.mpss.oms.controller.dto.OrderDetails;
import com.mpss.oms.controller.dto.OrderInfo;
import com.mpss.oms.domain.repo.OrderRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
	
	private final OrderRepo repo;

	@Transactional(readOnly = true)
	public List<OrderInfo> findAll() {
		return repo.findAll().stream().map(OrderInfo::from).toList();
	}

	@Transactional(readOnly = true)
	public OrderDetails findById(Long id) {
		return repo.findById(id).map(OrderDetails::from)
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id %s.".formatted(id)));
	}

	@Transactional
	public String updateStatus(OrderStatusUpdateForm form) {
		var order = repo.findById(form.orderId())
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id %s.".formatted(form.orderId())));
		
		order.setStatus(form.status());
		
		return "Order with id %s updated successfully!".formatted(form.orderId());
	}
	
}
