package com.vn.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.auth.jwt.JwtUtils;
import com.vn.auth.repository.RoleRepository;
import com.vn.auth.repository.UserRepository;
import com.vn.auth.request.LoginRequest;
import com.vn.auth.response.JwtResponse;
import com.vn.auth.service.SecurityService;
import com.vn.auth.service.UserDetailsImpl;
import com.vn.backend.dto.AboutDto;
import com.vn.backend.dto.AdvantageDto;
import com.vn.backend.dto.CustomerDto;
import com.vn.backend.dto.ProductDto;
import com.vn.backend.response.AboutResponse;
import com.vn.backend.service.AboutService;
import com.vn.backend.service.AdvantageService;
import com.vn.backend.service.CustomerService;
import com.vn.backend.service.ProductService;
import com.vn.utils.Constant;

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

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AdvantageService advantageService;

	@Autowired
	private AboutService aboutService;

	@GetMapping(value = { "/", "/home" })
	public String home1(Model model) {

		List<ProductDto> products = productService.getListProduct();
		model.addAttribute("products", products);

		List<AdvantageDto> advantages = advantageService.getListAdvantage();
		model.addAttribute("advantages", advantages);

		return "/frontend/index";
	}

	@GetMapping("/product")
	public String view(Model model) {
		List<ProductDto> products = productService.getListProduct();
		model.addAttribute("products", products);
		return "/frontend/product/view";
	}

	@GetMapping("/product/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {

		List<ProductDto> products = productService.getListProduct();

		ProductDto product = new ProductDto();
		for (ProductDto dto : products) {
			if (dto.getId().compareTo(id) == 0) {
				BeanUtils.copyProperties(dto, product);
			}
		}
		model.addAttribute("product", product);
		model.addAttribute("products", products);

		return "/frontend/product/detail";
	}

	@GetMapping("/about")
	public String about(Model model) {
		Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, Constant.DEFAULT_PAGE_SIZE,
				Sort.by("nameTab").ascending());
		AboutResponse abouts = aboutService.getPaggingAbout(Constant.DELETE_FLAG_ACTIVE, paging);
		for (AboutDto about : abouts.getAbouts()) {
			if (about.getPosition() == 1) {
				model.addAttribute("introduce", about);
			}

			if (about.getPosition() == 2) {
				model.addAttribute("history", about);
			}

			if (about.getPosition() == 3) {
				model.addAttribute("vision", about);
			}
		}

		return "/frontend/about";
	}

	@GetMapping("/contact")
	public String contact(Model model) {
		CustomerDto objectDto = new CustomerDto();
		model.addAttribute("customer", objectDto);
		return "/frontend/contact";
	}

	@RequestMapping(value = "/customer/add", method = RequestMethod.POST)
	public String doAdd(CustomerDto dto, Model model, RedirectAttributes redirect) {
		customerService.add(dto);
		CustomerDto objectDto = new CustomerDto();
		model.addAttribute("customer", objectDto);
//		model.addAttribute("message", "Thêm thành công");
		redirect.addFlashAttribute("message",
				"Cảm ơn quý khách đã liên hệ với chúng tôi. Chúng tôi sẽ phản hồi đến quý khách ngay sau khi chúng tôi tiếp nhận thông tin của quý khách");
		return "redirect:/contact";
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

}
