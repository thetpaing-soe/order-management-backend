package com.mpss.oms.controller.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpss.oms.controller.customer.input.OrderPurchaseForm;
import com.mpss.oms.controller.customer.service.OrderService;
import com.mpss.oms.controller.dto.OrderDetails;
import com.mpss.oms.controller.dto.OrderInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer/orders")
@RequiredArgsConstructor
public class OrderApi {

	private final OrderService service;
	
	@GetMapping
	public ResponseEntity<List<OrderInfo>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	@PostAuthorize("returnObject.body.username == authentication.name")
	public ResponseEntity<OrderDetails> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping("purchase")
	public ResponseEntity<String> placeOrder(
			@Validated @RequestBody OrderPurchaseForm form, BindingResult result) {
		return ResponseEntity.ok(service.placeOrder(form));
	}
	
	@PutMapping("{id}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
		return ResponseEntity.ok(service.cancelOrder(id));
	}
}
