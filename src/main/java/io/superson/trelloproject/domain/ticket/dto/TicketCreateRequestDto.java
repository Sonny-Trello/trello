package io.superson.trelloproject.domain.ticket.dto;

import io.superson.trelloproject.global.util.Color;
import io.superson.trelloproject.global.util.validator.EnumSubsetOf;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketCreateRequestDto {

    @NotBlank
    private String name;
    private String description;
    @EnumSubsetOf(enumClass = Color.class)
    private String color;
    private LocalDateTime deadline;
    private List<String> assigneeEmails;

}
