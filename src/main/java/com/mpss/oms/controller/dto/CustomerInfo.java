package com.mpss.oms.controller.dto;

import com.mpss.oms.domain.entity.CustomerAccount;

public record CustomerInfo(
		Long id,
		String name,
		String phone
		) {

	public static CustomerInfo from (CustomerAccount entity) {
		return new CustomerInfo(
				entity.getId(),
				entity.getFullName(), 
				entity.getPhone());
	}
}
