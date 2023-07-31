package com.sparta.myblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.myblog.entity.User;

public interface UserRepository extends JpaRepository <User, Long> {
	Optional<User> findByUserId(String userId);

	Optional<User> findByEmail(String email);
}
