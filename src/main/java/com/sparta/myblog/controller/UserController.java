package com.sparta.myblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.LoginRequestDto;
import com.sparta.myblog.dto.UserSignupDto;
import com.sparta.myblog.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/user/signup")
	public ResponseEntity <ApiResponseDto> signup (@Valid @RequestBody UserSignupDto signupDto){
		return userService.userSignup(signupDto);
	}

	@PostMapping("/user/login")
	public ResponseEntity <ApiResponseDto> login (@RequestBody LoginRequestDto loginDto){
		// 1. Service 에서 보낸 응답값으로 반환하기
		return userService.login(loginDto);

		// 2. controller 에서 응답값 보내기
		// try {
		// 	userService.login(loginDto);
		// } catch (IllegalArgumentException e) {
		// 	return ResponseEntity.ok().body(new ApiResponseDto("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
		// } return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.CREATED.value()));
	}




}
