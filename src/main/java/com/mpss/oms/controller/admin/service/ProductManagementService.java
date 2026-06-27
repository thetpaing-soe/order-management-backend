package com.mpss.oms.controller.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.controller.admin.input.ProductCreationForm;
import com.mpss.oms.controller.dto.ProductDetails;
import com.mpss.oms.controller.dto.ProductInfo;
import com.mpss.oms.domain.entity.Product;
import com.mpss.oms.domain.repo.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductManagementService {
	
	private final ProductRepo repo;

	@Transactional(readOnly = true)
	public List<ProductInfo> findAll() {
		return repo.findAll().stream().map(ProductInfo::from).toList();
	}

	@Transactional(readOnly = true)
	public ProductDetails findById(Long id) {
		return repo.findById(id).map(ProductDetails::from)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(id)));
	}

	@Transactional
	public String createProduct(ProductCreationForm form) {
		var product = new Product();
		product.setCode(String.valueOf(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli()));
		product.setName(form.name());
		product.setDescription(form.description());
		product.setPrice(form.price());
		product.setStock(form.stock());
		product.setAvailability(form.availability());
		
		product = repo.save(product);
		
		return "Product with id %s created successfully!".formatted(product.getId());
	}

	@Transactional
	public String updateProduct(Long id, ProductCreationForm form) {
		var product = repo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(id)));
		
		product.setName(form.name());
		product.setDescription(form.description());
		product.setPrice(form.price());
		product.setStock(form.stock());
		product.setAvailability(form.availability());
		
		product = repo.save(product);
		
		return "Product with id %s updated successfully!.".formatted(product.getId());
		
	}

	@Transactional
	public String deleteProduct(Long id) {
		var product = repo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id %s.".formatted(id)));
		
		product.setDeleted(true);
		
		return "Product with id %s deleted successfully!".formatted(product.getId());
	}

}
