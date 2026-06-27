package com.mpss.oms.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerAccount extends Account {

	@Column(unique = true, nullable = false, length = 20)
	private String phone;
	
	private String email;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String address;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();
}
