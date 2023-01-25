package com.swamy.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swamy.dto.PostDTO;
import com.swamy.dto.PostResponseDTO;
import com.swamy.entity.Post;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.PostRepository;
import com.swamy.service.IPostService;
import com.swamy.utils.AppConstants;
import com.swamy.utils.EmailUtils;

@Service
public class PostServiceImpl implements IPostService {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public PostDTO savePost(PostDTO postDTO) {

		Post post = modelMapper.map(postDTO, Post.class);
		Post savedPost = postRepository.save(post);
		PostDTO savedPostResponse = modelMapper.map(savedPost, PostDTO.class);

//		String subject = AppConstants.SUBJECT;
//		String receiver = AppConstants.TO_ADDRESS;
//		String body = readMailBody(savedPostResponse);
//		String mailSent = emailUtils.sendEmail(subject, receiver, body);
//		LOG.info(mailSent);
//		System.out.println(mailSent);
		return savedPostResponse;
	}

	public String readMailBody(PostDTO post) {
		String mailBody = null;
		StringBuffer buffer = new StringBuffer();

		try {
			Path filePath = Paths.get(AppConstants.POST_MAIL_BODY_TEMPLATE);
			List<String> lines = Files.readAllLines(filePath);
			lines.forEach(line -> {
				buffer.append(line);
				buffer.append("\n");
			});

			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.USER, AppConstants.TO_ADDRESS);
			mailBody = mailBody.replace(AppConstants.POST_ID, post.getId().toString());
			mailBody = mailBody.replace(AppConstants.POST_TITLE, post.getTitle());
			mailBody = mailBody.replace(AppConstants.POST_CONTENT, post.getContent());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;

	}

	@Override
	public PostResponseDTO getAllPosts(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Post> pages = postRepository.findAll(pageable);

		List<Post> postsList = pages.getContent();

		List<PostDTO> content = postsList.stream().map(post -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponseDTO postResponse = new PostResponseDTO();
		postResponse.setContent(content);
		postResponse.setPageNo(pageNo);
		postResponse.setPageSize(pageSize);
		postResponse.setSortBy(sortBy);
		postResponse.setTotalElements(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		postResponse.setLast(pages.isLast());

		return postResponse;

	}

	@Override
	public PostDTO getOnePostById(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostDTO updatePostById(Integer postId, PostDTO postDTO) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		Post updatedPost = postRepository.save(post);
		PostDTO updatedPostResponse = modelMapper.map(updatedPost, PostDTO.class);
		return updatedPostResponse;
	}

	@Override
	public String deletePostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.POST_NOT_FOUND + postId));
		postRepository.delete(post);
		return AppConstants.POST_DELETE_SUCCESS + postId;
	}
}
