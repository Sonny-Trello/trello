package io.superson.trelloproject.domain.status.dto;

import io.superson.trelloproject.domain.status.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UpdateStatusResponseDto {

    private Long statusId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UpdateStatusResponseDto(Status status) {
        this.statusId = status.getStatusId();
        this.name = status.getName();
        this.createdAt = status.getCreatedAt();
        this.modifiedAt = status.getModifiedAt();
    }
}
