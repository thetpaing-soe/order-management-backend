package com.mpss.oms.exception.aspect;

import java.util.stream.Collectors;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidationAspect {

	@AfterThrowing(value = "within(com.mpss.oms..*) && args(.., result)", argNames = "result")
	void validate(BindingResult result) {
		if (result.hasErrors()) {
			var message = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n"));
			throw new IllegalArgumentException(message);
		}
	}
}
