package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dao.CommentDAO;
import com.springbootvue.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDAO commentDAO;
    private final BoardDAO boardDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO, BoardDAO boardDAO) {
        this.commentDAO = commentDAO;
        this.boardDAO = boardDAO;
    }

    @Override
    public List<CommentDTO> getCommentList(Integer gubun, Integer fkId) {

        return commentDAO.getCommentList(gubun, fkId);
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        int comRs = commentDAO.addComment(commentDTO);
        if(comRs != 1){
            showMessageDialog(null, "등록 실패하였습니다.");
        }
    }

    @Override
    public void modifyComment(CommentDTO commentDTO) {
        int rs = commentDAO.modifyComment(commentDTO);

        if(rs != 1){
            showMessageDialog(null, "수정 실패하였습니다.");
        }
    }

    @Override
    public void deleteComment(Integer cid, Integer fkId) {
        int comRs = commentDAO.deleteComment(cid);
        if(comRs != 1){
            showMessageDialog(null, "삭제 실패하였습니다.");
        }
    }

    @Override
    public int getCntComment(Integer gubun, Integer fkId) {

        return commentDAO.getCntComment(gubun, fkId);
    }
}
