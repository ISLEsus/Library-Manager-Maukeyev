package kz.iitu.librarymanager.controller;

import java.util.List;

import kz.iitu.librarymanager.model.Category;
import kz.iitu.librarymanager.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {

	//Initializing the Category Service for the Category Controller
	private final CategoryService categoryService;
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	//List all Categories
	@RequestMapping("/categories")
	public String findAllCategories(Model model) {
		final List<Category> categories = categoryService.findAllCategories();
		model.addAttribute("categories", categories);
		return "list-categories"; //html
	}

	//Fetch a specific Category by its ID number
	@RequestMapping("/category/{id}")
	public String findCategoryById(@PathVariable("id") Long id, Model model) {
		final Category category = categoryService.findCategoryById(id);
		model.addAttribute("category", category);
		return "list-category"; //html
	}

	//Add a new Category to the database
	@RequestMapping("/add-category")
	public String createCategory(Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			//Loop back on itself in case of an error.
			return "add-category"; //html
		}
//		--->   proceed   --->
		categoryService.createCategory(category);
		model.addAttribute("category", categoryService.findAllCategories());
		//Throw to "/categories" in case of success; This will display all Categories from the database.
		return "redirect:/categories";
	}

	//Change an existing Category; Requires specific ID for reference.
	@GetMapping("/updateCategory/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		final Category category = categoryService.findCategoryById(id);
		model.addAttribute("category", category);
		return "update-category"; //html
	}

	//Change an existing Category; Requires a specific ID for the reference.
	@RequestMapping("/update-category/{id}")
	public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			category.setId(id);
			//Loop back on itself in case of an error.
			return "update-category";
		}
//		--->   proceed   --->
		categoryService.updateCategory(category);
		model.addAttribute("category", categoryService.findAllCategories()); //Uses another method from the same class; ISN'T IT COOL?
		//Throw to "/categories" in case of success; This will display all Categories from the database.
		return "redirect:/categories";
	}

	//Remove an existing Category; Requires a specific ID for the reference.
	@RequestMapping("/remove-category/{id}")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryService.deleteCategory(id);
		model.addAttribute("category", categoryService.findAllCategories());
		//Throw to "/categories" to display a fresh list of Categories from the database.
		return "redirect:/categories";
	}

}
