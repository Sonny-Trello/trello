package io.superson.trelloproject.domain.status.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.status.dto.StatusRequestDto;
import io.superson.trelloproject.domain.status.service.StatusService;
import io.superson.trelloproject.global.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/{boardId}/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @RequestBody @Valid StatusRequestDto statusRequestDto) {
        statusService.createStatus(userDetails.getUser(), boardId, statusRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<Void>builder().build());
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<ResponseDto<Void>> updateStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @PathVariable Long statusId, @RequestBody @Valid StatusRequestDto statusRequestDto) {

        statusService.updateStatus(userDetails.getUser(), boardId, statusId, statusRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder().build());
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<ResponseDto<Void>> deleteStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @PathVariable Long statusId) {
        statusService.deleteStatus(userDetails.getUser(), boardId, statusId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder().build());
    }
}
