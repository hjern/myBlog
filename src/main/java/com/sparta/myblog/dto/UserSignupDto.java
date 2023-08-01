package com.sparta.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserSignupDto {

	// 닉네임, 비밀번호, 비밀번호 확인을 request 에서 전달받기
	// 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
	// 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
	// 비밀번호 확인은 비밀번호와 정확하게 일치하기


	// ?			없거나 있거나 (zero or one)
	// "^[\\w]*$"	문자열만 허용하는 정규식 - 공백 미 허용
	// "^[\\d]*$"	숫자만 허용하는 정규식

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]{3,}$", message = "아이디는 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
	private String userId;

	@NotBlank
	@Size(min = 4, message = "비밀번호는 최소 4자 이상, ID를 포함할 수 없습니다.")
	private String password;

	@NotBlank
	private String passwordConfirm;

	private String email;

}
