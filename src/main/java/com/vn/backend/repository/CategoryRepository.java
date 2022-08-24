package com.vn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
