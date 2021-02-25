package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentDTO {
    /** 댓글 sid **/
    private int cid;

    /** 게시판 타입 **/
    private String postType;

    /** 게시판 번호 **/
    private int postId;

    /** 답변형 댓글 위한 레벨 **/
    private int level;

    /** 답변형 댓글 위한 상위 id **/
    private int parentId;

    /** 작성자 **/
    private String writer;

    /** 내용 **/
    private String content;

    /** 등록일 **/
    private Timestamp regDate;

    /** 수정일 **/
    private Timestamp modDate;

    /** 총 댓글 수 **/
    private int cnt;

    /** 작성자 프로필 URL **/
    private String imgUrl;
}
