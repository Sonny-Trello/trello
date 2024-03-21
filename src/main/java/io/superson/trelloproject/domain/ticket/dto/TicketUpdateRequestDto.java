package io.superson.trelloproject.domain.ticket.dto;

import io.superson.trelloproject.global.util.Color;
import io.superson.trelloproject.global.util.validator.EnumSubsetOf;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketUpdateRequestDto {

    private String name;
    private String description;
    @EnumSubsetOf(enumClass = Color.class)
    private String color;
    private LocalDateTime deadline;

}
