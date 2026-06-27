package com.mpss.oms.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mpss.oms.domain.entity.Order;
import com.mpss.oms.domain.entity.Order.OrderStatus;

public record OrderInfo(
		Long orderId,
		CustomerInfo customer,
		List<ProductInfo> products,
		BigDecimal totalAmount,
		LocalDateTime orderedAt,
		OrderStatus orderStatus
		) {
	
	public static OrderInfo from(Order entity) {
		var customer = entity.getCustomer();
		
		return new OrderInfo(
				entity.getId(), 
				CustomerInfo.from(customer),
				entity.getOrderHistories().stream().map((history) -> ProductInfo.from(history.getProduct())).toList(),
				entity.getTotalAmount(),
				entity.getIssueAt(), 
				entity.getStatus());
	}

}
