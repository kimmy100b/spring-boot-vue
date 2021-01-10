package com.springbootvue.dao;

import com.springbootvue.dto.BoardDTO;

import java.util.List;

public interface BoardDAO {
    //게시물 목차
    List<BoardDTO> getBoardList();
    //게시물 글 작성
    int addBoard(BoardDTO boardDTO);
    //게시물 수정 기능
    int modifyBoard(BoardDTO boardDTO);
    //게시물 상세보기
    BoardDTO getBoardInfo(int bid);
    //게시물 삭제
    int deleteBoard(int bid);
    //게시물 조회수 증가
    int increaseBoardViews(int bid);
}
