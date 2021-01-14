package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class BoardServiceImpl implements BoardService{

    final private BoardDAO boardDAO;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    // 게시물 목록 조회
    @Override
    public List<BoardDTO> getBoardList() {

        return boardDAO.getBoardList();
    }

    // 게시물 글쓰기 기능
    @Override
    public void addBoard(BoardDTO boardDTO) {

        int rs = boardDAO.addBoard(boardDTO);

        if(rs != 1){
            showMessageDialog(null, "등록 실패하였습니다.");
        }
    }

    // 게시물 수정 기능
    @Override
    public void modifyBoard(BoardDTO boardDTO){
        int rs = boardDAO.modifyBoard(boardDTO);

        if(rs != 1){
            showMessageDialog(null, "수정 실패하였습니다.");
        }
    }

    // 게시물 상세보기
    @Override
    public BoardDTO getBoardInfo(int bid) {

        return boardDAO.getBoardInfo(bid);
    }

    // 게시물 삭제 기능
    @Override
    public void deleteBoard(int bid) {
        int rs = boardDAO.deleteBoard(bid);

        if(rs != 1){
            showMessageDialog(null, "삭제 실패하였습니다.");
        }
    }

    // 게시물 조회수 증가
    @Override
    public void increaseBoardViews(int bid) {
        int rs = boardDAO.increaseBoardViews(bid);

        if(rs != 1){
            showMessageDialog(null, "조회수 증가에 실패하였습니다.");
        }

    }
}
