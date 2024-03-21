package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import java.util.Optional;

public interface TicketQuerydslRepository {

    Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId);

    Optional<TicketDetailsVo> findTicketDetails(Long ticketId);

    Optional<UserBoard> findByBoardIdAndUserId(Long boardId, String userId);

}
