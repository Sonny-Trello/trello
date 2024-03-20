package io.superson.trelloproject.domain.comment.service;

import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.comment.dto.CommentRequestDto;
import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.comment.repository.command.CommentRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.query.UserQueryRepository;
import io.superson.trelloproject.global.exception.UserPermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final UserQueryRepository userQueryRepository;

    public void createComment(User user, Long boardId, Long ticketId, CommentRequestDto commentRequestDto) {
        validateUserIsBoardMember(user, boardId);
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .build();
        commentRepository.save(user, comment);
    }

    public void updateComment(User user, Long boardId, Long ticketId, Long commentId, CommentRequestDto commentRequestDto) {
        validateUserIsBoardMember(user, boardId);
        Comment comment = commentRepository.findCommentOrElseThrow(commentId);
        validateCommentAuthor(user, commentId);
        comment.updateComment(commentRequestDto);
    }

    public void deleteComment(User user, Long boardId, Long ticketId, Long commentId) {
        validateUserIsBoardMember(user, boardId);
        Comment comment = commentRepository.findCommentOrElseThrow(commentId);
        validateCommentAuthor(user, commentId);
        commentRepository.deleteComment(comment);
    }

    private void validateUserIsBoardMember(User user, Long boardId) {
        List<User> userList = boardQueryRepository.findAllByBoardId(boardId);
        if (userList.stream().noneMatch(u -> u.getUserId().equals(user.getUserId()))) {
            throw new UserPermissionException("해당 멤버가 아닙니다.");
        }
    }

    private void validateCommentAuthor(User user, Long commentId) {
        if (!userQueryRepository.findByUserAndComment(user.getUserId(), commentId).equals(user)) {
            throw new UserPermissionException("해당 댓글의 작성자에게 권한이 있습니다.");
        }
    }
}
