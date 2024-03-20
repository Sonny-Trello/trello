package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import java.util.Optional;

public interface TicketQuerydslRepository {

    Optional<TicketDetailsVo> findTicketDetails(Long ticketId);

}
