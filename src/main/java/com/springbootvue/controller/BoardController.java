package com.springbootvue.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.springbootvue.Service.BoardService;
import com.springbootvue.Service.CommentService;
import com.springbootvue.Service.FileService;
import com.springbootvue.dto.BoardDTO;
import com.springbootvue.dto.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/board")
public class BoardController {

    final private BoardService boardService;

    /** 첨부파일 Service **/
    final private FileService fileService;

    /** 댓글 Service **/
    final private CommentService commentService;

    final private String POST_TYPE = "board";

    @Autowired
    public BoardController(
            BoardService boardService,
            FileService fileService,
            CommentService commentService) {
        this.boardService = boardService;
        this.fileService = fileService;
        this.commentService = commentService;
    }

    /** 게시판 목록 가져오기 **/
    @GetMapping("/getBoardList")
    public List<BoardDTO> getBoardList(@RequestParam(value = "curPage") int curPage) {

        int listCnt = boardService.selectBoardListCnt(); // 전체리스트 수
        Pagination pagination = new Pagination(curPage, listCnt);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        return boardService.getBoardList(startIndex, pageSize);
    }

    /** 페이징 정보 전달하기 **/
    @GetMapping("/getBoardPaging")
    public Pagination getBoardPaging(@RequestParam(value = "curPage") int curPage) {
        // 전체리스트 수
        int listCnt = boardService.selectBoardListCnt();
        Pagination pagination = new Pagination(curPage, listCnt);

        return pagination;
    }

    /** 게시판 등록하기 **/
    @PostMapping("/addBoard")
    public void addBoard(@ModelAttribute BoardDTO boardDTO) throws IOException {

        boardService.addBoard(boardDTO); // 게시물 내용 등록
    }

    /** 게시판 수정하기 **/
    @PostMapping("/modifyBoard")
    public void modifyBoard(@ModelAttribute BoardDTO boardDTO) throws IOException {

        boardService.modifyBoard(boardDTO);
    }

    /** 게시판 상세보기 **/
    @GetMapping("/getBoardInfo")
    public BoardDTO getBoardInfo(@RequestParam(value = "bid") int bid) {

        return boardService.getBoardInfo(bid);
    }

    /** 게시판 삭제하기 **/
    @DeleteMapping("/deleteBoard")
    public void deleteBoard(@RequestParam(value = "bid") int bid) {

        boardService.deleteBoard(bid);
        commentService.deleteCommentByBoard(bid);
        fileService.deleteFolder(POST_TYPE, bid);
    }

    /** 게시판 조회수 증가 **/
    @GetMapping("/increaseBoardViews")
    public void increaseBoardViews(@RequestParam(value = "bid") int bid) {

        boardService.increaseBoardViews(bid);
    }
}

