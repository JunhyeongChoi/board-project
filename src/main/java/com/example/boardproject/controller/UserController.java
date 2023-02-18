package com.example.boardproject.controller;

import com.example.boardproject.dto.request.UserRequestDto;
import com.example.boardproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // signup 에서 검증 때문에 userRequestDto 가 필요하므로 파라미터로 지정
    @GetMapping("/signup")
    public String signup(UserRequestDto userRequestDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!userRequestDto.getPassword1().equals(userRequestDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "signup";
        }

        // 중복 유저(username, email) 예외처리
        try {
            userService.saveUser(userRequestDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("userDuplicated", "아이디 또는 이메일이 이미 존재합니다.");
            return "signup";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }
        
        return "redirect:/";
    }
}
