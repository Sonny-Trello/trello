package io.superson.trelloproject.domain.board.repository.command.invite;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;

public interface InviteRepository {

    Invite save(Invite invite);

    Invite findById(Long id);
}
