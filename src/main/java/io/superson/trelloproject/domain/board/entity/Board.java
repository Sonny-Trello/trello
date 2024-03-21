package io.superson.trelloproject.domain.board.entity;

import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.user.entity.User;
import io.superson.trelloproject.global.util.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tb_board")
@NoArgsConstructor

@SQLDelete(sql = "update tb_board set deleted_at = NOW() where board_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String description;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "board")
    private List<UserBoard> userBoard = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto requestDto, User user) {
        this.name = requestDto.getName();
        this.color = Color.valueOf(requestDto.getColor());
        this.description = requestDto.getDescription();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.name = requestDto.getName();
        this.color = Color.valueOf(requestDto.getColor());
        this.description = requestDto.getDescription();
    }
}
