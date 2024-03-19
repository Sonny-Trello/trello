package io.superson.trelloproject.domain.board.dto;


import io.superson.trelloproject.global.util.Color;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
@NotBlank
public class BoardRequestDto {
    private String name;
    private Color color;
    private String description;
}
