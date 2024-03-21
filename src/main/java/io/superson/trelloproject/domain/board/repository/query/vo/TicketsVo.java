package io.superson.trelloproject.domain.board.repository.query.vo;

import com.querydsl.core.annotations.QueryProjection;
import io.superson.trelloproject.global.util.Color;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketsVo {

    private Long ticketId;
    private Long statusId;
    private String name;
    private Color color;
    private String description;
    private LocalDateTime deadline;


    @QueryProjection
    public TicketsVo(
        Long ticketId,
        Long statusId,
        String name,
        Color color,
        String description,
        LocalDateTime deadline
    ) {
        this.ticketId = ticketId;
        this.statusId = statusId;
        this.name = name;
        this.color = color;
        this.description = description;
        this.deadline = deadline;
    }
}
