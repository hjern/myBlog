package com.sparta.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.myblog.entity.Comment;

public interface CommentRepository extends JpaRepository < Comment, Long > {

}
