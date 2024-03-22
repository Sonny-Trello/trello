package io.superson.trelloproject.domain.board.repository.query.vo;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class StatusesVo {

    private Long statusId;
    private String name;

    @Setter
    private List<TicketsVo> tickets;

    @QueryProjection
    public StatusesVo(Long statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

}
