package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.CommentDTO;
import com.swamy.service.ICommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "COMMENT RESOURCE")
@RestController
@RequestMapping("/api/v1")
public class CommentRestController {

	@Autowired
	private ICommentService commentService;

	@Operation(summary = "SAVE COMMENT BY POST-ID", description = "This Is To Save Comment By Post Id")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Saved Comment", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Comment Not Saved", content = @Content) })
	@PostMapping("/post/{post-id}/comment/save")
	public ResponseEntity<CommentDTO> saveCommentByPostId(@PathVariable(value = "post-id") Integer postId,
			@RequestBody CommentDTO commentDTO) {

		CommentDTO savedComment = commentService.saveCommentByPostId(postId, commentDTO);
		return new ResponseEntity<CommentDTO>(savedComment, HttpStatus.CREATED);
	}

	@Operation(summary = "GET ALL COMMENTS BY POST-ID", description = "This Is To Fetch Get All Comments By Post Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Found Comments", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Comments Not Found", content = @Content) })
	@GetMapping("/post/{post-id}/comment/list")
	public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable(value = "post-id") Integer postId) {

		List<CommentDTO> comments = commentService.getAllCommentsByPostId(postId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@Operation(summary = "GET ONE COMMENT BY POST-ID AND COMMENT-ID", description = "This Is To Fetch Get All Comments By Post Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Found Comment", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Comment Not Found", content = @Content) })
	@GetMapping("/post/{post-id}/comment/{comment-id}")
	public ResponseEntity<CommentDTO> getOneCommentByPostId(@PathVariable(value = "post-id") Integer postId,
			@PathVariable(value = "comment-id") Integer commentId) {

		CommentDTO comment = commentService.getOneCommentByPostId(postId, commentId);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	@Operation(summary = "UPDATE COMMENT BY POST-ID AND COMMENT-ID", description = "This Is To Update Get Comments By Post Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Updated Comment", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Unable to Update Comment", content = @Content) })
	@PutMapping("/post/{post-id}/comment/update/{comment-id}")
	public ResponseEntity<CommentDTO> updateCommentByPostId(@PathVariable(value = "post-id") Integer postId,
			@PathVariable(value = "comment-id") Integer commentId, @RequestBody CommentDTO commentDTO) {

		CommentDTO updatedComment = commentService.updateCommentByPostId(postId, commentId, commentDTO);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	@Operation(summary = "DELETE COMMENT BY POST-ID AND COMMENT-ID", description = "This Is To Delete Comments By Post Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Deleted Comment", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "404", description = "Unable to Delete Comment", content = @Content) })
	@DeleteMapping("/post/{post-id}/comment/delete/{comment-id}")
	public ResponseEntity<String> deleteCommentByPostId(@PathVariable(value = "post-id") Integer postId,
			@PathVariable(value = "comment-id") Integer commentId) {

		String deletedComment = commentService.deleteCommentByPostId(postId, commentId);
		return new ResponseEntity<>(deletedComment, HttpStatus.OK);
	}
}