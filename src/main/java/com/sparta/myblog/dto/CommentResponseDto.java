package com.sparta.myblog.dto;

import java.time.LocalDateTime;

import com.sparta.myblog.entity.Comment;

public class CommentResponseDto {

	private Long id;
	private String userId;
	private String content;
	private LocalDateTime createAt; // 댓글 생성시간
	private LocalDateTime modifiedAt; // 댓글 수정시간

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.userId = comment.getUser().getUserId();
		this.content = comment.getComment();
		this.createAt = comment.getCreatedAt();
		this.modifiedAt = comment.getModifiedAt();
	}

}
