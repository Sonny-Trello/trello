package io.superson.trelloproject.domain.ticket.dto;

import io.superson.trelloproject.domain.ticket.repository.vo.CommentVo;
import io.superson.trelloproject.global.util.Color;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TicketDetailsResponseDto {

    private Long ticketId;
    private String name;
    private Color color;
    private String description;
    private LocalDateTime deadline;
    private List<CommentVo> comments;

}
