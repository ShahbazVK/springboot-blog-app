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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.entities.Post;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostService;
import com.blog.services.impl.PostServiceImpl;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost , HttpStatus.CREATED);
	}
	
	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto createPost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(createPost , HttpStatus.OK);
	}
	
//	Get posts by user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable Integer userId,
			@RequestParam(value="pageNumber", defaultValue = "1", required= false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required= false) Integer pageSize
			){
		PostResponse posts = this.postService.getPostsByUser(userId, pageNumber, pageSize);
		
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
//	Get posts by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber", defaultValue = "1", required= false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required= false) Integer pageSize
			){
		PostResponse posts = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
		
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
//	get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value ="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value ="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value ="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
		PostResponse posts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

//	get post by id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
		PostDto post = this.postService.getPost(postId);
		
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", true), HttpStatus.OK);
	}

//	find by keyword(title)
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> getAllPosts(
			@PathVariable("keywords") String keywords
			){
		List<PostDto> posts = this.postService.searchPosts(keywords);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
}
