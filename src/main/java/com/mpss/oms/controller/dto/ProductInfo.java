package com.mpss.oms.controller.dto;

import java.math.BigDecimal;

import com.mpss.oms.domain.entity.Product;
import com.mpss.oms.domain.entity.Product.ProductAvailability;

public record ProductInfo(
		long id,
		String code,
		String name,
		BigDecimal price,
		long stock,
		ProductAvailability availability
		) {

	public static ProductInfo from(Product entity) {
		return new ProductInfo(
				entity.getId(),
				entity.getCode(),
				entity.getName(),
				entity.getPrice(),
				entity.getStock(),
				entity.getAvailability());
	}
}
