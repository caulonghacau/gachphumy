package com.vn.backend.service;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.ContactDto;
import com.vn.backend.response.ContactResponse;

public interface ContactService {
	ContactResponse getPaggingContact(int deleteFlag, Pageable pagging);

	ContactDto getDetail(Long id, int deleteFlag);

	ContactDto add(ContactDto contactDto);

	ContactDto update(ContactDto contactDto) throws Exception;

	boolean delete(Long id) throws Exception;
}
