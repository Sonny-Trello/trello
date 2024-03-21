package io.superson.trelloproject.domain.user.dto;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import io.superson.trelloproject.domain.board.entity.Invite.InviteStatus;
import lombok.Getter;

@Getter
public class UserInviteResponseDto {

    private final Long inviteId;
    private final InviteStatus status;
    private final Long boardId;

    public UserInviteResponseDto(Invite invite) {
        this.inviteId = invite.getInviteId();
        this.status = invite.getStatus();
        this.boardId = invite.getBoardId();
    }
}
