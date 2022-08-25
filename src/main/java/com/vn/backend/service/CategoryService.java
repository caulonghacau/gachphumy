package com.vn.backend.service;

import java.util.List;

import com.vn.backend.dto.CategoryDto;

public interface CategoryService {

	List<CategoryDto> getListAll(int deleteFlag);

	CategoryDto getDetail(Long id, int deleteFlag);

	CategoryDto add(CategoryDto categoryDto);

	CategoryDto update(CategoryDto categoryDto) throws Exception;

	boolean delete(Long id) throws Exception;

}
