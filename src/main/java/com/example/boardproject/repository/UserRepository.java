package com.example.boardproject.repository;

import com.example.boardproject.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

}
