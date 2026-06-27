package com.mpss.oms.controller.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.controller.dto.ProductDetails;
import com.mpss.oms.controller.dto.ProductInfo;
import com.mpss.oms.domain.repo.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepo repo;

	@Transactional(readOnly = true)
	public List<ProductInfo> findAll(String productName) {
		return repo.findByName(productName).stream().map(ProductInfo::from).toList();
	}

	@Transactional(readOnly = true)
	public ProductDetails findById(Long id) {
		return repo.findById(id).map(ProductDetails::from)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(id)));
	}

}
