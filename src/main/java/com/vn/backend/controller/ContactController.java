package com.vn.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {
	@RequestMapping("/admin/contact")
	public String view() {
		return "/backend/contact/view";
	}
}
