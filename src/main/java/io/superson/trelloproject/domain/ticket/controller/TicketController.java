package io.superson.trelloproject.domain.ticket.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.service.TicketService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        TicketDetailsResponseDto responseDto = ticketService.findTicket(
            boardId,
            ticketId,
            userDetails.getUser().getUserId()
        );

        return ResponseEntity.ok(ResponseDto.of(responseDto));
    }

    private URI createUri(Long ticketId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(ticketId)
            .toUri();
    }

}
