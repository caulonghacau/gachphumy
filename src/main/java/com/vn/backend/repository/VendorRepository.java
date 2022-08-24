package com.vn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
