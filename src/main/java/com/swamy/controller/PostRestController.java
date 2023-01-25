package com.swamy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.PostDTO;
import com.swamy.dto.PostResponseDTO;
import com.swamy.service.IPostService;
import com.swamy.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "POST RESOURCE")
@RestController
@RequestMapping("/api/v1")
public class PostRestController {

	@Autowired
	private IPostService postService;

	@Operation(summary = "SAVE POST", description = "This Is To Save Post Data Into Database")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Saved Post Into Database", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Post Not Saved", content = @Content) })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/posts/save")
	public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
		PostDTO savedPost = postService.savePost(postDTO);
		return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
	}

	@Operation(summary = "GET ALL POSTS", description = "This Is To Fetch Get All Posts From Database")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Found Post Data", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Post Data Not Found ", content = @Content) })
	@GetMapping("/posts/list")
	public ResponseEntity<PostResponseDTO> getAllPosts(

			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = true) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = true) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = true) String sortBy) {

		PostResponseDTO posts = postService.getAllPosts(pageNo, pageSize, sortBy);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@Operation(summary = "GET ONE POST BY POST-ID", description = "This Is To Fetch Get One Post From Database")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Found Post Data", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Post Data Not Found ", content = @Content) })
	@GetMapping("/posts/post/{post-id}")
	public ResponseEntity<PostDTO> getOnePostById(@PathVariable(value = "post-id") Integer postId) {
		PostDTO post = postService.getOnePostById(postId);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@Operation(summary = "UPDATE POST BY POST-ID", description = "This Is To Update Post Data")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Updated Post Data", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Post Data Not Updated", content = @Content) })
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/posts/update/{post-id}")
	public ResponseEntity<PostDTO> updatePostById(@Valid @PathVariable(value = "post-id") Integer postId,
			@RequestBody PostDTO postDTO) {
		PostDTO post = postService.updatePostById(postId, postDTO);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@Operation(summary = "DELETE POST BY POST-ID", description = "This Is To Delete Post Data")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Deleted Post Data", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Post Data Not Deleted", content = @Content) })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/delete/{post-id}")
	public ResponseEntity<String> deletePostById(@PathVariable(value = "post-id") Integer postId) {
		String deletedPost = postService.deletePostById(postId);
		return new ResponseEntity<>(deletedPost, HttpStatus.OK);
	}

}
