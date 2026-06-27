package com.mpss.oms.controller.dto;

import java.math.BigDecimal;

import com.mpss.oms.domain.entity.Product;
import com.mpss.oms.domain.entity.Product.ProductAvailability;

public record ProductDetails(
		String code,
		String name,
		String description,
		BigDecimal price,
		long stock,
		ProductAvailability availability
		) {
	
	public static ProductDetails from(Product entity) {
		return new ProductDetails(
				entity.getCode(), 
				entity.getName(), 
				entity.getDescription(), 
				entity.getPrice(), 
				entity.getStock(), 
				entity.getAvailability());
	}

}
