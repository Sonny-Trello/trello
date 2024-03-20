package io.superson.trelloproject.domain.board.entity.Invite;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_invite")
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inviteId;

    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    private String userId;
    private Long boardId;

    public Invite(Board board, User user) {
        this.boardId = board.getBoardId();
        this.userId = user.getUserId();
        this.status = InviteStatus.STANDBY;
    }

    public void update(String status) {
        this.status = InviteStatus.valueOf(status);
    }
}
