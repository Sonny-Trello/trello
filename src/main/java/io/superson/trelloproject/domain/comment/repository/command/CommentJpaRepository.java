package io.superson.trelloproject.domain.comment.repository.command;

import io.superson.trelloproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

}
