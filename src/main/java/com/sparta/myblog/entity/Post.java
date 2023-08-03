package com.sparta.myblog.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.myblog.dto.PostRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post extends Timestamped{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Many = Post, One = User
	// DB 를 부를 때, 각 DB 당 단 건(manyTo"One")이므로 기본 전략으로 즉시 로딩 EAGER
	// 자주 함께 사용하는 DB 에게 지정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@Column(nullable = false, length = 500)
	private String content;

	// post 를 연관관계의 주인으로 설정. post 엔티티 제거시 연관된 comment 제거.
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List <Comment> commentList = new ArrayList<>();

	public Post(PostRequestDto requestDto, User user){
		this.user = user;
		this.content = requestDto.getContent();
	}

	public void update(String content) {
		this.content = content;
	}
}
