package io.superson.trelloproject.domain.ticket.dto;

import io.superson.trelloproject.global.util.Color;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {

    private Long ticketId;
    private String name;
    private Color color;
    private String description;
    private LocalDateTime deadline;
    private Double position;

}
