package io.superson.trelloproject.domain.user.repository.query;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import io.superson.trelloproject.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {

    List<Invite> findAllInviteByUserId(String userId);

    User findByUserAndComment(String userId, Long commentId);
}
