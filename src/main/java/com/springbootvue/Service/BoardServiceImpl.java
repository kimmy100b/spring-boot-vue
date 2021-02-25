package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dao.CommentDAO;
import com.springbootvue.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class BoardServiceImpl implements BoardService {
    /** 게시판 DAO **/
    final private BoardDAO boardDAO;

    /** 댓글 DAO **/
    final private CommentDAO commentDAO;

    /** 첨부파일 DAO **/
    final private FileService fileService;

    final private String POST_TYPE = "board";

    @Autowired
    public BoardServiceImpl(
            BoardDAO boardDAO, CommentDAO commentDAO, FileService fileService) {
        this.boardDAO = boardDAO;
        this.commentDAO = commentDAO;
        this.fileService = fileService;
    }


    /** 게시물 목록 조회하기 **/
    @Override
    public List<BoardDTO> getBoardList(int startIndex, int pageSize) {

        return boardDAO.selectBoardList(startIndex, pageSize);
    }

    /** 게시물 등록하기 **/
    @Transactional
    @Override
    public void addBoard(BoardDTO boardDTO) throws IOException {
        // 게시물 등록
        int rs = boardDAO.insertBoardInfo(
                boardDTO.getWriter(), boardDTO.getTitle(), boardDTO.getContent());
        if (rs != 1) {
            showMessageDialog(null, "등록 실패하였습니다.");
        } else {
            int bid = boardDAO.selectMaxBoardBid();

            // 첨부파일 등록
            if (boardDTO.getFiles() != null) {
                fileService.addFiles(POST_TYPE, bid, boardDTO.getFiles());
            }
        }
    }

    /** 게시물 수정하기 **/
    @Override
    public void modifyBoard(BoardDTO boardDTO) throws IOException {
        int rs = boardDAO.updateBoardInfo(
                boardDTO.getBid(),
                boardDTO.getWriter(),
                boardDTO.getTitle(),
                boardDTO.getContent());

        if (rs != 1) {
            showMessageDialog(null, "수정 실패하였습니다.");
        }

        int bid = boardDTO.getBid();

        // 삭제한 첨부파일 삭제
        if (boardDTO.getDelFids() != null) {
            fileService.deleteFile(POST_TYPE, bid, boardDTO.getDelFids());
        }

        // 새로운 첨부파일 등록
        if (boardDTO.getFiles() != null) {
            fileService.addFiles(POST_TYPE, bid, boardDTO.getFiles());
        }
    }

    /** 게시물 정보 가져오기 **/
    @Override
    public BoardDTO getBoardInfo(int bid) {

        return boardDAO.selectBoardInfo(bid);
    }

    /** 게시물 삭제하기 **/
    @Override
    public void deleteBoard(int bid) {
        int rs = boardDAO.deleteBoard(bid);
        if (rs != 1) {
            showMessageDialog(null, "게시물 삭제에 실패하였습니다.");
        }
    }

    /** 게시물 조회수 증가 **/
    @Override
    public void increaseBoardViews(int bid) {
        int rs = boardDAO.updateBoardViews(bid);

        if (rs != 1) {
            showMessageDialog(null, "조회수 증가에 실패하였습니다.");
        }
    }

    /** 게시물 총 개시물 수 **/
    @Override
    public int selectBoardListCnt() {
        return boardDAO.selectBoardListCnt();
    }
}
