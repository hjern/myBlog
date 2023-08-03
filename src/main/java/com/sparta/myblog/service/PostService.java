package com.sparta.myblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.entity.Post;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "게시글 작성 테스트")
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;

	// 1. 글쓰기
	public ResponseEntity<ApiResponseDto> createPost(PostRequestDto requestDto, User user) {

		// 권한 없는 사용자의 접근
		if(user == null){
			log.error("권한 없는 사용자의 접근");
			return ResponseEntity.status(400).body(new ApiResponseDto("허가되지 않은 사용자입니다.", HttpStatus.BAD_REQUEST.value()));
		}

		log.info("권한 있는 사용자의 접근");
		Post post = new Post(requestDto, user);
		postRepository.save(post);

		return ResponseEntity.status(200).body(new ApiResponseDto("게시글 작성에 성공했습니다.", HttpStatus.OK.value()));
	}

	// 2. 전체 조회하기 -- PostResponseDto 가 리스트화할 수 있도록 메서드 선언
	@Transactional
	public List <PostResponseDto> getPosts() {
		// Post entity 를 리스트화한다는 것은 postRepository 에 저장된 DB 를 생성된 순으로 목록화한다는 의미
		List <Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
		// PostResponseDto 가 새 배열로 선언
		List <PostResponseDto> postResponseDto = new ArrayList<>();

		// Post entity 에 저장된 DB 들을 생성된 순으로 목록화하여 DB가 끝날 때까지
		for (Post post : posts) {
			// 새로운 배열인 postResponseDto 에 순차적으로 담기
			postResponseDto.add(new PostResponseDto(post));
		}

		// DB post 를 순차적으로 담아 만든 새로운 배열을 리턴하므로써 종료
		return postResponseDto;
	}

	// 3. 개별 조회하기
	@Transactional
	public PostResponseDto getPost(Long id, User user) {

		// 게시글 존재 확인하기
		Post post = postRepository.findById(id).orElseThrow(
			()-> new IllegalArgumentException("선택한 게시물은 존재하지 않습니다."));

		return new PostResponseDto(post);
	}

	// 4. 수정하기
	@Transactional
	public ResponseEntity <ApiResponseDto> updatePost(Long id, PostRequestDto requestDto, User user) {

		Post post = postRepository.findById(id).orElse(null);

		// 게시글 사용 권한 확인하기
		if (post == null) {
			log.error("수정할 게시물이 존재하지 않음");
			return ResponseEntity.status(400).body(new ApiResponseDto("수정할 게시물이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));

		} else if (!post.getUser().getUserId().equals(user.getUserId())){
			log.error("권한 없는 사용자의 접근");
			return ResponseEntity.status(400).body(new ApiResponseDto("권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));

		}

		post.update(requestDto.getContent());
		return ResponseEntity.status(200).body(new ApiResponseDto("게시물 수정이 완료되었습니다.", HttpStatus.OK.value()));
	}

	// 5. 삭제하기
	@Transactional
	public ResponseEntity <ApiResponseDto> deletePost(Long id, User user) {

		Post post = postRepository.findById(id).orElse(null);

		// 게시글 사용 권한 확인하기
		if (post == null) {
			log.error("삭제할 게시물이 존재하지 않음");
			return ResponseEntity.status(400)
				.body(new ApiResponseDto("삭제할 게시물이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));

		} else if (!post.getUser().getUserId().equals(user.getUserId())) {
			log.error("권한 없는 사용자의 접근");
			return ResponseEntity.status(400).body(new ApiResponseDto("권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));

		}

		return ResponseEntity.status(200).body(new ApiResponseDto("게시물 삭제가 완료되었습니다.", HttpStatus.OK.value()));

	}
}
