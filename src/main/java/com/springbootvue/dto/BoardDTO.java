package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@ToString
@Getter
@Setter
public class BoardDTO {
    /** 게시판 id (PK) **/
    private int bid;

    /** 게시판 작성자 **/
    private String writer;

    /** 게시판 제목 **/
    private String title;

    /** 게시판 내용 **/
    private String content;

    /** 게시판 댓글 수 **/
    private int cntComment;

    /** 게시판 조회수 **/
    private int views;

    /** 게시판 엄지척 수 **/
    private int thumbUp;

    /** 게시판 작성일 **/
    private Timestamp regDate;

    /** 게시판 수정일 **/
    private Timestamp modDate;

    /** 게시판 첨부파일 **/
    private List<MultipartFile> files;

    /** 기존 첨부파일 중 삭제할 첨부파일 **/
    private List<Integer> delFids;

    /** 게시판 수 **/
    private int listCnt;

    /** 게시판 index - bid는 PK **/
    private int rowNum;

    /** 작성자 프로필 URL **/
    private String imgUrl;
}
