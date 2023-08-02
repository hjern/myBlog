package com.sparta.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {

	@NotBlank
	private String content;

}
