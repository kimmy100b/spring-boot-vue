package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class NoticeDTO {
    private int nid;
    private String writer;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
}
