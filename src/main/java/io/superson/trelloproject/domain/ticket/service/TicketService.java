package io.superson.trelloproject.domain.ticket.service;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.TicketRepository;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
        final TicketRequestDto requestDto,
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

    @Transactional(readOnly = true)
    public TicketDetailsResponseDto findTicket(Long boardId, Long ticketId, String userId) {
        Board board = validateBoard(boardId);
        User user = validateUserAccess(boardId, userId);
        Ticket ticket = validateTicketAccess(boardId, ticketId);

        return ticketRepository.findTicketDetailsById(ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    public TicketResponseDto updateTicket(
        Long boardId,
        Long ticketId,
        TicketRequestDto requestDto,
        String userId
    ) {
        Board board = validateBoard(boardId);
        User user = validateUserAccess(boardId, userId);
        Ticket ticket = validateTicketAccess(boardId, ticketId);

        List<Assignee> assignees = userRepository.findUsersByEmails(requestDto.getAssigneeEmails())
            .stream()
            .map(Assignee::new)
            .toList();
        Ticket updatedTicket = ticketRepository.update(ticketId, requestDto, assignees);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    private Board validateBoard(Long boardId) {
        // TODO: Implement

        return null;
    }

    private Status validateStatus(Long statusId) {
        // TODO: Implement

        return null;
    }

    private Ticket validateTicketAccess(Long boardId, Long ticketId) {
        // TODO: Implement

        return null;
    }

    private User validateUserAccess(Long boardId, String userId) {
        // TODO: Implement

        return null;
    }

}
