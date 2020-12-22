package com.springbootvue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor // 자동으로 모든 매개변수를 받는 생성자를 생성
@Getter // Getter 생성
@Setter // Setter 생성
public class BoardDTO {
    private int sid;
    private String writer;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
    private Timestamp delDate;
}
