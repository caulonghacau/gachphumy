package com.vn.backend.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String email;
	private String password;
}
