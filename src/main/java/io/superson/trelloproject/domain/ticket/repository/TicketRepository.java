package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket, Board board, Status status, List<Assignee> assignees);

    Ticket findOrElseThrow(Long boardId, Long ticketId);

    Optional<TicketDetailsResponseDto> findTicketDetailsById(Long boardId, Long ticketId);

    Double findMinPositionByStatusId(Long statusId);

    List<Ticket> findPreviousAndNextTicket(Long statusId, Long previousTicketId);

    List<User> findUsersInBoardByEmails(Long boardId, List<String> assigneeEmails);

    List<Assignee> findAssigneesInTicketByEmails(Long boardId, Long ticketId, List<String> emails);

    Ticket update(Long boardId, Long ticketId, TicketCreateRequestDto requestDto);

    Ticket updateStatus(Long boardId, Long ticketId, Status status, Double position);

    void deleteById(Long boardId, Long ticketId);

    Ticket addAssignees(Long boardId, Long ticketId, List<Assignee> assignees);

    Ticket deleteAssignees(Long boardId, Long ticketId, List<Assignee> assignees);

    Optional<UserBoard> validateUserAccess(Long boardId, String userId);

}
