package io.superson.trelloproject.domain.status.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusNumberResponseDto {

    private Long statusId;
    private Float currentStatusNumber;
}
