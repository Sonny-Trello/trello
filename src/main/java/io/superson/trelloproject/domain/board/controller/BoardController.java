package io.superson.trelloproject.domain.board.controller;

import io.superson.trelloproject.domain.board.dto.BoardRequestDto;
import io.superson.trelloproject.domain.board.dto.BoardResponseDto;
import io.superson.trelloproject.domain.board.dto.InviteRequestDto;
import io.superson.trelloproject.domain.board.dto.InviteResponseDto;
import io.superson.trelloproject.domain.board.service.BoardService;
import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("boards")
    public ResponseEntity<ResponseDto<BoardResponseDto>> createBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid BoardRequestDto requestDto
    ) {
        BoardResponseDto responseDto = boardService.createBoard(userDetails.getUser(), requestDto);

        return ResponseEntity.created(createUri(responseDto.getBoardId()))
            .body(ResponseDto.<BoardResponseDto>builder()
                .data(responseDto)
                .build());
    }

    @PutMapping("boards/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> updateBoard(
        @PathVariable Long id,
        @RequestBody @Valid BoardRequestDto requestDto
    ) {
        BoardResponseDto responseDto = boardService.updateBoard(id, requestDto);

        return ResponseEntity.created(createUri(responseDto.getBoardId()))
            .body(ResponseDto.<BoardResponseDto>builder()
                .data(responseDto)
                .build());
    }

    @PatchMapping("boards/{id}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> deleteBoard(
        @PathVariable Long id
    ) {
        boardService.deleteBoard(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("boards")
    public ResponseEntity<ResponseDto<List<BoardResponseDto>>> getBoards(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<BoardResponseDto> responseDto = boardService.getBoards(userDetails.getUser());

        return ResponseEntity.ok(ResponseDto.<List<BoardResponseDto>>builder()
            .data(responseDto)
            .build());
    }

    @PostMapping("boards/{id}/invite")
    public ResponseEntity<ResponseDto<InviteResponseDto>> inviteBoard(
        @PathVariable Long id,
        @RequestBody String email
    ) {
        InviteResponseDto responseDto = boardService.inviteBoard(id, email);

        return ResponseEntity.ok(ResponseDto.<InviteResponseDto>builder()
            .data(responseDto)
            .build());
    }

    private URI createUri(Long todoId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(todoId)
            .toUri();
    }

}
