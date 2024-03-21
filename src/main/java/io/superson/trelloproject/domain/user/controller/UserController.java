package io.superson.trelloproject.domain.user.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.user.dto.LoginRequestDto;
import io.superson.trelloproject.domain.user.dto.PasswordUpdateRequestDto;
import io.superson.trelloproject.domain.user.dto.SignUpRequestDto;
import io.superson.trelloproject.domain.user.dto.UserInviteResponseDto;
import io.superson.trelloproject.domain.user.dto.UserResponseDto;
import io.superson.trelloproject.domain.user.service.UserService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        @RequestBody @Validated SignUpRequestDto requestDto
    ) {
        userService.signUp(requestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(
        @RequestBody @Validated LoginRequestDto requestDto,
        HttpServletResponse httpServletResponse
    ) {
        userService.login(requestDto, httpServletResponse);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserInfo(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserResponseDto userResponseDto = userService.getUserInfo(
            userDetails.getUser().getUserId());
        return ResponseEntity.ok().body(ResponseDto.<UserResponseDto>builder()
            .data(userResponseDto)
            .build());
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updatePassword(
        @RequestBody PasswordUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.passwordUpdate(requestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(
        HttpServletResponse httpServletResponse
    ) {
        userService.logout(httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto<Void>> withdraw(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.withdraw(userDetails.getUser().getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invites")
    public ResponseEntity<ResponseDto<List<UserInviteResponseDto>>> getInvitations(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<UserInviteResponseDto> inviteList = userService.getInvitations(userDetails.getUser());
        return ResponseEntity.ok().body(
            ResponseDto.<List<UserInviteResponseDto>>builder()
                .data(inviteList)
                .build());
    }
}
