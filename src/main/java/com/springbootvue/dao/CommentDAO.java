package com.springbootvue.dao;

import com.springbootvue.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDAO {
    // 댓글 목차
    List<CommentDTO> getCommentList(
            @Param("postType") String postType,
            @Param("postId") Integer postId);

    // 댓글 작성
    int addComment(CommentDTO commentDTO);

    // 댓글 수정 기능
    int modifyComment(CommentDTO commentDTO);

    // 댓글 삭제
    int deleteComment(
            @Param("cid") Integer cid);

    // 댓글 전체 개수
    int getCntComment(
            @Param("postType") String postType,
            @Param("postId") Integer postId);

    // 게시물 삭제 시 댓글 삭제
    int deleteCommentByBoard(int postId);
}
