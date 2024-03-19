package io.superson.trelloproject.domain.status.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
