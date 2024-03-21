package io.superson.trelloproject.domain.comment.entity;

import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.ticket.entity.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
    임시로 만들어둔 엔티티입니다.
    컨플릭트 발생시 `9noeyni9`님의 코드로 변경해주세요.
     */
@Entity
@Table(name = "tb_comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
