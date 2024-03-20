package io.superson.trelloproject.domain.ticket.repository.vo;

import io.superson.trelloproject.global.util.Color;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TicketDetailsVo {

    private Long ticketId;
    private String name;
    private Color color;
    private String description;
    private LocalDateTime deadline;
    private String[] comments;

}
