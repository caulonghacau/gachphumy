package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vn.auth.model.ERole;
import com.vn.auth.model.Role;
import com.vn.auth.model.User;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.backend.dto.UserDto;
import com.vn.backend.response.UserResponse;
import com.vn.backend.service.UserService;
import com.vn.utils.Message;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponse getPaggingUser(int deleteFlag, Pageable pagging) {
		Page<User> pages = userRepository.findAll(pagging);
		UserResponse results = new UserResponse();

		List<User> users = pages.getContent();
		List<UserDto> listResult = new ArrayList<>();

		for (User user : users) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			listResult.add(dto);
		}
		results.setUsers(listResult);
		results.setPage(pages.getPageable().getPageNumber());
		results.setSize(pages.getSize());
		results.setTotalPages(pages.getTotalPages());
		results.setTotalElement(pages.getTotalElements());
		return results;
	}

	@Override
	public UserDto getDetail(Long id, int deleteFlag) {
		Optional<User> dataDetail = userRepository.findById(id);
		if (dataDetail.isPresent()) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(dataDetail.get(), dto);
			return dto;
		}

		return null;
	}

	@Override
	public UserDto add(UserDto userDto) {
		User newObject = new User();
		BeanUtils.copyProperties(userDto, newObject);
		newObject.setPassword(passwordEncoder.encode(userDto.getPassword()));

		HashSet<Role> roles = new HashSet<>();
		Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

		roles.add(roleAdmin);
		newObject.setRoles(roles);

		User result = userRepository.save(newObject);
		BeanUtils.copyProperties(result, userDto);
		return userDto;
	}

	@Override
	public UserDto update(UserDto userDto) throws Exception {
		Optional<User> option = userRepository.findById(userDto.getId());

		if (option.isPresent()) {
			User dataDb = option.get();
			if (!StringUtils.isEmpty(userDto.getEmail())) {
				dataDb.setEmail(userDto.getEmail());
			}

			User result = userRepository.save(dataDb);
			BeanUtils.copyProperties(userDto, result);
			return userDto;
		} else {
			throw new Exception(Message.UPDATE_FAILURE);
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Optional<User> option = userRepository.findById(id);
		if (option.isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
