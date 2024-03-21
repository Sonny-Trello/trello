package io.superson.trelloproject.domain.comment.controller;

import io.superson.trelloproject.domain.comment.dto.CommentRequestDto;
import io.superson.trelloproject.domain.comment.dto.CommentResponseDto;
import io.superson.trelloproject.domain.comment.service.CommentService;
import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/status/{statusId}/tickets/{ticketId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @PathVariable Long ticketId,
            @PathVariable Long statusId,
            @RequestBody @Validated CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto commentResponseDto = commentService.createComment(userDetails.getUser(), boardId, ticketId, commentRequestDto);
        return ResponseEntity.created(createUri(boardId, statusId, ticketId))
                .body(ResponseDto.<CommentResponseDto>builder().data(commentResponseDto).build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @PathVariable Long statusId,
            @PathVariable Long ticketId,
            @PathVariable Long commentId,
            @RequestBody @Validated CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto commentResponseDto = commentService.updateComment(userDetails.getUser(), boardId, ticketId, commentId, commentRequestDto);
        return ResponseEntity.created(createUri(boardId, statusId, ticketId))
                .body(ResponseDto.<CommentResponseDto>builder().data(commentResponseDto).build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @PathVariable Long statusId,
            @PathVariable Long ticketId, @PathVariable Long commentId
    ) {
        commentService.deleteComment(userDetails.getUser(), boardId, ticketId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.<Void>builder().build());
    }

    private URI createUri(Long boardId, Long statusId, Long ticketId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath("/v1/boards/{boardId}/status/{statusId}/ticket/{ticketId}")
                .buildAndExpand(boardId, statusId, ticketId)
                .toUri();
    }
}
