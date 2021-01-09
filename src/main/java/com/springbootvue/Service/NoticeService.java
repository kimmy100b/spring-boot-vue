package com.springbootvue.Service;

import com.springbootvue.dao.NoticeDAO;
import com.springbootvue.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class NoticeService {

    final private NoticeDAO noticeDAO;

    @Autowired
    public NoticeService(NoticeDAO noticeDAO) { this.noticeDAO = noticeDAO; }


    // 공지사항 목록 가져오기
    public List<NoticeDTO> getNoticeList() {
        return noticeDAO.getNoticeList();
    }

    // 공지사항 상세보기 가져오기
    public NoticeDTO getNoticeView(Integer id) {
        return noticeDAO.getNoticeView(id);
    }

    // 공지사항 삭제하기
    public void deleteNotice(Integer id) {
        int cnt = noticeDAO.deleteNotice(id);
        if (cnt != 1) {
            showMessageDialog(null, "삭제에 실패하였습니다.");
        }
    }
}
