package com.mpss.oms.auth.service;

import javax.management.RuntimeErrorException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.auth.confg.security.utils.AuthTokenProvider;
import com.mpss.oms.auth.input.AuthLoginForm;
import com.mpss.oms.auth.input.RegistractionForm;
import com.mpss.oms.auth.output.AuthResult;
import com.mpss.oms.domain.entity.Account.AccountType;
import com.mpss.oms.domain.entity.CustomerAccount;
import com.mpss.oms.domain.repo.AccountRepo;
import com.mpss.oms.domain.repo.CustomerAccountRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AccountRepo accountRepo;
	private final CustomerAccountRepo customerRepo;
	
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final AuthTokenProvider tokenProvider;

	@Transactional(readOnly = true)
	public AuthResult login(AuthLoginForm form) {
		try {
			var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.username(), form.password()));
			
			if (authentication.isAuthenticated()) {
				var account = accountRepo.findByUsername(authentication.getName())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with username: %s".formatted(authentication.getName())));
				SecurityContextHolder.getContext().setAuthentication(authentication);
                return new AuthResult(account.getId(), form.username(), tokenProvider.generate(authentication));
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeErrorException(null);
	    }
		
		return null;
	}

	@Transactional
	public AuthResult register(RegistractionForm form) {
		var customerAccount = new CustomerAccount();
		customerAccount.setUsername(form.username());
		customerAccount.setPassword(passwordEncoder.encode(form.password()));
		customerAccount.setFullName(form.fullName());
		customerAccount.setPhone(form.phoneNumber());
		customerAccount.setEmail(form.email());
		customerAccount.setAddress(form.address());
		customerAccount.setAccountType(AccountType.CUSTOMER);
		
		customerAccount = customerRepo.save(customerAccount);
		
		return login(new AuthLoginForm(form.username(), form.password()));
	}

}
