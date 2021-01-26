package com.springbootvue.Service;

import com.springbootvue.dto.BoardDTO;

import java.util.List;


public interface BoardService {
    // 게시물 목차 기능
    List<BoardDTO> getBoardList(int startIndex, int pageSize);

    // 게시물 글쓰기 기능
    void addBoard(BoardDTO boardDTO);

    // 게시물 수정 기능
    void modifyBoard(BoardDTO boardDTO);
    
    // 게시물 상세보기
    BoardDTO getBoardInfo (int bid);

    // 게시물 삭제 기능
    void deleteBoard(int bid);

    // 게시물 조회수 증가
    void increaseBoardViews(int bid);
    
    // 게시물 전체 수
    int selectBoardListCnt();
}
