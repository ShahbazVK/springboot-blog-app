package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostDto getPost(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir );
	
//	Get all posts by category
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
//	Get all posts by user
	PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
//	Search by keyword
	List<PostDto> searchPosts(String keywords);
}
