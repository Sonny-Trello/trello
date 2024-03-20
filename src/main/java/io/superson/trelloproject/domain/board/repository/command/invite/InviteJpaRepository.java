package io.superson.trelloproject.domain.board.repository.command.invite;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteJpaRepository extends JpaRepository<Invite, Long> {

}
