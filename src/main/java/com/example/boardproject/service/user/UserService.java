package com.example.boardproject.service.user;

import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.UserRequestDto;
import com.example.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Member saveUser(UserRequestDto userRequestDto) {
        Member member = userRequestDto.toEntity();

        userRepository.save(member);
        return member;
    }

}
