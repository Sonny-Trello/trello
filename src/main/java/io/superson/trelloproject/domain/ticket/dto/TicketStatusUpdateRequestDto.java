package io.superson.trelloproject.domain.ticket.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketStatusUpdateRequestDto {

    @Positive
    private Long toStatusId;
    @Positive
    private Long previousTicketId;
    @Positive
    private Double currentPosition;

}
