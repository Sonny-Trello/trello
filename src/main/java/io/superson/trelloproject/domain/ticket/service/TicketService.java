package io.superson.trelloproject.domain.ticket.service;

import io.superson.trelloproject.domain.board.entity.UserBoard;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.status.repository.command.StatusRepository;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketStatusUpdateRequestDto;
import io.superson.trelloproject.domain.ticket.entity.Assignee;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.mapper.TicketMapper;
import io.superson.trelloproject.domain.ticket.repository.TicketRepository;
import io.superson.trelloproject.domain.user.repository.command.UserRepository;
import io.superson.trelloproject.global.exception.UserNotFoundException;
import io.superson.trelloproject.global.exception.UserPermissionException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketService {

    private static final Double POSITION_INCREMENT = 65536D; // 2 ^ 16

    private final TicketRepository ticketRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public TicketResponseDto createTicket(
        final Long boardId,
        final Long statusId,
        final TicketCreateRequestDto requestDto,
        final String userId
    ) {
        UserBoard userBoard = validateUserAccessToBoard(boardId, userId);
        Status status = statusRepository.findStatusOrElseThrow(statusId);

        List<Assignee> assignees = userRepository.findUsersByEmails(requestDto.getAssigneeEmails())
            .stream()
            .map(Assignee::new)
            .toList();
        Ticket savedTicket = ticketRepository.save(TicketMapper.toEntity(requestDto),
            userBoard.getBoard(), status, assignees);

        return TicketMapper.toTicketResponseDto(savedTicket);
    }

    @Transactional(readOnly = true)
    public TicketDetailsResponseDto findTicketDetails(
        final Long boardId, final Long ticketId, final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);

        return ticketRepository.findTicketDetailsById(boardId, ticketId)
            .orElseThrow(() -> new UserNotFoundException("Ticket not found"));
    }

    @BlueLock
    public TicketResponseDto updateTicket(
        final Long boardId,
        final Long ticketId,
        final TicketCreateRequestDto requestDto,
        final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);

        Ticket updatedTicket = ticketRepository.update(boardId, ticketId, requestDto);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    @BlueLock
    public TicketResponseDto updateStatusAndOrder(
        final Long boardId,
        final Long fromStatusId,
        final Long currentTicketId,
        final TicketStatusUpdateRequestDto requestDto,
        final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);
        Status validatedStatus = statusRepository.findStatusOrElseThrow(requestDto.getToStatusId());
        Double calculatedPosition = calculatePosition(fromStatusId, currentTicketId, requestDto);

        Ticket updatedTicket = ticketRepository.updateStatus(boardId, currentTicketId,
            validatedStatus, calculatedPosition);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    public TicketResponseDto addAssignees(
        final Long boardId,
        final Long ticketId,
        final List<String> assigneeEmails,
        final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);

        List<Assignee> assignees = ticketRepository.findUsersInBoardByEmails(boardId,
                assigneeEmails)
            .stream()
            .map(Assignee::new)
            .toList();

        Ticket updatedTicket = ticketRepository.addAssignees(boardId, ticketId, assignees);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    public void deleteTicket(
        final Long boardId, final Long ticketId, final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);

        ticketRepository.deleteById(boardId, ticketId);
    }

    public TicketResponseDto deleteAssignees(
        final Long boardId,
        final Long ticketId,
        final List<String> assigneeEmails,
        final String userId
    ) {
        validateUserAccessToBoard(boardId, userId);

        List<Assignee> assignees = ticketRepository.findAssigneesInTicketByEmails(boardId, ticketId,
            assigneeEmails);
        if (assigneeEmails.size() != assignees.size()) {
            throw new UserNotFoundException("Assignee not found");
        }

        Ticket updatedTicket = ticketRepository.deleteAssignees(boardId, ticketId, assignees);

        return TicketMapper.toTicketResponseDto(updatedTicket);
    }

    private UserBoard validateUserAccessToBoard(final Long boardId, final String userId) {
        return ticketRepository.validateUserAccess(boardId, userId)
            .orElseThrow(
                () -> new UserPermissionException("User not allowed to access this board"));
    }

    private Double calculatePosition(
        final Long fromStatusId,
        final Long currentTicketId,
        final TicketStatusUpdateRequestDto requestDto
    ) {
        final Long toStatusId = requestDto.getToStatusId();
        final Long previousTicketId = requestDto.getPreviousTicketId();

        // ! issue: if moving to same position, it should not change the position

        // to first
        if (previousTicketId == null) {
            Double minPosition = ticketRepository.findMinPositionByStatusId(toStatusId);

            return (minPosition == null) ? POSITION_INCREMENT : Double.valueOf(minPosition / 2);
        }

        List<Ticket> positions = ticketRepository.findPreviousAndNextTicket(toStatusId,
            previousTicketId);
        int size = positions.size();

        // to last
        if (size == 1) {
            return positions.get(0).getPosition() + POSITION_INCREMENT;
        }

        // to others
        if (size == 2) {
            return Objects.equals(positions.get(1).getTicketId(), currentTicketId)
                ? positions.get(1).getPosition()
                : (positions.get(0).getPosition() + positions.get(1).getPosition()) / 2;
        }
        return POSITION_INCREMENT;
    }

}
