package com.sparta.myblog.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.myblog.dto.ApiResponseDto;
import com.sparta.myblog.dto.LoginRequestDto;
import com.sparta.myblog.dto.UserSignupDto;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.entity.UserRoleEnum;
import com.sparta.myblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "회원가입 및 로그인 서비스")
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // 패스워드 인코더를 사용하기 위해 주입

	// 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
	// 비밀번호 확인은 비밀번호와 정확하게 일치하기
	// 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 노출
	public ResponseEntity <ApiResponseDto> userSignup (UserSignupDto signupDto){
		log.info("회원가입 시도");
		String userId = signupDto.getUserId();
		String password = passwordEncoder.encode(signupDto.getPassword());
		// String passwordConfirm = passwordEncoder.encode(signupDto.getPasswordConfirm());
		String email = signupDto.getEmail();

		// 1. id 중복
		if(userRepository.findByUserId(userId).isPresent()){
			log.error("이미 존재하는 ID 입니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("이미 존재하는 ID 입니다.",HttpStatus.BAD_REQUEST.value()));

		// 2. email 중복
		} else if(userRepository.findByEmail(email).isPresent()){
			log.error("이미 존재하는 Email 입니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("이미 존재하는 Email 입니다.",HttpStatus.BAD_REQUEST.value()));

		// 3. password 불일치
		} else if(!signupDto.getPassword().equals(signupDto.getPasswordConfirm())){
			log.error("비밀번호가 일치하지 않습니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("비밀번호가 일치하지 않습니다.",HttpStatus.BAD_REQUEST.value()));

		// 4. password 내 아이디 사용
		} else if (signupDto.getPassword().contains(userId)){
			log.error("비밀번호는 닉네임을 포함할 수 없습니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("비밀번호는 닉네임을 포함할 수 없습니다.",HttpStatus.BAD_REQUEST.value()));

		}

		User user = new User(userId, password, email, UserRoleEnum.USER);
		userRepository.save(user);
		return ResponseEntity.status(200).body(new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.OK.value()));
	}


	// 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤,
	// 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 response 에 포함하기
	public ResponseEntity <ApiResponseDto> login (LoginRequestDto loginRequestDto) {
		log.info("로그인 시도");
		String userId = loginRequestDto.getUserId();
		User enrolledUser = userRepository.findByUserId(userId).orElse(null);
		String password = loginRequestDto.getPassword();

		if (enrolledUser == null || !passwordEncoder.matches(password, enrolledUser.getPassword())) {
			log.error("아이디 또는 패스워드를 확인해주세요.");

			// 1.
			return ResponseEntity.status(400).body(new ApiResponseDto("아이디 또는 패스워드를 확인해주세요.",HttpStatus.BAD_REQUEST.value()));

			// 2.
			// throw new IllegalArgumentException("아이디 또는 패스워드를 확인해주세요.");

		}

		return ResponseEntity.status(200).body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
	}
}
