package com.springbootvue.controller;

import com.springbootvue.Service.FileService;
import com.springbootvue.Service.NoticeService;
import com.springbootvue.dto.FileDTO;
import com.springbootvue.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    final private NoticeService noticeService;
    final private FileService fileService;

    @Autowired
    public NoticeController(NoticeService noticeService, FileService fileService) {
        this.noticeService = noticeService;
        this.fileService = fileService;
    }

    // 공지사항 목록 가져오기
    @GetMapping("/getNoticeList")
    public List<NoticeDTO> getNoticeList() {
        return noticeService.getNoticeList();
    }

    // 공지사항 상세보기 가져오기 + 조회수 증가
    @GetMapping("/getNoticeView")
    public NoticeDTO getNoticeAndIncreaseViews(
            @RequestParam(value = "id") Integer id
    ) {
        return noticeService.getNoticeAndIncreaseViews(id);
    }

    // 특정 공지사항 가져오기
    @GetMapping("/getNoticeInfo")
    public NoticeDTO getNoticeInfo(
            @RequestParam(value = "id") Integer id
    ) {
        return noticeService.getNoticeInfo(id);
    }

    // 공지사항 삭제하기
    @DeleteMapping("/deleteNotice")
    public void deleteNotice(
            @RequestParam(value = "id") Integer id
    ) {
        noticeService.deleteNotice(id);
    }

    // 공지사항 등록하기
    @PostMapping("/addNotice")
    public void addNotice(
            @ModelAttribute NoticeDTO noticeDTO
    ) throws IOException {
        noticeService.addNotice(noticeDTO); // TODO 추후 회원가입 기능 구현되면 작성자를 server에서 추가한다.
    }

    // 공지사항 수정하기
    @PostMapping("/editNotice")
    public void editNotice(
            @ModelAttribute NoticeDTO noticeDTO
    ) throws IOException {
        noticeService.editNotice(noticeDTO);
    }
}
