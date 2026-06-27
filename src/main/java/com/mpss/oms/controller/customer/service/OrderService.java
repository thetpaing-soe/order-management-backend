package com.mpss.oms.controller.customer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.controller.customer.input.OrderPurchaseForm;
import com.mpss.oms.controller.dto.OrderDetails;
import com.mpss.oms.controller.dto.OrderInfo;
import com.mpss.oms.domain.entity.Order;
import com.mpss.oms.domain.entity.Order.OrderStatus;
import com.mpss.oms.domain.entity.OrderHistory;
import com.mpss.oms.domain.repo.CustomerAccountRepo;
import com.mpss.oms.domain.repo.OrderHistoryRepo;
import com.mpss.oms.domain.repo.OrderRepo;
import com.mpss.oms.domain.repo.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final CustomerAccountRepo customerRepo;
	private final ProductRepo productRepo;
	private final OrderRepo orderRepo;
	private final OrderHistoryRepo historyRepo;
	
	@Transactional(readOnly = true)
	public List<OrderInfo> findAll() {
		return orderRepo.findAllWithUserName(SecurityContextHolder.getContext().getAuthentication().getName())
				.stream().map(OrderInfo::from).toList();
	}

	@Transactional(readOnly = true)
	public OrderDetails findById(Long id) {
		return orderRepo.findById(id).map(OrderDetails::from)
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id %s.".formatted(id)));
	}

	@Transactional
	public String placeOrder(OrderPurchaseForm form) {
		
		var customer = customerRepo.findById(form.customerId())
				.orElseThrow(() -> new EntityNotFoundException("Customer not found with id %s.".formatted(form.customerId())));
		
		var order = new Order();
		var orderHistories = new ArrayList<OrderHistory>();
		
		form.orders().forEach((item) -> {
			var product = productRepo.findById(item.productId())
					.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(item.productId())));
			
			if (product.getStock() < item.quantity()) {
				throw new IllegalArgumentException("Insufficient stock for product with id %s.".formatted(item.quantity()));
			}
			
			product.setStock(product.getStock() - item.quantity());
			
			var orderHistory = new OrderHistory();
			orderHistory.setOrder(order);
			orderHistory.setProduct(product);
			orderHistory.setQuantity(item.quantity());
			
			orderHistories.add(orderHistory);
			order.setTotalAmount(order.getTotalAmount().add(product.getPrice().multiply(BigDecimal.valueOf(item.quantity()))));
		});
		
		order.setCustomer(customer);
		order.setOrderHistories(orderHistories);
		order.setStatus(OrderStatus.Pending);
		order.setIssueAt(LocalDateTime.now());
		
		orderRepo.save(order);
		
		return "Order placed successfully!";
	}

	@Transactional
	public String cancelOrder(Long id) {
		var order = orderRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id %s.".formatted(id)));
		
		if (!order.getCustomer().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new RuntimeException("You can't do thi action!");
		}
		
		if (order.getStatus() != OrderStatus.Pending) {
			throw new IllegalArgumentException("Invalid Order Status!");
		}
		
		var orderHistory = historyRepo.findAllWithId(id);
		
		orderHistory.forEach(history -> {
			var product = productRepo.findById(history.getProduct().getId())
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(history.getProduct().getId())));
			
			product.setStock(product.getStock() + history.getQuantity());
		});
		
		order.setStatus(OrderStatus.Cancelled);
		
		return "Order with id %s updated successfully!".formatted(id);
	}

}
