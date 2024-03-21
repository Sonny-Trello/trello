package io.superson.trelloproject.domain.ticket.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssigneeVo {

    private String assigneeEmail;

    @QueryProjection
    public AssigneeVo(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

}
