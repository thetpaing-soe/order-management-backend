package com.mpss.oms.controller.admin.input;

import java.math.BigDecimal;

import com.mpss.oms.domain.entity.Product.ProductAvailability;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductCreationForm(
		@NotEmpty(message = "Please Enter Product Name!")
		String name,
		@NotEmpty(message = "Please Enter Product Description!")
		String description,
		@NotNull(message = "Please Enter Product Price!")
		BigDecimal price,
		@NotNull(message = "Please Enter Available Stock!")
		Long stock,
		@NotNull(message = "Please Enter Product Availabity!")
		ProductAvailability availability
		) {

}
