package com.mpss.oms.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime issueAt;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status = OrderStatus.Pending;

	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private CustomerAccount customer;
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<OrderHistory> orderHistories = new ArrayList<>();
	
	public enum OrderStatus {
		Pending,
		Confirmed,
		Processing,
		Shipped,
		Delivered,
		Cancelled,
	}
}
