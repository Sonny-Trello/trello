package io.superson.trelloproject.domain.user.service;

import io.superson.trelloproject.domain.user.dto.LoginRequestDto;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import io.superson.trelloproject.domain.user.dto.UserResponseDto;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import io.superson.trelloproject.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signUp(SignUpRequestDto requestDto) {
        String email = requestDto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        User user = new User(requestDto, passwordEncoder);
        userRepository.save(user);

    }

    public void login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        String email = requestDto.getEmail();
        User foundUser = userRepository.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("이메일과 비밀번호를 다시 확인해주세요.")
        );
        String password = requestDto.getPassword();
        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new IllegalArgumentException("이메일과 비밀번호를 다시 확인해주세요.");
        }
        String token = jwtUtil.createToken(foundUser);
        httpServletResponse.setHeader(jwtUtil.AUTHORIZATION_HEADER, token);
    }

    public UserResponseDto getUserInfo(String userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        return new UserResponseDto(foundUser);
    }

}
