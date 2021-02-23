package com.springbootvue.Service;

import com.springbootvue.dao.BoardDAO;
import com.springbootvue.dao.CommentDAO;
import com.springbootvue.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<CommentDTO> getCommentList(String postType, int postId) {

        return commentDAO.getCommentList(postType, postId);
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
    public void deleteComment(int cid, int postId) {
        int comRs = commentDAO.deleteComment(cid);
        if(comRs != 1){
            showMessageDialog(null, "삭제 실패하였습니다.");
        }
    }

    @Override
    public int getCntComment(String postType, int postId) {

        return commentDAO.getCntComment(postType, postId);
    }

    @Override
    public void deleteCommentByBoard(int postId){
        int rs = commentDAO.deleteCommentByBoard(postId);
        if (rs < 0) {
            showMessageDialog(null, "댓글 삭제에 실패하였습니다.");
        }
    }
}
