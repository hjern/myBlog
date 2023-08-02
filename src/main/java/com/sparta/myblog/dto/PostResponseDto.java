package com.sparta.myblog.dto;

import java.time.LocalDateTime;

import com.sparta.myblog.entity.Post;
import com.sparta.myblog.entity.User;

import lombok.Getter;

@Getter
public class PostResponseDto {

	private Long id;
	private String userId;
	private String content;
	private LocalDateTime createAt; // 게시글 생성시간
	private LocalDateTime modifiedAt; // 게시글 수정시간

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.userId = post.getUser().getUserId();
		this.content = post.getContent();
		this.createAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
	}

}
