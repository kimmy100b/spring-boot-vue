package com.springbootvue.Service;

import com.springbootvue.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    // 댓글 목차
    List<CommentDTO> getCommentList(Integer gubun, Integer fkId);

    // 댓글 작성
    void addComment(CommentDTO commentDTO);

    // 댓글 수정 기능
    void modifyComment(CommentDTO commentDTO);

    // 댓글 삭제
    void deleteComment(Integer cid, Integer fkId);

    // 댓글 전체 개수
    int getCntComment(Integer gubun, Integer fkId);
}
