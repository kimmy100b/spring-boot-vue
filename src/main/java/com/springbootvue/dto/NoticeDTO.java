package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class NoticeDTO {
    private int nid;
    private int views;
    private String writer;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
    private List<MultipartFile> files;
    private List<Integer> delFids;
}
