package io.superson.trelloproject.domain.status.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusRequestDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
}
