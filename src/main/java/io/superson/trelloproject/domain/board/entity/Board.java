package io.superson.trelloproject.domain.board.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import java.awt.*;

@Entity
@Getter
@Table(name = "tb_board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String name;

    //    @Enumerated(EnumType.STRING)
    @Column
    private Color color;

    @Column(nullable = false)
    private String description;

}
