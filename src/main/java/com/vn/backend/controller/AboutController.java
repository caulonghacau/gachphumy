package com.vn.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {
	@RequestMapping("/admin/about")
	public String view() {
		return "/backend/about/view";
	}
}
