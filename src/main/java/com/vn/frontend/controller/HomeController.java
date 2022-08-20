package com.vn.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vn.auth.jwt.JwtUtils;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.auth.request.LoginRequest;
import com.vn.auth.response.JwtResponse;
import com.vn.auth.service.SecurityService;
import com.vn.auth.service.UserDetailsImpl;

@Controller
public class HomeController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	SecurityService securityService;

	@RequestMapping("/")
	public String home() {
		return "/frontend/home";
	}

	@GetMapping("/home")
	public String home1() {
		return "/frontend/home";
	}

	@GetMapping("/admin")
	public String admin() {
		return "backend/admin";
	}

	@GetMapping("/admin/product")
	public String product() {
		return "backend/product/view";
	}

	@GetMapping("/user")
	public String user() {
		return "/user";
	}

	@GetMapping("/about")
	public String about() {
		return "/frontend/about";
	}

	@GetMapping("/contact")
	public String contact() {
		return "/frontend/contact";
	}

	@GetMapping("/token")
	public String token(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("admin1");
		loginRequest.setPassword("123456");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles);

		List<String> notes = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
		// check if notes is present in session or not
		if (notes == null) {
			notes = new ArrayList<>();
			// if notes object is not present in session, set notes in the request session
			request.getSession().setAttribute("NOTES_SESSION", notes);
		}
		notes.add(jwt);

		request.getSession().setAttribute("NOTES_SESSION", notes);

		return "/backend/admin";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		return "/login";
	}

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

	@GetMapping("/product/view")
	public String view() {
		return "/frontend/product/view";
	}

	@GetMapping("/product/detail")
	public String detail() {
		return "/frontend/product/detail";
	}

}
