package com.vn.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.response.ProductResponse;

public interface ProductService {

	List<ProductDto> getListProduct();

	ProductDto addProduct(ProductDto productDto);

	ProductResponse getPaggingProduct(int deleteFlag, Pageable pagging);

	ProductDto getDetail(Long id, int deleteFlag);

	ProductDto add(ProductDto productDto);

	ProductDto update(ProductDto productDto) throws Exception;

	boolean delete(Long id) throws Exception;

}
