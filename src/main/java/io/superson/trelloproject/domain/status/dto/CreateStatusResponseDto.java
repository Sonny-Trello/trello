package io.superson.trelloproject.domain.status.dto;

import io.superson.trelloproject.domain.status.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateStatusResponseDto {

    private Long statusId;
    private String name;
    private LocalDateTime createdAt;

    @Builder
    public CreateStatusResponseDto(Status status) {
        this.statusId = status.getStatusId();
        this.name = status.getName();
        this.createdAt = status.getCreatedAt();
    }
}
