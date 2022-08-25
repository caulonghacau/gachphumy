package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.model.Category;
import com.vn.backend.repository.CategoryRepository;
import com.vn.backend.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getListAll(int deleteFlag) {
		List<Category> categories = categoryRepository.findByDeleteFlag(deleteFlag);
		List<CategoryDto> results = new ArrayList<>();
		for (Category cate : categories) {
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(cate, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public CategoryDto getDetail(Long id, int deleteFlag) {
		Category category = categoryRepository.findByIdAndDeleteFlag(id, deleteFlag);
		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(category, dto);
		return dto;
	}

	@Override
	public CategoryDto add(CategoryDto categoryDto) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		Category result = categoryRepository.save(category);
		BeanUtils.copyProperties(result, categoryDto);
		return categoryDto;
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto) throws Exception {
		Optional<Category> option = categoryRepository.findById(categoryDto.getId());

		if (option.isPresent()) {
			Category category = option.get();
			if (!StringUtils.isEmpty(categoryDto.getName())) {
				category.setName(categoryDto.getName());
			}
			Category result = categoryRepository.save(category);
			BeanUtils.copyProperties(categoryDto, result);
			return categoryDto;
		} else {
			throw new Exception("Update category failure");
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<Category> option = categoryRepository.findById(id);

		if (option.isPresent()) {
			categoryRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
