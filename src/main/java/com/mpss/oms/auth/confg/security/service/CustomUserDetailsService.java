package com.mpss.oms.auth.confg.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpss.oms.domain.entity.Account;
import com.mpss.oms.domain.entity.Account.AccountType;
import com.mpss.oms.domain.repo.AccountRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final AccountRepo repo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var account = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: %s".formatted(username)));
		
		return User.withUsername(account.getUsername())
				.password(account.getPassword())
				.roles(account.getAccountType().name())
				.build();
	}


	@PostConstruct
	@Transactional
	void init() {
		repo.findByUsername("admin").orElseGet(() -> {
			var account = new Account();
			account.setUsername("admin");
			account.setPassword(passwordEncoder.encode("admin"));
			account.setFullName("Admin User");
			account.setAccountType(AccountType.ADMIN);
			
			return repo.save(account);
		});
		
	}
}
