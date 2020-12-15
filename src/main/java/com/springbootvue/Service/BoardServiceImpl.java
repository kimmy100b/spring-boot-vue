package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    final private BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    /**
     * 게시물 목록 조회
     */
    @Override
    public List<BoardDTO> boardList() {
        return boardDAO.boardList();
    }

    /**
     * 게시물 글쓰기 기능
     */
    @Override
    public void boardWrite(BoardDTO boardDTO) {
        boardDAO.boardWrite(boardDTO);
    }
}
