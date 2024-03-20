package io.superson.trelloproject.domain.user.repository.query;

import io.superson.trelloproject.domain.user.entity.User;

public interface UserQueryRepository {
    User findByUserAndComment(String userId, Long commentId);
}
