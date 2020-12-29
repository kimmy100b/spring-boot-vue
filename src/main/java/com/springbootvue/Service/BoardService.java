package com.springbootvue.Service;

import com.springbootvue.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    //게시물 목록 조회
    List<BoardDTO> getBoardList();

    //게시물 글쓰기 기능
    int setBoardWrite(BoardDTO boardDTO);
    
    //게시물 상세보기 페이지
    BoardDTO getBoardView(int id);
}
