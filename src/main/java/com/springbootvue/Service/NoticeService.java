package com.springbootvue.Service;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    // 공지사항 목록 가져오기
    List<Map<String, Object>> getNoticeList();

    // 공지사항 상세보기 가져오기
    Map<String, Object> getNoticeView(Integer id);

    // 공지사항 삭제하기
    void deleteNotice(Integer id);
}
