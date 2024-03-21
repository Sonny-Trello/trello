package io.superson.trelloproject.domain.ticket.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import io.superson.trelloproject.global.util.Color;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class TicketDetailsVo {

    private Long ticketId;
    private String name;
    private Color color;
    private String description;
    private LocalDateTime deadline;
    @Setter
    private List<AssigneeVo> assignees;
    @Setter
    private List<CommentVo> comments;

    @QueryProjection
    public TicketDetailsVo(
        Long ticketId,
        String name,
        Color color,
        String description,
        LocalDateTime deadline
    ) {
        this.ticketId = ticketId;
        this.name = name;
        this.color = color;
        this.description = description;
        this.deadline = deadline;
    }

}
