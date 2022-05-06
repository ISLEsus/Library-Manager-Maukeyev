package kz.iitu.librarymanager.service;

import java.util.List;

import kz.iitu.librarymanager.model.Category;

public interface CategoryService {

	public List<Category> findAllCategories();

	public Category findCategoryById(Long id);

	public void createCategory(Category category);

	public void updateCategory(Category category);

	public void deleteCategory(Long id);

}
