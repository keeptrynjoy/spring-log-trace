package spring.log.web.domain.controller;

import spring.log.web.domain.dto.RegistrationBoardRequest;
import spring.log.web.domain.service.BoardService;
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
