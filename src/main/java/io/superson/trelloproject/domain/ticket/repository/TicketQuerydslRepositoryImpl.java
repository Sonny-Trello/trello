package io.superson.trelloproject.domain.ticket.repository;

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
    public Optional<TicketDetailsVo> findTicketDetails(Long ticketId) {
        QTicket qTicket = QTicket.ticket;

        // TODO: Implement the query to get comments by ticketId

        Optional<Ticket> ticket = Optional.ofNullable(
            queryFactory.selectFrom(qTicket)
                .where(qTicket.ticketId.eq(ticketId))
                .fetchOne()
        );

        return ticket.map(t -> TicketMapper.toTicketDetailsVo(t, null));
    }

}
