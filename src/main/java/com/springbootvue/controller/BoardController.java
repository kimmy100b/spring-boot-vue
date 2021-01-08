package com.springbootvue.controller;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    final private BoardDAO boardService;

    @Autowired
    public BoardController(BoardDAO boardService) {
        this.boardService = boardService;
    }

    /**
     * 게시판 목록
     */
    @PostMapping("/list")
    public List<BoardDTO> getBoardList(){
        List<BoardDTO> boardList = boardService.getBoardList();

        return boardList;
    }

    /**
     * 게시판 글쓰기
     */
    @PostMapping("/write")
    public int setBoardWrite(BoardDTO boardDTO){
        int rs = boardService.setBoardWrite(boardDTO);

        if(rs==1){
           return rs;
        }else{
            return -1; //저장 실패
        }
    }

    /**
     * 게시판 상세보기
     */
    @GetMapping("/view")
    public BoardDTO getBoardView(@RequestParam(value = "id") int id){
        BoardDTO boardDTO = boardService.getBoardView(id);
        return boardDTO;
    }
}

