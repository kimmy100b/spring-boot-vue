package com.springbootvue.Service;

import com.springbootvue.dto.FileDTO;
import com.springbootvue.dto.NoticeDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface NoticeService {

    // 공지사항 목록 가져오기
    public List<NoticeDTO> getNoticeList();

    // 공지사항 상세보기 가져오기 + 조회수 증가
    public NoticeDTO getNoticeAndIncreaseViews(Integer id);

    // 특정 공지사항 가져오기
    public NoticeDTO getNoticeInfo(Integer id);

    // 공지사항 삭제하기
    public void deleteNotice(Integer id);

    // 공지사항 등록하기
    public void addNotice(NoticeDTO noticeDTO) throws IOException;

    // 공지사항 수정하기
    public void editNotice(NoticeDTO noticeDTO) throws IOException;
}
