package io.superson.trelloproject.domain.board.dto;

import io.superson.trelloproject.domain.board.entity.Board;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardInfoResponseDto {

    private Long boardId;
    private String name;
    private String color;
    private String description;
    private List<String> statuses;

    public BoardInfoResponseDto(Board board, List<String> statuses) {
        this.boardId = board.getBoardId();
        this.name = board.getName();
        this.color = String.valueOf(board.getColor());
        this.description = board.getDescription();
        this.statuses = statuses;
    }
}
