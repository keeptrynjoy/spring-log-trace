package com.example.logtrace.domain.service;

import com.example.logtrace.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(String subject, String content) {
        log.debug("====== Subject : {} ",subject);
        if(Objects.equals(subject, "exception_test")){
            throw new RuntimeException("exception_test");
        }
        boardRepository.insertBoard(subject, content);
    }
}
