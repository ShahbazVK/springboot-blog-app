package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto category) {
		Category cat = this.modelMapper.map(category,Category.class);
		Category addedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> catDtos= categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto category, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		cat.setCategoryTitle(category.getCategoryTitle());
		cat.setCategoryDescription(category.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		this.categoryRepo.delete(cat);
	}
	
}
