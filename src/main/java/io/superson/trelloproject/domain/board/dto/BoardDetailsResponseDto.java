package io.superson.trelloproject.domain.board.dto;

import io.superson.trelloproject.domain.board.repository.query.vo.BoardDetailsVo;
import io.superson.trelloproject.domain.board.repository.query.vo.StatusesVo;
import io.superson.trelloproject.domain.board.repository.query.vo.TicketsVo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDetailsResponseDto {

    private Long boardId;
    private String name;
    private String color;
    private String description;
    private List<StatusesVo> statuses;

    public BoardDetailsResponseDto(BoardDetailsVo boardDetailsVo) {
        this.boardId = boardDetailsVo.getBoardId();
        this.name = boardDetailsVo.getName();
        this.color = String.valueOf(boardDetailsVo.getColor());
        this.description = boardDetailsVo.getDescription();
        this.statuses = boardDetailsVo.getStatuses();
    }
}
