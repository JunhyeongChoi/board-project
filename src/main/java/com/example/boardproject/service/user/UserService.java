package com.example.boardproject.service.user;

import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.UserRequestDto;
import com.example.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Member saveUser(UserRequestDto userRequestDto) {
        Member member = userRequestDto.toEntity();

        userRepository.save(member);
        return member;
    }

    public Member getMember(String username) {
        Optional<Member> member = userRepository.findByUsername(username);

        if (member.isPresent()) {
            return member.get();
        } else {
            throw new EntityNotFoundException("유저를 찾을 수 없습니다.");
        }
    }
    
}
