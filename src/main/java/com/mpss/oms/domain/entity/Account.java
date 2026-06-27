package com.mpss.oms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, length = 75)
	private String fullName;
	
	@Column(nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	public enum AccountType {
		ADMIN, CUSTOMER
	}
}
