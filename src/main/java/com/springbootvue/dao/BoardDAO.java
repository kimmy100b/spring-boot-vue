package com.springbootvue.dao;

import com.springbootvue.dto.BoardDTO;

import java.util.List;

public interface BoardDAO {
    //게시물 목차
    List<BoardDTO> getBoardList();
    //게시물 글 작성
    int addBoard(BoardDTO boardDTO);
    //게시물 상세보기
    BoardDTO getBoardView(int id);
    //게시물 삭제
    int deleteBoard(int id);
}
