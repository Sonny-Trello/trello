package io.superson.trelloproject.domain.comment.repository.command;

import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.user.entity.User;

public interface CommentRepository {

    Comment save(User user, Comment comment);

    Comment findCommentOrElseThrow(Long commentId);

    void deleteComment(Comment comment);
}
