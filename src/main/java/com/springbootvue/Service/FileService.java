package com.springbootvue.Service;

import com.springbootvue.dto.FileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    // 첨부파일 추가
    void addFiles(String postType, int nid, List<MultipartFile> files) throws IOException;
    // 해당 첨부파일 가져오기
    List<FileDTO> getFileList(String postType, int id);
    // fid로 첨부파일 찾기
    FileDTO findById(int id);
    // 첨부파일 폴더 삭제
    void deleteFolder(String postType, int postId);
    // 선택적 첨부파일 삭제
    void deleteFile(String postType, int postId, List<Integer> delFids);
}
