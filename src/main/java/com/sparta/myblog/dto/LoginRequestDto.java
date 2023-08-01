package com.sparta.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank
	private String userId;

	@NotBlank
	private String password;
}
