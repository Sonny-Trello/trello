package io.superson.trelloproject.domain.comment.controller;

import io.superson.trelloproject.domain.comment.dto.CommentRequestDto;
import io.superson.trelloproject.domain.comment.service.CommentService;
import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/tickets/{ticketId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId,
                                                           @PathVariable Long ticketId, @RequestBody @Validated CommentRequestDto commentRequestDto) {
        commentService.createComment(userDetails.getUser(), boardId, ticketId, commentRequestDto);
        return ResponseEntity.created(createUri(boardId))
                .body(ResponseDto.<Void>builder().build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId,
                                                           @PathVariable Long ticketId, @PathVariable Long commentId,
                                                           @RequestBody @Validated CommentRequestDto commentRequestDto) {
        commentService.updateComment(userDetails.getUser(), boardId, ticketId, commentId, commentRequestDto);
        return ResponseEntity.created(createUri(boardId))
                .body(ResponseDto.<Void>builder().build());
    }

    private URI createUri(Long boardId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("/v1/boards/{boardId}")
                .buildAndExpand(boardId)
                .toUri();
    }
}
