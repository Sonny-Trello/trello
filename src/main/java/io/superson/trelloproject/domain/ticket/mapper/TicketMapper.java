package io.superson.trelloproject.domain.ticket.mapper;

import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.global.util.Color;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class TicketMapper {

    private static final Random random = new Random();

    public static Ticket toEntity(
        final TicketCreateRequestDto requestDto
    ) {
        Color color = (requestDto.getColor() != null)
            ? Color.valueOf(requestDto.getColor())
            : Color.values()[random.nextInt(Color.values().length)];

        return Ticket.builder()
            .name(requestDto.getName())
            .color(color)
            .description(requestDto.getDescription())
            .deadline(requestDto.getDeadline())
            .build();
    }

    public static TicketResponseDto toTicketResponseDto(
        final Ticket ticket
    ) {
        return TicketResponseDto.builder()
            .ticketId(ticket.getTicketId())
            .name(ticket.getName())
            .color(ticket.getColor())
            .description(ticket.getDescription())
            .deadline(ticket.getDeadline())
            .build();
    }

}
