package io.superson.trelloproject.domain.user.repository.query;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import java.util.List;
import io.superson.trelloproject.domain.user.entity.User;

public interface UserQueryRepository {

    List<Invite> findAllInviteByUserId(String userId);

    User findByUserAndComment(String userId, Long commentId);
}
