package com.springbootvue.controller;

import com.springbootvue.Service.BoardService;
import com.springbootvue.Service.BoardServiceImpl;
import com.springbootvue.dto.BoardDTO;
import com.springbootvue.dto.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board")
public class BoardController {

    final private BoardService boardService;

    @Autowired
    public BoardController(BoardServiceImpl boardServiceImpl) {

        this.boardService = boardServiceImpl;
    }

    // 게시판 목록 가져오기
    @GetMapping("/getBoardList")
    public List<BoardDTO> getBoardList(@RequestParam(value = "curPage") int curPage){

        // 전체리스트 수
        int listCnt = boardService.selectBoardListCnt();
        Pagination pagination = new Pagination(curPage, listCnt);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        return boardService.getBoardList(startIndex, pageSize);
    }

    // 페이징 정보 전달
    @GetMapping("/getBoardPaging")
    public Pagination getBoardPaging(@RequestParam(value = "curPage") int curPage){
        // 전체리스트 수
        int listCnt = boardService.selectBoardListCnt();
        Pagination pagination = new Pagination(curPage, listCnt);

        return pagination;
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

