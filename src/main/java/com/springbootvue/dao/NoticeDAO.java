package com.springbootvue.dao;

import com.springbootvue.dto.NoticeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeDAO {

    // 공지사항 목록 가져오기
    List<NoticeDTO> getNoticeList();

    // 공지사항 상세보기 가져오기
    NoticeDTO getNoticeView(@Param("id") Integer id);

    // 공지사항 삭제하기
    int deleteNotice(@Param("id") Integer id);
}

