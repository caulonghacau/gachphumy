package com.vn;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;

public class GenerateUser {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;

	public void addUser() {
		try {
			List<User> listUser = userRepository.findAll();
			if (listUser != null && listUser.size() > 0) {
			} else {
				User user = new User();
				user.setUsername("gachphumy");
				user.setEmail("bandauzero@gmail.com");
				user.setPassword(encoder.encode("123456"));

				Set<Role> roles = new HashSet<>();

				Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
				if (!adminRole.isPresent()) {
					Role role = new Role();
					role.setName(ERole.ROLE_ADMIN);
					Role roleNew = roleRepository.save(role);
					roles.add(roleNew);
				} else {
					roles.add(adminRole.get());
				}

				user.setRoles(roles);
				userRepository.save(user);
			}
		} catch (Exception e) {
			User user = new User();
			user.setUsername("gachphumy");
			user.setEmail("bandauzero@gmail.com");
			user.setPassword(encoder.encode("123456"));

			Set<Role> roles = new HashSet<>();

			Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
			if (!adminRole.isPresent()) {
				Role role = new Role();
				role.setName(ERole.ROLE_ADMIN);
				Role roleNew = roleRepository.save(role);
				roles.add(roleNew);
			} else {
				roles.add(adminRole.get());
			}

			user.setRoles(roles);
			userRepository.save(user);
		}

	}
}
