package com.vn.backend.response;

import java.util.ArrayList;
import java.util.List;

import com.vn.backend.dto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {

	private int page;
	private int size;
	private int totalPages;
	private Long totalElement;
	private List<CategoryDto> categories = new ArrayList<>();

}
