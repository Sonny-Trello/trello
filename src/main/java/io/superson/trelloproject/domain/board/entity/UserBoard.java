package io.superson.trelloproject.domain.board.entity;


import io.superson.trelloproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_user_board")
@NoArgsConstructor
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

    public UserBoard(User user, Board board) {
        this.user = user;
        this.board = board;
    }

}
