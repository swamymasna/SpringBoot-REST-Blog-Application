package com.swamy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByPostId(Integer postId);
}
