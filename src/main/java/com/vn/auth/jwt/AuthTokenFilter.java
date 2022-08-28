package com.vn.auth.jwt;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vn.auth.service.UserDetailsServiceImpl;
import com.vn.backend.model.Category;
import com.vn.backend.model.Contact;
import com.vn.backend.model.Menu;
import com.vn.backend.model.Slice;
import com.vn.backend.model.Vendor;
import com.vn.backend.repository.CategoryRepository;
import com.vn.backend.repository.ContactRepository;
import com.vn.backend.repository.MenuRepository;
import com.vn.backend.repository.SliceRepository;
import com.vn.backend.repository.VendorRepository;
import com.vn.utils.Constant;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private ObjectFactory<HttpSession> httpSessionFactory;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private SliceRepository sliceRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private VendorRepository vendorRepository;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			List<String> notes = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
			String jwt = null;
			if (notes != null && notes.size() > 0) {
				jwt = notes.get(0);
			}
			String getContextPath = request.getContextPath();
			String getPathInfo = request.getPathInfo();
			String getRequestURI = request.getRequestURI();
			String getServletPath = request.getServletPath();

			if (!getServletPath.contains("/admin")) {
				setMenuSession(request);
				setContactSession(request);
				setSliceSession(request);
				setCategorySession(request);
				setVendorSession(request);
			}

			String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build()
					.toUriString();

//			if (getServletPath.contains("/admin") && jwt == null && !jwtUtils.validateJwtToken(jwt)) {
//				response.sendRedirect("/login");
//			}

			// String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private String parseJwtInSession(String headerAuth) {

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private void setMenuSession(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<Menu> sessions = (List<Menu>) request.getSession().getAttribute(Constant.MENU_SESSION);
		if (sessions == null) {
			sessions = menuRepository.findAll();
			request.getSession().setAttribute(Constant.MENU_SESSION, sessions);
		}
	}

	private void setContactSession(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Contact sessions = (Contact) request.getSession().getAttribute(Constant.CONTACT_SESSION);
		if (sessions == null) {
			sessions = contactRepository.findAll().get(0);
			request.getSession().setAttribute(Constant.CONTACT_SESSION, sessions);
		}
	}

	private void setSliceSession(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<Slice> sessions = (List<Slice>) request.getSession().getAttribute(Constant.SLICE_SESSION);
		if (sessions == null) {
			sessions = sliceRepository.findByType(0);
			request.getSession().setAttribute(Constant.SLICE_SESSION, sessions);
		}
	}

	private void setCategorySession(HttpServletRequest request) {
//		@SuppressWarnings("unchecked")
//		List<Category> sessions = (List<Category>) request.getSession().getAttribute(Constant.CATEGORY_SESSION);
//		if (sessions == null) {
//			sessions = categoryRepository.findAll();
//			request.getSession().setAttribute(Constant.CATEGORY_SESSION, sessions);
//		}
		List<Category> sessions = categoryRepository.findAll();
		request.getSession().setAttribute(Constant.CATEGORY_SESSION, sessions);
	}

	private void setVendorSession(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<Vendor> sessions = (List<Vendor>) request.getSession().getAttribute(Constant.VENDOR_SESSION);
//		if (sessions == null) {
//			sessions = vendorRepository.findAll();
//			request.getSession().setAttribute(Constant.VENDOR_SESSION, sessions);
//		}
		sessions = vendorRepository.findAll();
		request.getSession().setAttribute(Constant.VENDOR_SESSION, sessions);
	}

}
