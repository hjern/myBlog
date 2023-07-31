package com.sparta.myblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.myblog.dto.ApiResponseDto;
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
		try {
			userService.userSignup(signupDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
		} return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
	}

}
