package io.superson.trelloproject.domain.ticket.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.status.entity.Status;
import jakarta.persistence.*;

import java.awt.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_ticket")
public class Ticket extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    //    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    @Column
    private Color color;

    @Column(nullable = false)
    private String description;

    @Column
    private Timestamp deadline;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
