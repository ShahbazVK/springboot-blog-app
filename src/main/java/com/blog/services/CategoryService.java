package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto category);
	
	CategoryDto getCategory(Integer categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(CategoryDto category, Integer categoryId);
	
	void deleteCategory(Integer categoryId);
}
