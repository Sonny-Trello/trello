package io.superson.trelloproject.domain.comment.service;

import io.superson.trelloproject.domain.board.repository.query.BoardQueryRepository;
import io.superson.trelloproject.domain.comment.dto.CommentRequestDto;
import io.superson.trelloproject.domain.comment.dto.CommentResponseDto;
import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.comment.repository.command.CommentRepository;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.TicketQuerydslJpaRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.query.UserQueryRepository;
import io.superson.trelloproject.global.exception.UserNotFoundException;
import io.superson.trelloproject.global.exception.UserPermissionException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final BoardQueryRepository boardQueryRepository;
    private final CommentRepository commentRepository;
    private final TicketQuerydslJpaRepository ticketQuerydslJpaRepository;
    private final UserQueryRepository userQueryRepository;

    public CommentResponseDto createComment(User user, Long boardId, Long ticketId, CommentRequestDto commentRequestDto) {
        validateBoardWithTicket(boardId, ticketId);
        validateUserIsBoardMember(user, boardId);

        Ticket ticket = ticketQuerydslJpaRepository.findTicketByBoardIdAndTicketId(boardId, ticketId);
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .ticket(ticket)
                .build();
        return new CommentResponseDto(commentRepository.save(user, comment));
    }

    public CommentResponseDto updateComment(User user, Long boardId, Long ticketId, Long commentId, CommentRequestDto commentRequestDto) {
        System.out.println(user);
        validateBoardWithTicket(boardId, ticketId);
        validateUserIsBoardMember(user, boardId);

        Comment comment = commentRepository.findCommentOrElseThrow(commentId);
        validateCommentAuthor(user, commentId);

        comment.updateComment(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(User user, Long boardId, Long ticketId, Long commentId) {
        validateBoardWithTicket(boardId, ticketId);
        validateUserIsBoardMember(user, boardId);

        Comment comment = commentRepository.findCommentOrElseThrow(commentId);
        validateCommentAuthor(user, commentId);

        commentRepository.deleteComment(comment);
    }

    private void validateBoardWithTicket(Long boardId, Long ticketId) {
        Ticket confirmTicket = ticketQuerydslJpaRepository.findTicketByBoardIdAndTicketId(boardId, ticketId);
        if (confirmTicket == null) {
            throw new NoSuchElementException("해당 티켓은 존재하지 않습니다.");
        }
    }

    private void validateUserIsBoardMember(User user, Long boardId) {
        List<User> userList = boardQueryRepository.findAllByBoardId(boardId);
        if (userList.stream().noneMatch(u -> u.getUserId().equals(user.getUserId()))) {
            throw new UserPermissionException("해당 멤버가 아닙니다.");
        }
    }

    private void validateCommentAuthor(User user, Long commentId) {//user -> 댓글 지우기 시도하는 사람 = 로그인 한 사람 // commentId -> 지우려는 comment
        User confirmUser = userQueryRepository.findByUserAndComment(commentId)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
        if (!Objects.equals(confirmUser.getUserId(), user.getUserId())) {
            throw new UserPermissionException("해당 댓글의 작성자에게 권한이 있습니다.");
        }
    }
}
