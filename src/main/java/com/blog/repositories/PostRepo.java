package com.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	Page<Post> findByUser(User user, Pageable p);
	
	Page<Post> findByCategory(Category category, Pageable p);
	
//	@Query("SELECT * FROM post p where p.title like :key")
//	List<Post> searchByTitle(@Param("key") String title);
	
	List<Post> findByTitleContaining(String title);

}
