package com.vn.backend.service;

import java.util.List;

import com.vn.backend.dto.ProductDto;

public interface ProductService {

	List<ProductDto> getListProduct();

	ProductDto addProduct(ProductDto product);

}
