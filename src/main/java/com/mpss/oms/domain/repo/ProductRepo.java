package com.mpss.oms.domain.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.mpss.oms.domain.entity.Product;

import jakarta.persistence.LockModeType;

public interface ProductRepo extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.deleted = false")
	List<Product> findAll();
	
	@Query("SELECT p FROM Product p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))) AND p.deleted = false")
	List<Product> findByName(String name);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM Product p WHERE p.id = :id AND p.deleted = false")
	Optional<Product> findById(Long id);
}
