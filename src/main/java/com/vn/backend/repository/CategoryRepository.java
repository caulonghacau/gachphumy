package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByDeleteFlag(int deleteFlag, Pageable pagging);

	Category findByIdAndDeleteFlag(Long id, int deleteFlag);

}
