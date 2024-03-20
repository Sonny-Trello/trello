package io.superson.trelloproject.domain.user.dto;

import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {

    private String presentPassword;
    private String newPassword;
    private String checkPassword;
}
