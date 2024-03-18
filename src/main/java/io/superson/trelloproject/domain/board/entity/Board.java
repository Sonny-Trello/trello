package io.superson.trelloproject.domain.board.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.common.enumeration.Color;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    private String description;

}
