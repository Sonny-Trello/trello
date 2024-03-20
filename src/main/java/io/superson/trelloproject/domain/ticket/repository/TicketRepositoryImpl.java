package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import java.util.List;
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

}
