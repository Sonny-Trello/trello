package io.superson.trelloproject.domain.ticket.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketAssigneeRequestDto {

    private final List<String> assigneeEmails;

}
