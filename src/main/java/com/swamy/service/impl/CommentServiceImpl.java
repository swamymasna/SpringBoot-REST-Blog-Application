package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.dto.CommentDTO;
import com.swamy.entity.Comment;
import com.swamy.entity.Post;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.CommentRepository;
import com.swamy.repository.PostRepository;
import com.swamy.service.ICommentService;
import com.swamy.utils.AppConstants;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO saveCommentByPostId(Integer postId, CommentDTO commentDTO) {
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		CommentDTO savedCommentResponse = modelMapper.map(savedComment, CommentDTO.class);
		return savedCommentResponse;
	}

	@Override
	public List<CommentDTO> getAllCommentsByPostId(Integer postId) {

		List<Comment> commentsList = commentRepository.findByPostId(postId);
		return commentsList.stream().map(comment -> modelMapper.map(comment, CommentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public CommentDTO getOneCommentByPostId(Integer postId, Integer commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT_NOT_FOUND + commentId));

		return modelMapper.map(comment, CommentDTO.class);
	}

	@Override
	public CommentDTO updateCommentByPostId(Integer postId, Integer commentId, CommentDTO commentDTO) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT_NOT_FOUND + commentId));
		
		comment.setName(commentDTO.getName());
		comment.setBody(commentDTO.getBody());
		comment.setPost(post);
		Comment updatedComment = commentRepository.save(comment);
		return modelMapper.map(updatedComment, CommentDTO.class);
	}

	@Override
	public String deleteCommentByPostId(Integer postId, Integer commentId) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.COMMENT_NOT_FOUND + commentId));

		commentRepository.delete(comment);
		
		return AppConstants.COMMENT_DELETE_SUCCESS + commentId;
	}
}
