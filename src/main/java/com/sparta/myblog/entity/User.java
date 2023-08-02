package com.sparta.myblog.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class User extends Timestamped {

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

	@Column
	@Enumerated(value = EnumType.STRING) // Enum 타입을 DB에 저장할 때 쓰는 Annotation
	private UserRoleEnum role; // admin, user, manager

	public User(String userId, String password, String email, UserRoleEnum role) {
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.role = role;
	}
}
