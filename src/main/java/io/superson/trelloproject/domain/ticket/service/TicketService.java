package io.superson.trelloproject.domain.ticket.service;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.command.StatusRepository;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.TicketRepository;
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
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public TicketResponseDto createTicket(
        final Long boardId,
        final Long statusId,
        final TicketCreateRequestDto requestDto,
        final String userId
    ) {
        UserBoard userBoard = validateUserAccess(boardId, userId);
        Status status = statusRepository.findStatusOrElseThrow(statusId);

        List<Assignee> assignees = userRepository.findUsersByEmails(requestDto.getAssigneeEmails())
            .stream()
            .map(Assignee::new)
            .toList();
        Ticket savedTicket = ticketRepository.save(TicketMapper.toEntity(requestDto), userBoard.getBoard(), status, assignees);

        return TicketMapper.toTicketResponseDto(savedTicket);
    }

    @Transactional(readOnly = true)
    public TicketDetailsResponseDto findTicketDetails(Long boardId, Long ticketId, String userId) {
        validateUserAccess(boardId, userId);

        return ticketRepository.findTicketDetailsById(boardId, ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    public TicketResponseDto updateTicket(
        Long boardId, Long ticketId, TicketCreateRequestDto requestDto, String userId
    ) {
        validateUserAccess(boardId, userId);

        Ticket updatedTicket = ticketRepository.update(boardId, ticketId, requestDto);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    public TicketResponseDto updateStatus(
        Long boardId, Long ticketId, Long statusId, String userId
    ) {
        validateUserAccess(boardId, userId);
        Status status = statusRepository.findStatusOrElseThrow(statusId);

        Ticket updatedTicket = ticketRepository.updateStatus(boardId, ticketId, status);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    public void deleteTicket(Long boardId, Long ticketId, String userId) {
        validateUserAccess(boardId, userId);

        ticketRepository.deleteById(boardId, ticketId);
    }

    private UserBoard validateUserAccess(Long boardId, String userId) {
        return ticketRepository.validateUserAccess(boardId, userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
