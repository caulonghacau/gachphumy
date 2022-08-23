package com.vn.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Slice;

public interface SliceRepository extends JpaRepository<Slice, Long> {
	List<Slice> findByType(int type);

}
