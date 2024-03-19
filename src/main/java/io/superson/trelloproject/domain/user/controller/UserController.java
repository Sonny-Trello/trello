package io.superson.trelloproject.domain.user.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.user.dto.LoginRequestDto;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import io.superson.trelloproject.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> signUp(
        @RequestBody @Validated SignUpRequestDto requestDto) {
        userService.signUp(requestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(
        @RequestBody @Validated LoginRequestDto requestDto,
        HttpServletResponse httpServletResponse) {
        userService.login(requestDto, httpServletResponse);

        return ResponseEntity.ok().build();
    }
}
