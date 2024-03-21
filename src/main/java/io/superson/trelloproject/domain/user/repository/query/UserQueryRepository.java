package io.superson.trelloproject.domain.user.repository.query;

import io.superson.trelloproject.domain.board.entity.Invite.Invite;
import java.util.List;

public interface UserQueryRepository {

    List<Invite> findAllByUserId(String userId);
}
