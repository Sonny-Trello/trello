package io.superson.trelloproject.domain.ticket.service;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.TicketRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketResponseDto createTicket(
        final Long boardId,
        final Long statusId,
        final TicketCreateRequestDto requestDto,
        final String userId
    ) {
        Board board = validateBoard(boardId);
        User user = validateUserAccess(boardId, userId);
        Status status = validateStatus(statusId);

        List<Assignee> assignees = userRepository.findUsersByEmails(requestDto.getAssigneeEmails())
            .stream()
            .map(Assignee::new)
            .toList();
        Ticket savedTicket = ticketRepository.save(TicketMapper.toEntity(requestDto), board, status, assignees);

        return TicketMapper.toTicketResponseDto(savedTicket);
    }

    private Board validateBoard(Long boardId) {
        // TODO: Implement

        return null;
    }

    private User validateUserAccess(Long boardId, String userId) {
        // TODO: Implement

        return null;
    }

    private Status validateStatus(Long statusId) {
        // TODO: Implement

        return null;
    }

}
