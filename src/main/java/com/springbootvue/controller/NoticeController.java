package com.springbootvue.controller;

import com.springbootvue.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    final private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 목록 가져오기
    @GetMapping("/getNoticeList")
    public List<Map<String, Object>> getNoticeList() {
        return noticeService.getNoticeList();
    }

    // 공지사항 상세보기 가져오기
    @GetMapping("/getNoticeView")
    public Map<String, Object> getNoticeView(
            @RequestParam(value="id") Integer id
    ) {
        return noticeService.getNoticeView(id);
    }

    // 공지사항 삭제하기
    @GetMapping("/deleteNotice")
    public void deleteNotice(
        @RequestParam(value="id") Integer id
    ) {
        noticeService.deleteNotice(id);
    }
}
