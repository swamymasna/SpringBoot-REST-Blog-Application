package com.swamy.service;

import java.util.List;

import com.swamy.dto.CommentDTO;

public interface ICommentService {

	public CommentDTO saveCommentByPostId(Integer postId, CommentDTO commentDTO);

	public List<CommentDTO> getAllCommentsByPostId(Integer postId);

	public CommentDTO getOneCommentByPostId(Integer postId, Integer commentId);

	public CommentDTO updateCommentByPostId(Integer postId, Integer commentId, CommentDTO commentDTO);

	public String deleteCommentByPostId(Integer postId, Integer commentId);
}
