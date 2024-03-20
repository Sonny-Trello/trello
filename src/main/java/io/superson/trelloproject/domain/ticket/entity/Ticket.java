package io.superson.trelloproject.domain.ticket.entity;

import io.superson.trelloproject.domain.board.entity.Board;
import io.superson.trelloproject.domain.common.entity.Timestamped;
import io.superson.trelloproject.domain.status.entity.Status;
import io.superson.trelloproject.domain.ticket.dto.TicketCreateRequestDto;
import io.superson.trelloproject.global.util.Color;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_ticket")
public class Ticket extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;
    private String description;
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    private Board board;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Assignee> assignees = new ArrayList<>();

    public void update(TicketCreateRequestDto requestDto, List<Assignee> assignees) {
        Optional.ofNullable(requestDto.getName()).ifPresent(this::setName);
        Optional.ofNullable(requestDto.getDescription()).ifPresent(this::setDescription);
        Optional.ofNullable(requestDto.getColor()).ifPresent(c -> setColor(Color.valueOf(c)));
        Optional.ofNullable(requestDto.getDeadline()).ifPresent(this::setDeadline);
        this.assignees = assignees;
    }

    public void setParents(Board board, Status status) {
        this.board = board;
        this.status = status;
    }

    public void addAssignee(Assignee assignee) {
        if (assignees.contains(assignee)) {
            return;
        }

        assignees.add(assignee);
        assignee.setTicket(this);
    }

    public void addAssignees(List<Assignee> assignees) {
        assignees.forEach(this::addAssignee);
    }

}
