package io.superson.trelloproject.domain.ticket.repository;

import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import static io.superson.trelloproject.domain.comment.entity.QComment.comment;
import static io.superson.trelloproject.domain.ticket.entity.QAssignee.assignee;
import static io.superson.trelloproject.domain.ticket.entity.QTicket.ticket;
import static io.superson.trelloproject.domain.user.entity.QUser.user;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.AssigneeVo;
import io.superson.trelloproject.domain.ticket.repository.vo.CommentVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QAssigneeVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QCommentVo;
import io.superson.trelloproject.domain.ticket.repository.vo.QTicketDetailsVo;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import io.superson.trelloproject.domain.ticket.repository.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.superson.trelloproject.domain.board.entity.QUserBoard.userBoard;
import static io.superson.trelloproject.domain.comment.entity.QComment.comment;
import static io.superson.trelloproject.domain.ticket.entity.QAssignee.assignee;
import static io.superson.trelloproject.domain.ticket.entity.QTicket.ticket;

@RequiredArgsConstructor
@Repository
public class TicketQuerydslRepositoryImpl implements TicketQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId) {
        return Optional.ofNullable(queryFactory.selectFrom(ticket)
            .where(ticket.board.boardId.eq(boardId), ticket.ticketId.eq(ticketId))
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
        return Optional.ofNullable(queryFactory.selectFrom(userBoard)
            .where(userBoard.board.boardId.eq(boardId), userBoard.user.userId.eq(userId))
            .fetchOne());
    }

    @Override
    public Ticket findTicketByBoardIdAndTicketId(Long boardId, Long ticketId) {
        return queryFactory.select(ticket)
            .from(ticket)
            .where(ticket.board.boardId.eq(boardId))
            .fetchOne();
    }

    @Override
    public List<Float> findPreviousAndNextTicketPositions(Long statusId, Long previousTicketId) {
        JPQLQuery<Float> previousTicketPosition = JPAExpressions.select(ticket.position)
            .from(ticket)
            .where(ticket.ticketId.eq(previousTicketId));

        return queryFactory.select(ticket.position)
            .from(ticket)
            .where(ticket.status.statusId.eq(statusId), ticket.position.goe(previousTicketPosition))
            .orderBy(ticket.position.asc())
            .limit(2)
            .fetch();
    }

    @Override
    public List<User> findUsersInBoardByEmails(Long boardId, List<String> assigneeEmails) {
        return queryFactory.selectFrom(user)
            .join(userBoard)
            .where(userBoard.board.boardId.eq(boardId), user.email.in(assigneeEmails), user.deletedAt.isNotNull())
            .fetch();
    }

    @Override
    public List<Assignee> findAssigneesInTicketByEmails(
        Long boardId, Long ticketId, List<String> emails
    ) {
        return queryFactory.selectFrom(assignee)
            .where(assignee.ticket.ticketId.eq(ticketId), assignee.user.email.in(emails))
            .fetch();
    }

    @Override
    public Float findMinPosition(Long statusId) {
        return queryFactory.select(ticket.position.min())
            .from(ticket)
            .where(ticket.status.statusId.eq(statusId))
            .fetchOne();
    }

    @Nullable
    @Override
    public Float findMaxPosition(Long statusId) {
        Float result = queryFactory.select(ticket.position.max())
            .from(ticket)
            .where(ticket.status.statusId.eq(statusId))
            .fetchOne();

        return Objects.requireNonNullElse(result, 0f);
    }

}
