package io.superson.trelloproject.domain.board.dto;

import io.superson.trelloproject.domain.board.entity.Invite.InviteStatus;
import io.superson.trelloproject.global.util.Color;
import io.superson.trelloproject.global.util.validator.EnumSubsetOf;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InviteResultRequestDto {

    @EnumSubsetOf(enumClass = InviteStatus.class)
    private String status;
}
