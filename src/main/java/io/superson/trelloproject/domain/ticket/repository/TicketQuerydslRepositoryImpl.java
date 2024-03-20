package io.superson.trelloproject.domain.ticket.repository;

import static io.superson.trelloproject.domain.ticket.entity.QTicket.ticket;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.superson.trelloproject.domain.ticket.entity.QTicket;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketQuerydslRepositoryImpl implements TicketQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(ticket.board.boardId.eq(boardId));
        condition.and(ticket.ticketId.eq(ticketId));

        return Optional.ofNullable(
            queryFactory.selectFrom(ticket)
                .where(condition)
                .fetchOne()
        );
    }

    @Override
    public Optional<TicketDetailsVo> findTicketDetails(Long ticketId) {
        // TODO: Implement the query to get comments by ticketId

        Optional<Ticket> ticket = Optional.ofNullable(
            queryFactory.selectFrom(QTicket.ticket)
                .where(QTicket.ticket.ticketId.eq(ticketId))
                .fetchOne()
        );

        return ticket.map(t -> TicketMapper.toTicketDetailsVo(t, null));
    }

}
