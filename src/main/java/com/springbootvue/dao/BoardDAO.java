package com.springbootvue.dao;

import com.springbootvue.dto.BoardDTO;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
public interface BoardDAO {
    //게시물 목차
    List<BoardDTO> boardList();
    //게시물 글 작성
    int boardWrite(BoardDTO boardDTO);
}
