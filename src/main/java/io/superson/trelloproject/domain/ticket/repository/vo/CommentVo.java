package io.superson.trelloproject.domain.ticket.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVo {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    @QueryProjection
    public CommentVo(Long commentId, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
    }

}
