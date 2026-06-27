package com.mpss.oms.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
	public ResponseEntity<String> handleBadRequest(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AuthorizationDeniedException ex) {
		return ResponseEntity.status(403).body(ex.getMessage());
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleException(Throwable ex) {
		ex.printStackTrace();
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}

}
