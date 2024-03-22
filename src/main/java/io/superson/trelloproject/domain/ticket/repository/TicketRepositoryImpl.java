package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import io.superson.trelloproject.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private static final Double POSITION_INCREMENT = 65536d; // 2 ^ 16

    private final TicketQuerydslJpaRepository repository;

    @Override
    public Ticket save(
        final Ticket ticket, final Board board, final Status status, final List<Assignee> assignees
    ) {
        ticket.setParents(board, status);
        ticket.addAssignees(assignees);

        Double position = repository.findMaxPosition(status.getStatusId()) + POSITION_INCREMENT;
        ticket.setPosition(position);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Optional<TicketDetailsResponseDto> findTicketDetailsById(
        final Long boardId, final Long ticketId
    ) {
        Optional<TicketDetailsVo> ticketWithById = repository.findTicketDetails(ticketId);

        return ticketWithById.map(TicketMapper::toTicketDetailsResponseDto);
    }

    @Override
    public Double findMinPositionByStatusId(final Long statusId) {
        return repository.findMinPosition(statusId);
    }

    @Override
    public List<Ticket> findPreviousAndNextTicket(
        final Long statusId, final Long previousTicketId
    ) {
        return repository.findPreviousAndNextTicket(statusId, previousTicketId);
    }

    @Override
    public List<User> findUsersInBoardByEmails(
        final Long boardId, final List<String> assigneeEmails
    ) {
        return repository.findUsersInBoardByEmails(boardId, assigneeEmails);
    }

    public List<Assignee> findAssigneesInTicketByEmails(
        final Long boardId, final Long ticketId, final List<String> emails
    ) {
        return repository.findAssigneesInTicketByEmails(boardId, ticketId, emails);
    }

    @Override
    public Ticket update(
        final Long boardId, final Long ticketId, final TicketCreateRequestDto requestDto
    ) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        ticket.update(requestDto);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Ticket updateStatus(
        final Long boardId, final Long ticketId, final Status status, final Double position
    ) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        ticket.setStatus(status);
        ticket.setPosition(position);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Ticket addAssignees(
        final Long boardId, final Long ticketId, final List<Assignee> assignees
    ) {
        Ticket savedTicket = findOrElseThrow(boardId, ticketId);
        savedTicket.addAssignees(assignees);

        return savedTicket;
    }

    @Override
    public Ticket deleteAssignees(
        final Long boardId, final Long ticketId, final List<Assignee> assignees
    ) {
        Ticket savedTicket = findOrElseThrow(boardId, ticketId);
        savedTicket.deleteAssignees(assignees);

        return savedTicket;
    }

    @Override
    public void deleteById(
        final Long boardId, final Long ticketId
    ) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        repository.delete(ticket);
    }

    @Override
    public Optional<UserBoard> validateUserAccess(
        final Long boardId, final String userId
    ) {
        return repository.findByBoardIdAndUserId(boardId, userId);
    }

    @Override
    public Ticket findOrElseThrow(
        final Long boardId, final Long ticketId
    ) {
        return repository.findByBoardIdAndTicketId(boardId, ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

}
