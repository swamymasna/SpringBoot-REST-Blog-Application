package com.swamy.service;

import com.swamy.dto.PostDTO;
import com.swamy.dto.PostResponseDTO;

public interface IPostService {

	public PostDTO savePost(PostDTO postDTO);

	public PostResponseDTO getAllPosts(Integer pageNo, Integer pageSize, String sortBy);

	public PostDTO getOnePostById(Integer postId);

	public PostDTO updatePostById(Integer postId, PostDTO postDTO);

	public String deletePostById(Integer postId);

}
