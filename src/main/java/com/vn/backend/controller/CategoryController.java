package com.vn.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/category")
	public String view(Model model) {

		List<CategoryDto> categories = categoryService.getListAll(0);

		model.addAttribute("categories", categories);
		return "/backend/category/viewCategory";

	}

	public String detail(Model model) {
		return "/backend/category/detailCategory";

	}

	@RequestMapping(value = "/category/add", method = RequestMethod.GET)
	public String add(Model model) {
		CategoryDto categoryDto = new CategoryDto();
		model.addAttribute("categoryDto", categoryDto);

		return "/backend/category/addCategory";

	}

	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String doAdd(CategoryDto categoryDto, Model model, RedirectAttributes redirect) {
		CategoryDto dto = categoryService.add(categoryDto);
		redirect.addFlashAttribute("successMessage", "Thêm mới danh mục thành công!");
		return "redirect:/admin/category/";

	}

	@RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		CategoryDto categoryDto = categoryService.getDetail(id, 0);

		model.addAttribute("categoryDto", categoryDto);
		return "/backend/category/editCategory";

	}

	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public String doUpdate(CategoryDto categoryDto, Model model, RedirectAttributes redirect) {

		try {
			CategoryDto t = categoryService.update(categoryDto);
			redirect.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
			return "redirect:/admin/category/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/category/";
	}

	@RequestMapping(value = "/category/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			boolean categoryDto = categoryService.delete(id);
			redirect.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
			return "redirect:/admin/category/";
		} catch (Exception e) {

			return "/backend/category/editCategory";
		}
	}

}
