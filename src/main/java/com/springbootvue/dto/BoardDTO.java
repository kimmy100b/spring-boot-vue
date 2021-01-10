package com.springbootvue.dto;

import lombok.*;

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

    @Builder
    public BoardDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public BoardDTO(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    @Builder
    public BoardDTO(int bid, String writer, String title, String content) {
        this.bid = bid;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
