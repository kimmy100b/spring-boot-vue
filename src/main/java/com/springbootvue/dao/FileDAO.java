package com.springbootvue.dao;

import com.springbootvue.dto.FileDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileDAO {

    // 파일 정보 추가하기
    int addFile(
        @Param("postType") String postType, // 업로드한 게시판 타입
        @Param("postId") Integer postId, // 업로드한 게시판 번호
        @Param("fileName") String fileName,
        @Param("fileSize") Long fileSize,
        @Param("filePath") String filePath, // 파일 저장 장소(서버)
        @Param("uploadUser") String uploadUser
    );

    // 파일 리스트 가져오기
    List<FileDTO> getFileList(String postType, Integer postId);

    // 첨부파일 정보 가져오기
    FileDTO findById(@Param("fid") Integer id);

    //첨부파일 폴더 삭제
    int deleteFolder(
        @Param("postType") String postType,
        @Param("postId") Integer postId
    );

    //첨부파일 파일 삭제
    int deleteFile(
        @Param("fid") Integer fid
    );
}
