package io.superson.trelloproject.domain.board.repository.query.vo;

import com.querydsl.core.annotations.QueryProjection;
import io.superson.trelloproject.global.util.Color;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class BoardDetailsVo {

    private Long boardId;
    private String name;
    private Color color;
    private String description;

    @Setter
    private List<StatusesVo> statuses;

    @QueryProjection
    public BoardDetailsVo(
        Long boardId,
        String name,
        Color color,
        String description
    ) {
        this.boardId = boardId;
        this.name = name;
        this.color = color;
        this.description = description;
    }

}
