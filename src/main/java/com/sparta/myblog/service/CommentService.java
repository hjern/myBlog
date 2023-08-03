package com.sparta.myblog.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.entity.Comment;
import com.sparta.myblog.entity.Post;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "댓글 작성 테스트")
@RequiredArgsConstructor
@Service
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	// 1. 댓글 작성하기
	public ResponseEntity <ApiResponseDto> addComment (Long postId, CommentRequestDto requestDto, User user) {

		Post post = postRepository.findById(postId).orElse(null);

		if (post == null) {
			log.error("게시글이 존재하지 않음");
			return ResponseEntity.status(400).body(new ApiResponseDto("댓글을 작성할 게시물이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		log.info("권한 있는 사용자의 접근");
		Comment comment = commentRepository.save(new Comment(post, user, requestDto));
		return ResponseEntity.status(200).body(new ApiResponseDto("댓글 작성에 성공했습니다.", HttpStatus.OK.value()));
	}

	// 2. 댓글 수정하기

	// 3. 댓글 삭제하기
	@Transactional
	public ResponseEntity <ApiResponseDto> deleteComment (Long commentId, User user) {

		Comment comment = commentRepository.findById(commentId).orElse(null);

		// 게시글 사용 권한 확인하기
		if (comment == null) {
			log.error("삭제할 댓글이 존재하지 않음");
			return ResponseEntity.status(400).body(new ApiResponseDto("삭제할 댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));

		} else if (!comment.getUser().getUserId().equals(user.getUserId())) {
			log.error("권한 없는 사용자의 접근");
			return ResponseEntity.status(400).body(new ApiResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		commentRepository.delete(comment);
		return ResponseEntity.status(200).body(new ApiResponseDto("댓글 삭제가 완료되었습니다.", HttpStatus.OK.value()));

	}

}
