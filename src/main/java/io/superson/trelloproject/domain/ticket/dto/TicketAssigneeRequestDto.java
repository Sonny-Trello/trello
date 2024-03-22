package io.superson.trelloproject.domain.ticket.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketAssigneeRequestDto {

    private List<String> assigneeEmails;

}
