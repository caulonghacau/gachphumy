package com.vn.auth.data;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		Optional<Role> optionalAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
		if (!optionalAdmin.isPresent()) {
			Role role = new Role(ERole.ROLE_ADMIN);
			roleRepository.save(role);
		}
		Optional<Role> optionalUser = roleRepository.findByName(ERole.ROLE_USER);
		if (!optionalUser.isPresent()) {
			Role role = new Role(ERole.ROLE_USER);
			roleRepository.save(role);
		}

		List<User> listUser = userRepository.findAll();
		// Admin account
		if (listUser == null || (listUser != null && listUser.size() == 0)) {
			User admin = new User();
			admin.setUsername("gachphumy");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("123456"));
			HashSet<Role> roles = new HashSet<>();
			Role roleUser = roleRepository.findByName(ERole.ROLE_USER).get();
			Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
			roles.add(roleUser);
			roles.add(roleAdmin);
			admin.setRoles(roles);
			userRepository.save(admin);
		}
	}
}
