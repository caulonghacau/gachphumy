package com.vn.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
	private Long id;
	private String producCode;

	private Long categoryId;
	private String name;
	private String image;
	private int amount;
	private int status;
	private BigDecimal price;
	private String ingredient;
	private String standard;
	private String specifications;
	private String size;
	private String weight;
	private String newProduct;
	private String description1;
	private String description2;
}
