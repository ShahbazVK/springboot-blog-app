package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));		
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPost(Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if (sortDir.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePosts= this.postRepo.findAll(p);
		
		List<Post> allPosts = pagePosts.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setLastPage(pagePosts.isLast());		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		Page<Post> pagePosts= this.postRepo.findByCategory(category, p);
		
		List<Post> posts= pagePosts.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setLastPage(pagePosts.isLast());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setPageNumber(pagePosts.getNumber());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		
		Page<Post> pagePosts = this.postRepo.findByUser(user, p);
		
		List<Post> posts= pagePosts.getContent();

		List<PostDto> postDtos= posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		List<Post> posts = this.postRepo.findByTitleContaining(keywords);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
