package com.swamy.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.swamy.entity.Comment;

import lombok.Data;

@Data
public class PostDTO {

	private Integer id;

	@NotEmpty(message = "Title must not be Empty or Null")
	@Size(min = 2, message = "Title should have atleast 2 charecters")
	private String title;

	@NotEmpty(message = "Content must not be Empty or Null")
	@Size(min = 2, message = "Content should have atleast 2 charecters")
	private String content;
	
	private Set<Comment> comments;
}
