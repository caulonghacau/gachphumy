package com.vn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
