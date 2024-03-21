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
    public Ticket save(Ticket ticket, Board board, Status status, List<Assignee> assignees) {
        ticket.setParents(board, status);
        ticket.addAssignees(assignees);

        Double position = repository.findMaxPosition(status.getStatusId()) + POSITION_INCREMENT;
        ticket.setPosition(position);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Optional<TicketDetailsResponseDto> findTicketDetailsById(Long boardId, Long ticketId) {
        Optional<TicketDetailsVo> ticketWithById = repository.findTicketDetails(ticketId);

        return ticketWithById.map(TicketMapper::toTicketDetailsResponseDto);
    }

    @Override
    public Double findMinPositionByStatusId(Long statusId) {
        return repository.findMinPosition(statusId);
    }

    @Override
    public List<Double> findPreviousAndNextTicketPositions(Long statusId, Long previousTicketId) {
        return repository.findPreviousAndNextTicketPositions(statusId, previousTicketId);
    }

    @Override
    public List<User> findUsersInBoardByEmails(Long boardId, List<String> assigneeEmails) {
        return repository.findUsersInBoardByEmails(boardId, assigneeEmails);
    }

    public List<Assignee> findAssigneesInTicketByEmails(
        Long boardId,
        Long ticketId,
        List<String> emails
    ) {
        return repository.findAssigneesInTicketByEmails(boardId, ticketId, emails);
    }

    @Override
    public Ticket update(
        Long boardId, Long ticketId, TicketCreateRequestDto requestDto
    ) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        ticket.update(requestDto);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Ticket updateStatus(Long boardId, Long ticketId, Status status, Double position) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        ticket.setStatus(status);
        ticket.setPosition(position);

        return repository.saveAndFlush(ticket);
    }

    @Override
    public Ticket addAssignees(Long boardId, Long ticketId, List<Assignee> assignees) {
        Ticket savedTicket = findOrElseThrow(boardId, ticketId);
        savedTicket.addAssignees(assignees);

        return savedTicket;
    }

    @Override
    public Ticket deleteAssignees(Long boardId, Long ticketId, List<Assignee> assignees) {
        Ticket savedTicket = findOrElseThrow(boardId, ticketId);
        savedTicket.deleteAssignees(assignees);

        return savedTicket;
    }

    @Override
    public void deleteById(Long boardId, Long ticketId) {
        Ticket ticket = findOrElseThrow(boardId, ticketId);

        repository.delete(ticket);
    }

    @Override
    public Optional<UserBoard> validateUserAccess(Long boardId, String userId) {
        return repository.findByBoardIdAndUserId(boardId, userId);
    }

    @Override
    public Ticket findOrElseThrow(Long boardId, Long ticketId) {
        return repository.findByBoardIdAndTicketId(boardId, ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

}
