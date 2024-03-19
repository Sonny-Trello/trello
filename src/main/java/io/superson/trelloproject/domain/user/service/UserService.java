package io.superson.trelloproject.domain.user.service;

import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import io.superson.trelloproject.domain.user.dto.SignUpResponseDto;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
    String email = requestDto.getEmail();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
    }

    User user = new User(requestDto, passwordEncoder);
    userRepository.save(user);

    return SignUpResponseDto.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .build();
  }
}
