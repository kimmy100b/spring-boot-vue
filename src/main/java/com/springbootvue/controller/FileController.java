package com.springbootvue.controller;

import com.springbootvue.Service.FileService;
import com.springbootvue.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    final private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 해당 첨부파일 가져오기
    @GetMapping("/getFileList")
    public List<FileDTO> getFileList(
        @RequestParam(value = "type") String postType,
        @RequestParam(value = "id") Integer id
    ) {
        return fileService.getFileList(postType, id);
    }

    // 첨부파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> download(
        @RequestParam(value = "id") Integer id
    ) {
        try {
            FileDTO fileDTO = fileService.findById(id);
            Path path = Paths.get(fileDTO.getFilePath());
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            ContentDisposition contentDisposition =
                ContentDisposition.builder("attachment")
                    .filename(fileDTO.getFileName(), StandardCharsets.UTF_8).build();
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileDTO.getFileSize()))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
