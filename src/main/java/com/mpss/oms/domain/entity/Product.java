package com.mpss.oms.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String name;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private long stock;
	
	@Enumerated(EnumType.STRING)
	private ProductAvailability availability = ProductAvailability.IN_STOCK;
	
	private boolean deleted = false;
	
	@OneToMany(mappedBy = "product")
	private List<OrderHistory> orderHistories = new ArrayList<>();
	
	public enum ProductAvailability {
		IN_STOCK,
		LOW_STOCK,
		OUT_OF_STOCK,
		PRE_ORDER
	}
}
