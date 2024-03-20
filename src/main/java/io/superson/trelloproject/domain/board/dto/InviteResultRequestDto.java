package io.superson.trelloproject.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InviteResultRequestDto {

    @NotBlank
    private String status;
}
