package com.sparta.myblog.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.myblog.dto.UserSignupDto;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "회원가입 서비스단")
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // 패스워드 인코더를 사용하기 위해 주입

	// 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
	// 비밀번호 확인은 비밀번호와 정확하게 일치하기
	// 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 노출
	public void userSignup (UserSignupDto signupDto){
		log.info("회원가입");
		String userId = signupDto.getUserId();
		String password = passwordEncoder.encode(signupDto.getPassword());
		// String passwordConfirm = passwordEncoder.encode(signupDto.getPasswordConfirm());
		String email = signupDto.getEmail();

		// 1. id 중복
		if(userRepository.findByUserId(userId).isPresent()){
			throw new IllegalArgumentException("이미 존재하는 ID 입니다.");

		// 2. email 중복
		} else if(userRepository.findByEmail(email).isPresent()){
			throw new IllegalArgumentException("이미 존재하는 Email 입니다.");

		// 3. password 불일치
		} else if(!signupDto.getPassword().equals(signupDto.getPasswordConfirm())){
			log.info("비밀번호 불일치 경우");
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

		// 4. password 내 아이디 사용
		} else if (signupDto.getPassword().contains(userId)){
			log.info("비밀번호 내 닉네임을 포함한 경우");
			throw new IllegalArgumentException("비밀번호는 닉네임을 포함할 수 없습니다.");

		}

		User user = new User(userId, password, email);
		userRepository.save(user);

	}


}
