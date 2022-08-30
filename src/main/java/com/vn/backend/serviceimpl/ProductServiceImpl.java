package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.model.Product;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.response.ProductResponse;
import com.vn.backend.service.ProductService;
import com.vn.utils.Message;

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

	@Override
	public ProductResponse getPaggingProduct(int deleteFlag, Pageable pagging) {
		ProductResponse results = new ProductResponse();

		Page<Product> pages = productRepository.findByDeleteFlag(deleteFlag, pagging);
		List<Product> products = pages.getContent();
		List<ProductDto> listResult = new ArrayList<>();

		for (Product product : products) {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(product, dto);
			listResult.add(dto);
		}
		results.setProducts(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public ProductDto getDetail(Long id, int deleteFlag) {
		Product dataDetail = productRepository.findByIdAndDeleteFlag(id, deleteFlag);
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(dataDetail, dto);
		return dto;
	}

	@Override
	public ProductDto add(ProductDto productDto) {
		Product newObject = new Product();
		BeanUtils.copyProperties(productDto, newObject);
		Product result = productRepository.save(newObject);
		BeanUtils.copyProperties(result, productDto);
		return productDto;
	}

	@Override
	public ProductDto update(ProductDto productDto) throws Exception {
		Optional<Product> option = productRepository.findById(productDto.getId());

		if (option.isPresent()) {
			Product dataDb = option.get();

			if (!StringUtils.isEmpty(productDto.getCategoryId())) {
				dataDb.setCategoryId(productDto.getCategoryId());
			}
			if (StringUtils.hasText(productDto.getImage())) {
				dataDb.setImage(productDto.getImage());
			}
			if (StringUtils.hasText(productDto.getProductCode())) {
				dataDb.setProductCode(productDto.getProductCode());
			}
			if (StringUtils.hasText(productDto.getName())) {
				dataDb.setName(productDto.getName());
			}
			if (!StringUtils.isEmpty(productDto.getAmount())) {
				dataDb.setAmount(productDto.getAmount());
			}
			if (!StringUtils.isEmpty(productDto.getPrice())) {
				dataDb.setPrice(productDto.getPrice());
			}

			if (StringUtils.hasText(productDto.getIngredient())) {
				dataDb.setIngredient(productDto.getIngredient());
			}

			if (StringUtils.hasText(productDto.getStandard())) {
				dataDb.setStandard(productDto.getStandard());
			}

			if (StringUtils.hasText(productDto.getSpecifications())) {
				dataDb.setSpecifications(productDto.getSpecifications());
			}

			if (StringUtils.hasText(productDto.getSize())) {
				dataDb.setSize(productDto.getSize());
			}

			if (StringUtils.hasText(productDto.getWeight())) {
				dataDb.setWeight(productDto.getWeight());
			}

			if (StringUtils.hasText(productDto.getNewProduct())) {
				dataDb.setNewProduct(productDto.getNewProduct());
			}

			if (StringUtils.hasText(productDto.getDescription1())) {
				dataDb.setDescription1(productDto.getDescription1());
			}

			if (StringUtils.hasText(productDto.getDescription2())) {
				dataDb.setDescription2(productDto.getDescription2());
			}

			Product result = productRepository.save(dataDb);
			BeanUtils.copyProperties(productDto, result);
			return productDto;
		} else {
			throw new Exception(Message.UPDATE_FAILURE);
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {

		Optional<Product> option = productRepository.findById(id);
		if (option.isPresent()) {
			productRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
