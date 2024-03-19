package io.superson.trelloproject.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
}
