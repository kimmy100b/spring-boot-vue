package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    final private BoardDAO boardDAO;

    @Autowired
    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    // 게시물 목록 조회
    public List<BoardDTO> getBoardList() {
        return boardDAO.getBoardList();
    }

    // 게시물 글쓰기 기능
    public int addBoard(BoardDTO boardDTO) {
        // TODO
        int rs = boardDAO.addBoard(boardDTO);
        if(rs==1){
            return rs;
        }
        return -1;
    }

    // 게시물 상세보기
    public BoardDTO getBoardView (int id) {
        return boardDAO.getBoardView(id);
    }

    public int deleteBoard(int id) {
        int rs = boardDAO.deleteBoard(id);
        if(rs==1){
            return rs;
        }
        return -1;
    }
}
