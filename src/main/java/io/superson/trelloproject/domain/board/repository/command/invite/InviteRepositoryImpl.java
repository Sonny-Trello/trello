package io.superson.trelloproject.domain.board.repository.command.invite;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InviteRepositoryImpl implements InviteRepository {

    private final InviteJpaRepository inviteJpaRepository;

    @Override
    public Invite save(Invite invite) {
        return inviteJpaRepository.save(invite);
    }
}
