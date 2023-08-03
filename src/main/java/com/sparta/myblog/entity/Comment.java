package com.sparta.myblog.entity;

import com.sparta.myblog.dto.CommentRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	@Column(nullable = false, length = 200)
	private String comment;

	public Comment (Post post, User user, CommentRequestDto requestDto){
		this.post = post;
		this.user = user;
		this.comment = requestDto.getComment();
	}

	public void update(String comment) {
		this.comment = comment;
	}
}
