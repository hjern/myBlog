package com.sparta.myblog.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

	@Id
	// 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	// 오라클 = 시퀀스, MySQL = auto_increment
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String userId;

	@Column(nullable = false)
	private String password;

	@Column
	private String passwordConfirm;

	@Column(nullable = false, unique = true)
	private String email;

	// @ColumnDefault("'user'")
	// private String role; // admin, user, manager

	@CreationTimestamp // 시간 자동 입력
	private Timestamp createDate;

	public User(String userId, String password, String email) {
		this.userId = userId;
		this.password = password;
		this.email = email;
	}
}
