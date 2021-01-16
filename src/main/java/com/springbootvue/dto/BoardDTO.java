package com.springbootvue.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter // Getter 생성
@Setter // Setter 생성
public class BoardDTO {
    private int bid;
    private String writer;
    private String title;
    private String content;
    private int cntComment;
    private int views;
    private int thumbUp;
    private Timestamp regDate;
    private Timestamp modDate;
}
