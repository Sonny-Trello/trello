package io.superson.trelloproject.domain.user.dto;

import lombok.Builder;

@Builder
public class SignUpResponseDto {

    String userId;
    String email;
}
