package com.mpss.oms.controller.customer.input;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderPurchaseForm(
		@NotNull(message = "Customer ID cannot be null!")
		Long customerId,
		@NotNull(message = "Orders cannot be null!")
		@NotEmpty(message = "Please select items")
		List<OrderItem> orders
		) {

}
