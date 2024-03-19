package io.superson.trelloproject.domain.comment.repository;

import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(User user, Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Comment findCommentOrElseThrow(Long commentId) {
        return commentJpaRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("해당 댓글은 존재하지 않습니다."));
    }
}
