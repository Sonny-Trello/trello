package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
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
    public Optional<TicketDetailsResponseDto> findTicketDetailsById(Long id) {
        Optional<TicketDetailsVo> ticketWithById = repository.findTicketDetails(id);

        return ticketWithById.map(TicketMapper::toTicketDetailsResponseDto);
    }

}
