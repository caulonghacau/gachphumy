package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.model.Product;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductDto> getListProduct() {
		List<Product> products = productRepository.findAll();
		List<ProductDto> results = new ArrayList<>();

		for (Product product : products) {
			ProductDto dto = new ProductDto();

			BeanUtils.copyProperties(product, dto);
			results.add(dto);

		}
		return results;
	}

	@Override
	public ProductDto addProduct(ProductDto product) {
		return null;
	}

}
