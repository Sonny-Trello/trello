package io.superson.trelloproject.domain.board.dto;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.global.util.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponseDto {
    private Long boardId;
    private String name;
    private Color color;
    private String description;


    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.name = board.getName();
        this.color = board.getColor();
        this.description = board.getDescription();
    }
}
