package io.superson.trelloproject.domain.board.dto;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InviteResponseDto {

    private Long inviteId;
    private String userId;
    private String status;
    private Long boardId;

    public InviteResponseDto(Invite invite) {
        this.inviteId = invite.getInviteId();
        this.userId = invite.getUserId();
        this.status = String.valueOf(invite.getStatus());
        this.boardId = invite.getBoardId();
    }
}
