package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import java.util.Optional;

public interface TicketQuerydslRepository {

    Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId);

    Optional<TicketDetailsVo> findTicketDetails(Long ticketId);

}
