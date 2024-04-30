package com.example.logtrace.domain.controller;

import com.example.logtrace.domain.dto.RegistrationBoardRequest;
import com.example.logtrace.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<Void> registrationBoard(@RequestBody RegistrationBoardRequest registrationBoardRequest) {
        boardService.createBoard(registrationBoardRequest.subject(), registrationBoardRequest.content());
        return ResponseEntity.ok().build();
    }
}
