package com.springbootvue.controller;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/list")
    public void boardList(Model model){
        List<BoardDTO> list = boardService.boardList();
        model.addAttribute("list", list);
    }

    /**
     * 게시판 글쓰기
     */
    @PostMapping("/write")
    public int boardWrite(BoardDTO boardDTO){
        int rs = boardService.boardWrite(boardDTO);

        if(rs==1){
           return rs;
        }else{
            return -1; //저장 실패
        }
    }
}

