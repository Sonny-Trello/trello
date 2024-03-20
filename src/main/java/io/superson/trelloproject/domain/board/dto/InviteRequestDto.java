package io.superson.trelloproject.domain.board.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class InviteRequestDto {

    @Email
    private String email;
}
