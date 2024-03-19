package io.superson.trelloproject.domain.user.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import io.superson.trelloproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        @RequestBody @Valid SignUpRequestDto requestDto) {
        userService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(null);
    }
}
