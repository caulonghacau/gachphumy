package com.vn.backend.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.model.Product;
import com.vn.backend.repository.ProductRepository;
import com.vn.backend.response.ProductResponse;
import com.vn.backend.service.ProductService;
import com.vn.utils.FileUploadUtil;
import com.vn.utils.Message;

@Service
public class ProductServiceImpl implements ProductService {
	@Value("${upload.path}")
	private String fileUpload;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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
		MultipartFile multipartFile = dto.getImageProduct();
		dto.setImageProduct(multipartFile);
		BeanUtils.copyProperties(dataDetail, dto);
		return dto;
	}

	@Override
	public ProductDto add(ProductDto productDto) {
		Product newObject = new Product();

		MultipartFile multipartFile = productDto.getImageProduct();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
		} catch (IOException e) {
			logger.error("Upload image has erro: " + productDto.getId());
			logger.error("Message: " + e.getMessage());
		}
		String url = File.separator + this.fileUpload + File.separator + fileName;
		productDto.setImage(url);
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

			MultipartFile multipartFile = productDto.getImageProduct();
			String fileName = multipartFile.getOriginalFilename();
			try {
				FileUploadUtil.saveFile(this.fileUpload, fileName, multipartFile);
			} catch (IOException e) {
				logger.error("Upload image has erro: " + productDto.getId());
				logger.error("Message: " + e.getMessage());
			}
			String url = File.separator + this.fileUpload + File.separator + fileName;
			productDto.setImage(url);
			if (StringUtils.hasText(url)) {
				dataDb.setImage(url);
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
