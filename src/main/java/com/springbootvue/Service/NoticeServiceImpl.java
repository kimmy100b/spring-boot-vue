package com.springbootvue.Service;

import com.springbootvue.dao.NoticeDAO;
import com.springbootvue.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class NoticeServiceImpl implements NoticeService {

    final private NoticeDAO noticeDAO;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO) { this.noticeDAO = noticeDAO; }


    // 공지사항 목록 가져오기
    @Override
    public List<NoticeDTO> getNoticeList() {
        return noticeDAO.getNoticeList();
    }

    // 공지사항 상세보기 가져오기 + 조회수 증가
    @Override
    public NoticeDTO getNoticeAndIncreaseViews(Integer id) {
        int cnt = noticeDAO.increaseNoticeViews(id);
        if (cnt != 1) {
            showMessageDialog(null, "조회수 증가에 실패하였습니다.");
        }
        return noticeDAO.getNoticeInfo(id);
    }

    // 특정 공지사항 가져오기
    @Override
    public NoticeDTO getNoticeInfo(Integer id) {
        return noticeDAO.getNoticeInfo(id);
    }

    // 공지사항 삭제하기
    @Override
    public void deleteNotice(Integer id) {
        int cnt = noticeDAO.deleteNotice(id);
        if (cnt != 1) {
            showMessageDialog(null, "삭제에 실패하였습니다.");
        }
    }

    // 공지사항 등록하기
    @Override
    public void addNotice(NoticeDTO noticeDTO) {
        int cnt = noticeDAO.addNotice(noticeDTO.getTitle(), noticeDTO.getContent());
        if (cnt != 1) {
            showMessageDialog(null, "등록에 실패하였습니다.");
        }
    }

    // 공지사항 수정하기
    @Override
    public void editNotice(NoticeDTO noticeDTO) {
        int cnt = noticeDAO.editNotice(
            noticeDTO.getNid(),
            noticeDTO.getTitle(),
            noticeDTO.getContent());
        if (cnt != 1) {
            showMessageDialog(null, "수정에 실패하였습니다.");
        }
    }
}

