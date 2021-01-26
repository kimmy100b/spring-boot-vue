package com.springbootvue.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter // Getter 생성
@Setter // Setter 생성
public class BoardDTO {
    // DB Table에 있는 칼럼
    private int bid;
    private String writer;
    private String title;
    private String content;
    private int cntComment;
    private int views;
    private int thumbUp;
    private Timestamp regDate;
    private Timestamp modDate;

    // 코드 필요에 의해 만들어진 변수
    private int listCnt;
    private int rowNum;
}
