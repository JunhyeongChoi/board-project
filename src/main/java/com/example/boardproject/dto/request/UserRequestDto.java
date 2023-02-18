package com.example.boardproject.dto.request;

import com.example.boardproject.domain.user.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRequestDto {

    @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$",
            message = "아이디는 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하이어야 합니다.")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$",
            message = "비밀번호는 최소 6글자이며 영문, 숫자, 특수문자 하나 이상을 포함해야 합니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(encode(password1))
                .email(email)
                .build();
    }

    public String encode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

}
