package com.sparta.myblog.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;

@Getter
// JPA Entity Classe 들이 아래 추상클래스를 상속할 경우, 추상클래스 내 멤버 변수를 column 으로 인식시키는 annotation
@MappedSuperclass
// Timestamped class 에 auditing 기능을 넣어 줌
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

	@CreatedDate
	// Update 가 되지 않도록 막음
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedAt;
}