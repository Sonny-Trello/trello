package io.superson.trelloproject.domain.ticket.dto;

import io.superson.trelloproject.global.util.Color;
import io.superson.trelloproject.global.util.validator.EnumSubsetOf;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class TicketUpdateRequestDto {

    @NotBlank
    private String name;
    private String description;
    @EnumSubsetOf(enumClass = Color.class)
    private String color;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

}
