package com.sparta.myblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

	private final CommentService commentService;

	// 1. 댓글 작성하기
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity <ApiResponseDto> addComment
	(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

		return commentService.addComment(postId, requestDto, userDetails.getUser());
	}

	// 2. 댓글 수정하기

	// 3. 댓글 삭제하기
	@DeleteMapping("/post/{postId}/comment/{commentId}")
	public ResponseEntity <ApiResponseDto> deleteComment
	(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

		return commentService.deleteComment(commentId, userDetails.getUser());
	}



}
