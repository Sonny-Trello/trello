package io.superson.trelloproject.domain.board.entity.Invite;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InviteStatus {
    STANDBY("StandBy"), ACCEPTED("Accepted"), REJECTED("Rejected");

    private final String status;
}
