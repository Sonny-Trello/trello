package io.superson.trelloproject.domain.status.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.dto.UpdateStatusResponseDto;
import io.superson.trelloproject.domain.status.service.StatusService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.Valid;
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
@RequestMapping("/boards/{boardId}/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateStatusResponseDto>> createStatus(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long boardId,
        @RequestBody @Validated StatusRequestDto statusRequestDto
    ) {
        CreateStatusResponseDto statusResponseDto = statusService.createStatus(userDetails.getUser(), boardId, statusRequestDto);
        return ResponseEntity.created(createUri(statusResponseDto.getStatusId()))
            .body(ResponseDto.<CreateStatusResponseDto>builder()
                .data(statusResponseDto)
                .build());
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<ResponseDto<UpdateStatusResponseDto>> updateStatus(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId, @PathVariable Long statusId,
            @RequestBody @Valid StatusRequestDto statusRequestDto
    ) {
        UpdateStatusResponseDto updateStatusResponseDto = statusService.updateStatus(userDetails.getUser(), boardId, statusId, statusRequestDto);
        return ResponseEntity.created(createUri(updateStatusResponseDto.getStatusId()))
                .body(ResponseDto.<UpdateStatusResponseDto>builder()
                        .data(updateStatusResponseDto)
                        .build());
    }

    @PatchMapping("/{statusId}/movement")
    public ResponseEntity<ResponseDto<Void>> updateStatusNumber(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @PathVariable Long statusId,
            @RequestParam Float frontPositionNumber
    ) {
        statusService.updateStatusNumber(userDetails.getUser(), boardId, statusId, frontPositionNumber);
        return ResponseEntity.created(createUri(statusId))
                .body(ResponseDto.<Void>builder().build());
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<ResponseDto<Void>> deleteStatus(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @PathVariable Long statusId
    ) {
        statusService.deleteStatus(userDetails.getUser(), boardId, statusId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.<Void>builder().build());
    }

    private URI createUri(Long statusId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(statusId)
            .toUri();
    }
}
