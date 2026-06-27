package com.mpss.oms.controller.admin.input;

import com.mpss.oms.domain.entity.Order.OrderStatus;

import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateForm(
		@NotNull(message = "Please Enter Order Id!")
		Long orderId,
		@NotNull(message = "Please Enter Order Status!")
		OrderStatus status
		) {

}
