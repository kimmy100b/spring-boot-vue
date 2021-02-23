package com.springbootvue.Service;

import com.springbootvue.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    /** 댓글 목차 **/
    List<CommentDTO> getCommentList(String postType, int postId);

    /** 댓글 작성 **/
    void addComment(CommentDTO commentDTO);

     /** 댓글 수정 기능 **/
    void modifyComment(CommentDTO commentDTO);

     /** 댓글 삭제 **/
    void deleteComment(int cid, int postId);

     /** 댓글 전체 개수 **/
    int getCntComment(String postType, int postId);

    /** 게시물 전체 댓글 삭제 **/
    void deleteCommentByBoard(int postId);
}
