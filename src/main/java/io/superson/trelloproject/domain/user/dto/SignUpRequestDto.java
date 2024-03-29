package io.superson.trelloproject.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @Email
    String email;

    @NotBlank
    String password;
}
