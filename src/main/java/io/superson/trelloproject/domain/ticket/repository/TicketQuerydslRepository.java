package io.superson.trelloproject.domain.ticket.repository;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import io.superson.trelloproject.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

public interface TicketQuerydslRepository {

    Optional<Ticket> findByBoardIdAndTicketId(Long boardId, Long ticketId);

    Optional<TicketDetailsVo> findTicketDetails(Long ticketId);

    Optional<UserBoard> findByBoardIdAndUserId(Long boardId, String userId);

    List<User> findUsersInBoardByEmails(Long boardId, List<String> assigneeEmails);

    List<Ticket> findPreviousAndNextTicket(Long statusId, Long previousTicketId);

    List<Assignee> findAssigneesInTicketByEmails(Long boardId, Long ticketId, List<String> emails);

    Double findMaxPosition(Long statusId);

    Double findMinPosition(Long statusId);

    Ticket findTicketByBoardIdAndTicketId(Long boardId, Long ticketId);

}
