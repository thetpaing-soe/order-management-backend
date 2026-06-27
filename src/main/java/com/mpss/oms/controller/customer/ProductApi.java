package com.mpss.oms.controller.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpss.oms.controller.customer.service.ProductService;
import com.mpss.oms.controller.dto.ProductDetails;
import com.mpss.oms.controller.dto.ProductInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer/products")
@RequiredArgsConstructor
public class ProductApi {
	
	private final ProductService service;
	
	@GetMapping
	public ResponseEntity<List<ProductInfo>> findAll(@RequestParam(required = false) String productName) {
		return ResponseEntity.ok(service.findAll(productName));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProductDetails> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
    }

}
