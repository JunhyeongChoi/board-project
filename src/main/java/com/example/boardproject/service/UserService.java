package com.example.boardproject.service;

import com.example.boardproject.domain.User;
import com.example.boardproject.dto.request.UserRequestDto;
import com.example.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity();

        userRepository.save(user);
        return user;
    }

}
