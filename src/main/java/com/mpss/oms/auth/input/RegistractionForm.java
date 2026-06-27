package com.mpss.oms.auth.input;

import jakarta.validation.constraints.NotEmpty;

public record RegistractionForm(
		@NotEmpty(message = "Please Enter username!")
		String username,
		@NotEmpty(message = "Please Enter password!")
		String password,
		@NotEmpty(message = "Please Enter Full Name!")
		String fullName,
		@NotEmpty(message = "Please Enter phone number!")
		String phoneNumber,
		String email,
		@NotEmpty(message = "Please Enter address!")
		String address
		) {

}
