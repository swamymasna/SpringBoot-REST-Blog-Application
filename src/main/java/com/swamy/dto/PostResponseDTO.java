package com.swamy.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostResponseDTO {

	private List<PostDTO> content;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private Integer totalPages;
	private Long totalElements;
	private boolean isLast;
}
