package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl; 
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
		CategoryDto cat = this.categoryServiceImpl.createCategory(category);
		return new ResponseEntity<CategoryDto>(cat,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category, @PathVariable Integer catId){
		CategoryDto cat = this.categoryServiceImpl.updateCategory(category, catId);
		return new ResponseEntity<CategoryDto>(cat,HttpStatus.OK );
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryServiceImpl.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfully",true), HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto cat = this.categoryServiceImpl.getCategory(catId);
		return new ResponseEntity<CategoryDto>(cat, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> cats = this.categoryServiceImpl.getAllCategories();
		return ResponseEntity.ok(cats);
	}
}
