package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket, Board board, Status status, List<Assignee> assignees);

    Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId);

    Optional<TicketDetailsResponseDto> findTicketDetailsById(Long boardId, Long ticketId);

    Ticket update(
        Long boardId,
        Long ticketId,
        TicketCreateRequestDto requestDto,
        List<Assignee> assignees
    );

    Ticket updateStatus(Long boardId, Long ticketId, Status status);

}
