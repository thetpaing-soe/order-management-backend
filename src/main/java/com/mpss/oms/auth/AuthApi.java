package com.mpss.oms.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpss.oms.auth.input.AuthLoginForm;
import com.mpss.oms.auth.input.RegistractionForm;
import com.mpss.oms.auth.output.AuthResult;
import com.mpss.oms.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {
	
	private final AuthService service;
	
	@PostMapping("login")
	public ResponseEntity<AuthResult> login(@RequestBody AuthLoginForm form) {
		return ResponseEntity.ok(service.login(form));
	}
	
	@PostMapping("register")
	public ResponseEntity<AuthResult> register(
			@RequestBody RegistractionForm form, BindingResult result) {
		return ResponseEntity.ok(service.register(form));
	}

}
