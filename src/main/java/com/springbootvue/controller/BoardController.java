package com.springbootvue.controller;

import com.springbootvue.Service.BoardService;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board")
public class BoardController {

    final private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {

        this.boardService = boardService;
    }

    // 게시판 목록 가져오기
    @PostMapping("/getBoardList")
    public List<BoardDTO> getBoardList(){

        return boardService.getBoardList();
    }

    // 게시판 글쓰기
    @PostMapping("/addBoard")
    public void addBoard(@RequestBody BoardDTO boardDTO){

        boardService.addBoard(boardDTO);
    }

    //게시판 수정
    @PostMapping("/modifyBoard")
    public void modifyBoard(@RequestBody BoardDTO boardDTO){

        boardService.modifyBoard(boardDTO);
    }

    // 게시판 상세보기
    @GetMapping("/getBoardInfo")
    public BoardDTO getBoardInfo(@RequestParam(value = "bid") int bid) {

        return boardService.getBoardInfo(bid);
    }

    // 게시판 삭제하기
    @DeleteMapping("/deleteBoard")
    public void deleteBoard(@RequestParam(value = "bid") int bid){

        boardService.deleteBoard(bid);
    }

    // 게시판 조회수 증가
    @GetMapping("/increaseBoardViews")
    public void increaseBoardViews(@RequestParam(value = "bid") int bid) {

        boardService.increaseBoardViews(bid);
    }
}

