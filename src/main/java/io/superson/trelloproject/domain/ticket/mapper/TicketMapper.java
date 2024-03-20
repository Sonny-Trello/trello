package io.superson.trelloproject.domain.ticket.mapper;

import io.superson.trelloproject.domain.comment.entity.Comment;
import io.superson.trelloproject.domain.ticket.dto.TicketDetailsResponseDto;
import io.superson.trelloproject.domain.ticket.dto.TicketRequestDto;
import io.superson.trelloproject.domain.ticket.dto.TicketResponseDto;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import io.superson.trelloproject.domain.ticket.repository.vo.TicketDetailsVo;
import io.superson.trelloproject.global.util.Color;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class TicketMapper {

    private static final Random random = new Random();

    public static Ticket toEntity(
        final TicketRequestDto requestDto
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

    public static TicketDetailsResponseDto toTicketDetailsResponseDto(
        final TicketDetailsVo detailsVo
    ) {
        return TicketDetailsResponseDto.builder()
            .ticketId(detailsVo.getTicketId())
            .name(detailsVo.getName())
            .color(detailsVo.getColor())
            .description(detailsVo.getDescription())
            .deadline(detailsVo.getDeadline())
            .comments(detailsVo.getComments())
            .build();
    }

    public static TicketDetailsVo toTicketDetailsVo(
        final Ticket ticket, List<Comment> comments
    ) {
        return TicketDetailsVo.builder()
            .ticketId(ticket.getTicketId())
            .name(ticket.getName())
            .color(ticket.getColor())
            .description(ticket.getDescription())
            .deadline(ticket.getDeadline())
            // TODO: Implement Comments to ResponseDto
            .build();
    }

}
