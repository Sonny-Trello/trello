package io.superson.trelloproject.domain.status.dto;

import io.superson.trelloproject.domain.status.entity.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
