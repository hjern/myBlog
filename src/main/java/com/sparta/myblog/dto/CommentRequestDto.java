package com.sparta.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

	@NotBlank
	private String comment;

}
