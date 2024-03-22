package io.superson.trelloproject.domain.ticket.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketAssigneeRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketStatusUpdateRequestDto;
import io.superson.trelloproject.domain.ticket.service.TicketService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/{boardId}")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/status/{statusId}/tickets")
    public ResponseEntity<ResponseDto<TicketResponseDto>> createTicket(
        final @PathVariable @Positive @Validated Long boardId,
        final @PathVariable @Positive @Validated Long statusId,
        final @RequestBody @Validated TicketCreateRequestDto requestDto,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketResponseDto responseDto = ticketService.createTicket(
            boardId,
            statusId,
            requestDto,
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.created(createUri(responseDto.getTicketId()))
            .body(ResponseDto.of(responseDto));
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<ResponseDto<TicketDetailsResponseDto>> getTicket(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketDetailsResponseDto responseDto = ticketService.findTicketDetails(
            boardId,
            ticketId,
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(responseDto));
    }

    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateTicket(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @RequestBody @Validated TicketCreateRequestDto requestDto,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketResponseDto responseDto = ticketService.updateTicket(
            boardId,
            ticketId,
            requestDto,
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(responseDto));
    }

    @PatchMapping("/status/{fromStatusId}/tickets/{ticketId}")
    public ResponseEntity<ResponseDto<TicketResponseDto>> updateStatus(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @PathVariable @Positive Long fromStatusId,
        final @Validated TicketStatusUpdateRequestDto requestDto,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketResponseDto responseDto = ticketService.updateStatusAndOrder(
            boardId,
            fromStatusId,
            ticketId,
            requestDto,
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(responseDto));
    }

    @PostMapping("/tickets/{ticketId}/assignees")
    public ResponseEntity<ResponseDto<TicketResponseDto>> addAssignees(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @RequestBody @Validated TicketAssigneeRequestDto requestDto,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketResponseDto ticketResponseDto = ticketService.addAssignees(
            boardId,
            ticketId,
            requestDto.getAssigneeEmails(),
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(ticketResponseDto));
    }

    @DeleteMapping("/tickets/{ticketId}/assignees")
    public ResponseEntity<ResponseDto<TicketResponseDto>> deleteAssignees(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @RequestBody @Validated TicketAssigneeRequestDto requestDto,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TicketResponseDto ticketResponseDto = ticketService.deleteAssignees(
            boardId,
            ticketId,
            requestDto.getAssigneeEmails(),
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(ticketResponseDto));
    }

    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<ResponseDto<Void>> deleteTicket(
        final @PathVariable @Positive Long boardId,
        final @PathVariable @Positive Long ticketId,
        final @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ticketService.deleteTicket(boardId, ticketId, userDetails.getUser().getUserId());

        return ResponseEntity.ok().build();
    }

    private URI createUri(Long ticketId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(ticketId)
            .toUri();
    }

}
