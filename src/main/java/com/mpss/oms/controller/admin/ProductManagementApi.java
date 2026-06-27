package com.mpss.oms.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpss.oms.controller.admin.input.ProductCreationForm;
import com.mpss.oms.controller.admin.service.ProductManagementService;
import com.mpss.oms.controller.dto.ProductDetails;
import com.mpss.oms.controller.dto.ProductInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductManagementApi {
	
	private final ProductManagementService service;
	
	@GetMapping
	public ResponseEntity<List<ProductInfo>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProductDetails> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
    }
	
	@PostMapping
	public ResponseEntity<String> createProduct(
			@Validated @RequestBody ProductCreationForm form, BindingResult result) {
		return ResponseEntity.ok(service.createProduct(form));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<String> updateProduct(
			@PathVariable Long id, 
			@Validated @RequestBody ProductCreationForm form, BindingResult result) {
		return ResponseEntity.ok(service.updateProduct(id, form));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		return ResponseEntity.ok(service.deleteProduct(id));
	}
}
