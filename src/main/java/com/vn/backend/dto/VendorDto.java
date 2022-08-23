package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VendorDto {
	private Long id;
	private String name;
	private String image;
	private int deleteFlag;
}
