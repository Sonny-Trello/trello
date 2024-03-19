package io.superson.trelloproject.domain.comment.repository;

import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.user.entity.User;

public interface CommentRepository {

    Comment save(User user, Comment comment);

}
