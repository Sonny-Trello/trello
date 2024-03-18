package io.superson.trelloproject.domain.user.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userBoardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
