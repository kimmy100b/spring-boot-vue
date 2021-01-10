package com.springbootvue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter // Getter 생성
@Setter // Setter 생성
public class BoardDTO {
    private int bid;
    private String writer;
    private String title;
    private String content;
    private String views;
    private Timestamp regDate;
    private Timestamp modDate;
}
