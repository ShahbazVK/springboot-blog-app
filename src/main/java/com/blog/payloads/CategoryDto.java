package com.blog.payloads;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CategoryDto {
	
	private int categoryId;
	
	@NotBlank
	@Size(min=2, max=50)
	private String categoryTitle;
	
	@NotBlank
	@Size(min=2, max=50)
	private String categoryDescription;
}
