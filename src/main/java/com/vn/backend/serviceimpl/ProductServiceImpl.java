package com.vn.backend.serviceimpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.vn.utils.Message;

@Service
public class ProductServiceImpl implements ProductService {
	@Value("${upload.path}")
	private String fileUpload;
//	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/upload/";

	public static String UPLOAD_DIRECTORY = "/upload/";
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

		MultipartFile multipartFile = productDto.getImageProduct();
		String fileName = multipartFile.getOriginalFilename();

//		String uploadDirectory = "";
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		if (requestAttributes instanceof ServletRequestAttributes) {
//			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//			uploadDirectory = request.getServletContext().getRealPath(this.fileUpload);
//		}

		Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
		Path staticPath = Paths.get("static");
		Path imagePath = Paths.get("upload");
//		try {
//			String tmp = this.fileUpload + fileName;
//			FileCopyUtils.copy(productDto.getImageProduct().getBytes(), new File(this.fileUpload + fileName));

//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
		try (OutputStream os = Files.newOutputStream(file)) {
			os.write(multipartFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = imagePath.resolve(multipartFile.getOriginalFilename()).toString();

		productDto.setImage(this.fileUpload + fileName);
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
