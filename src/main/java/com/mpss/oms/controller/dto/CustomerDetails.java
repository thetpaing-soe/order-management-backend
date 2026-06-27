package com.mpss.oms.controller.dto;

import com.mpss.oms.domain.entity.CustomerAccount;

public record CustomerDetails(
		String name,
		String phone,
		String email,
		String address
		) {
	
	public static CustomerDetails from(CustomerAccount entity) {
		return new CustomerDetails(
				entity.getFullName(), 
				entity.getPhone(), 
				entity.getEmail(), 
				entity.getAddress());
	}

}
