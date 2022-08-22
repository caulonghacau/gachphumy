package com.vn.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.vn.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Long id;
	private String username;
	private String email;
	private String password;
	private List<Role> roles = new ArrayList<>();
}
