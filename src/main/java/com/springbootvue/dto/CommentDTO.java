package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentDTO {
    private int cid;
    private int gubun;
    private int fkId;
    private int level;
    private int parentId;
    private String writer;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
    
    // 총 댓글 수
    private int cnt;
}
