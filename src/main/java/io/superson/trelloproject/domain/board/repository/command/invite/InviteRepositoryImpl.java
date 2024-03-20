package io.superson.trelloproject.domain.board.repository.command.invite;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Invite findById(Long id) {
        return inviteJpaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 초대입니다."));
    }
}
