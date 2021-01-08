package com.springbootvue.Service;

import com.springbootvue.dao.NoticeDAO;
import com.springbootvue.util.CamelCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class NoticeServiceImpl implements NoticeService {

    final private NoticeDAO noticeDAO;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO) { this.noticeDAO = noticeDAO; }

    CamelCase camelCase = new CamelCase();


    // 공지사항 목록 가져오기
    public List<Map<String, Object>> getNoticeList() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> noticeList = noticeDAO.getNoticeList();
        for (Map<String, Object> notice : noticeList) {
            result.add(camelCase.convertToCamelCase(notice));
        }
        return result;
    }

    // 공지사항 상세보기 가져오기
    public Map<String, Object> getNoticeView(Integer id) {
        Map<String, Object> noticeView = noticeDAO.getNoticeView(id);
        return camelCase.convertToCamelCase(noticeView);
    }

    // 공지사항 삭제하기
    public void deleteNotice(Integer id) {
        int cnt = noticeDAO.deleteNotice(id);
        if (cnt != 1) {
            showMessageDialog(null, "삭제에 실패하였습니다.");
        }
    }
}
