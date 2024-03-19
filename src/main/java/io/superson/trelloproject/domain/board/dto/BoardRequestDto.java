package io.superson.trelloproject.domain.board.dto;


import io.superson.trelloproject.global.util.Color;
import io.superson.trelloproject.global.util.validator.EnumSubsetOf;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    @NotBlank
    private String name;

    @EnumSubsetOf(enumClass = Color.class)
    private String color;

    @NotBlank
    private String description;
}
