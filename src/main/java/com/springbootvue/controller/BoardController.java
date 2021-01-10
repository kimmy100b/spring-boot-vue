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
    public int addBoard(BoardDTO boardDTO){
        // TODO
        int rs = boardService.addBoard(boardDTO);

        if(rs==1){
           return rs;
        }else{
            return -1; //저장 실패
        }
    }

    // 게시판 상세보기
    @GetMapping("/getBoardView")
    public BoardDTO getBoardView(@RequestParam(value = "id") int id) {
        return boardService.getBoardView(id);
    }

    // 게시판 삭제하기
    @GetMapping("/deleteBoard")
    public int deleteNotice(@RequestParam(value = "id") int id){
        int rs = boardService.deleteBoard(id);
        return rs;
    }
}

