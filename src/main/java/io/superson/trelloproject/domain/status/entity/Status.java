package io.superson.trelloproject.domain.status.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Status extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
