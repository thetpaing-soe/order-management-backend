package com.mpss.oms.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpss.oms.controller.admin.input.OrderStatusUpdateForm;
import com.mpss.oms.controller.admin.service.OrderManagementService;
import com.mpss.oms.controller.dto.OrderDetails;
import com.mpss.oms.controller.dto.OrderInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderManagementApi {
	
	private final OrderManagementService service;
	
	@GetMapping
	public ResponseEntity<List<OrderInfo>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<OrderDetails> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping("status")
	public ResponseEntity<String> updateOrderStatus(
			@Validated @RequestBody OrderStatusUpdateForm form, BindingResult result) {
		return ResponseEntity.ok(service.updateStatus(form));
	}

}
