package io.superson.trelloproject.domain.ticket.repository;

import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import static io.superson.trelloproject.domain.comment.entity.QComment.comment;
import static io.superson.trelloproject.domain.ticket.entity.QAssignee.assignee;
import static io.superson.trelloproject.domain.ticket.entity.QTicket.ticket;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.AssigneeVo;
import io.superson.trelloproject.domain.ticket.repository.vo.CommentVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QAssigneeVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QCommentVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QTicketDetailsVo;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketQuerydslRepositoryImpl implements TicketQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(ticket)
            .where(ticket.board.boardId.eq(boardId),
                ticket.ticketId.eq(ticketId))
            .fetchOne());
    }

    @Override
    public Optional<TicketDetailsVo> findTicketDetails(Long ticketId) {
        TicketDetailsVo ticketWithComments = queryFactory.select(new QTicketDetailsVo(ticket.ticketId, ticket.name, ticket.color, ticket.description, ticket.deadline))
            .from(ticket)
            .leftJoin(comment)
            .on(comment.ticket.ticketId.eq(ticket.ticketId))
            .where(ticket.ticketId.eq(ticketId))
            .fetchOne();

        if (ticketWithComments == null) {
            return Optional.empty();
        }

        List<CommentVo> comments = queryFactory.select(new QCommentVo(comment.commentId, comment.content, comment.createdAt))
            .from(comment)
            .where(comment.ticket.ticketId.eq(ticketId))
            .fetch();

        List<AssigneeVo> assignees = queryFactory.select(new QAssigneeVo(assignee.user.email))
            .from(assignee)
            .where(assignee.ticket.ticketId.eq(ticketId))
            .fetch();

        ticketWithComments.setComments(comments);
        ticketWithComments.setAssignees(assignees);

        return Optional.of(ticketWithComments);
    }

    @Override
    public Optional<UserBoard> findByBoardIdAndUserId(Long boardId, String userId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(userBoard)
            .where(userBoard.board.boardId.eq(boardId),
                userBoard.user.userId.eq(userId))
            .fetchOne());
    }

}
