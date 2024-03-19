package io.superson.trelloproject.domain.ticket.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.global.util.Color;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_ticket")
public class Ticket extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    private String description;

    @Column
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
