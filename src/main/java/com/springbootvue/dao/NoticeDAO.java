package com.springbootvue.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeDAO {

    // 공지사항 목록 가져오기
    List<Map<String, Object>> getNoticeList();

    // 공지사항 상세보기 가져오기
    Map<String, Object> getNoticeView(@Param("id") Integer id);

    // 공지사항 삭제하기
    int deleteNotice(@Param("id") Integer id);
}

