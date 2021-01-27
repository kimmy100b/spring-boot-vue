package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
public class FileDTO {
    private int fid;
    private String postType;
    private int postId;
    private String fileName;
    private Long fileSize;
    private String filePath; // 파일 저장 장소(서버)
    private Timestamp uploadDate;
    private String uploadUser;
    private boolean isDelete;
}
