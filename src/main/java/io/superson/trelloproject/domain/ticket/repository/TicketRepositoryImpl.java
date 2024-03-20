package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketQuerydslJpaRepository repository;

    @Override
    public Ticket save(Ticket ticket, Board board, Status status, List<Assignee> assignees) {
        ticket.setParents(board, status);
        ticket.addAssignees(assignees);

        return repository.save(ticket);
    }

    @Override
    public Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId) {
        return repository.findByBoardIdAndTicketId(boardId, ticketId);
    }

    @Override
    public Optional<TicketDetailsResponseDto> findTicketDetailsById(Long boardId, Long ticketId) {
        Optional<TicketDetailsVo> ticketWithById = repository.findTicketDetails(ticketId);

        return ticketWithById.map(TicketMapper::toTicketDetailsResponseDto);
    }

    @Override
    public Ticket update(
        Long boardId, Long ticketId, TicketCreateRequestDto requestDto, List<Assignee> assignees
    ) {
        Ticket ticket = repository.findByBoardIdAndTicketId(boardId, ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        ticket.update(requestDto, assignees);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Ticket updateStatus(Long boardId, Long ticketId, Status status) {
        Ticket ticket = repository.findByBoardIdAndTicketId(boardId, ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        ticket.setStatus(status);

        return repository.saveAndFlush(ticket);
    }

}
