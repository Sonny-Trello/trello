package io.superson.trelloproject.domain.comment.repository;

import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(User user, Comment comment) {
        return commentJpaRepository.save(comment);
    }
}
