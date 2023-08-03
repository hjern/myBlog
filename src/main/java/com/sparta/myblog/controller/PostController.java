package com.sparta.myblog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.PostService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostService postService;

	// 1. 글쓰기
	@PostMapping("/post")
	public ResponseEntity <ApiResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		return postService.createPost(requestDto, userDetails.getUser());
	}

	// 2. 전체 조회하기
	@GetMapping("/posts")
	public List <PostResponseDto> getPosts(){
		return postService.getPosts();
	}

	// 3. 개별 조회하기
	@GetMapping("/post/{postId}")
	public PostResponseDto getPost(@PathVariable Long postId, User user) {
		return postService.getPost(postId, user);
	}

	// 4. 수정하기
	@PutMapping("/post/{postId}")
	public ResponseEntity <ApiResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return postService.updatePost(postId,requestDto, userDetails.getUser());
	}

	// 5. 삭제하기
	@DeleteMapping("/post/{postId}")
	public ResponseEntity <ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return postService.deletePost(postId, userDetails.getUser());
	}


}
