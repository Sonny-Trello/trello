package io.superson.trelloproject.domain.comment.service;

import io.superson.trelloproject.domain.comment.dto.CommentRequestDto;
import io.superson.trelloproject.domain.comment.repository.CommentRepository;
import io.superson.trelloproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public void createComment(User user, Long boardId, Long ticketId, CommentRequestDto commentRequestDto) {
    }
}
