package com.mpss.oms.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mpss.oms.domain.entity.Order;
import com.mpss.oms.domain.entity.Order.OrderStatus;

public record OrderDetails(
		String username,
		CustomerDetails customer,
		List<ProductDetails> products,
		BigDecimal totalAmount,
		LocalDateTime orderedAt,
		OrderStatus orderStatus
		) {
	
	public static OrderDetails from(Order entity) {
		return new OrderDetails(
				entity.getCustomer().getUsername(), 
				CustomerDetails.from(entity.getCustomer()), 
				entity.getOrderHistories().stream().map(history -> ProductDetails.from(history.getProduct())).toList(),
				entity.getTotalAmount(),
				entity.getIssueAt(), 
				entity.getStatus());
	}

}
