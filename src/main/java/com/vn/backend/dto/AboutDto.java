package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AboutDto {
	private Long id;
	private String nameTab;
	private String title;
	private String header;
	private String decription;
}
