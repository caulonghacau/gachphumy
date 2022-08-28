package com.vn.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	Page<Contact> findByDeleteFlag(int deleteFlag, Pageable pagging);

	Contact findByIdAndDeleteFlag(Long id, int deleteFlag);
}
