package io.superson.trelloproject.domain.status.controller;

import io.superson.trelloproject.domain.common.dto.ResponseDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusRequestDto;
import io.superson.trelloproject.domain.status.dto.CreateStatusResponseDto;
import io.superson.trelloproject.domain.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/status")
public class StatusController {

    private final StatusService statusService;

    @PostMapping("/{statusId}")
    public ResponseEntity<ResponseDto<CreateStatusResponseDto>> createStatus(@PathVariable Long boardId, @PathVariable Long statusId,
                                                                             @RequestBody CreateStatusRequestDto createStatusRequestDto) {
        CreateStatusResponseDto createStatusResponseDto = statusService.createStatus(boardId, statusId, createStatusRequestDto);
        return null;
    }


}
