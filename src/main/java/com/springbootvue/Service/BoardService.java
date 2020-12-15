package com.springbootvue.Service;

import com.springbootvue.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    //게시물 목록 조회
    List<BoardDTO> boardList();

    //게시물 글쓰기 기능
    void boardWrite(BoardDTO boardDTO);
}
