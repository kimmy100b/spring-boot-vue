package com.springbootvue.Service;

import com.springbootvue.dao.FileDAO;
import com.springbootvue.dao.NoticeDAO;
import com.springbootvue.dto.FileDTO;
import com.springbootvue.dto.NoticeDTO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class NoticeServiceImpl implements NoticeService {
    final private String POST_TYPE = "notice";
    final private NoticeDAO noticeDAO;
    final private FileService fileService;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO, FileService fileService) {
        this.noticeDAO = noticeDAO;
        this.fileService = fileService;
    }

    @Autowired


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
    @Transactional
    @Override
    public void deleteNotice(Integer id) {
        int cnt = noticeDAO.deleteNotice(id);
        if (cnt != 1) {
            showMessageDialog(null, "삭제에 실패하였습니다.");
        }

        fileService.deleteFolder(POST_TYPE,id);
    }

    // 공지사항 등록하기
    @Transactional
    @Override
    public void addNotice(NoticeDTO noticeDTO) throws IOException {
        int cnt = noticeDAO.addNotice(noticeDTO.getTitle(), noticeDTO.getContent());
        if (cnt != 1) {
            showMessageDialog(null, "등록에 실패하였습니다.");
        }

        int nid = noticeDAO.getNoticeNid();

        // 첨부파일 등록
        if (noticeDTO.getFiles() != null) {
            fileService.addFiles(POST_TYPE, nid,noticeDTO.getFiles());
        }
    }

    // 공지사항 수정하기
    @Transactional
    @Override
    public void editNotice(NoticeDTO noticeDTO) throws IOException {
        int cnt = noticeDAO.editNotice(
            noticeDTO.getNid(),
            noticeDTO.getTitle(),
            noticeDTO.getContent());
        if (cnt != 1) {
            showMessageDialog(null, "수정에 실패하였습니다.");
        }

        int nid = noticeDTO.getNid();

        // 첨부파일 삭제
        if (noticeDTO.getDelFids() != null) {
            fileService.deleteFile(POST_TYPE, nid, noticeDTO.getDelFids());
        }

        // 첨부파일 등록
        if (noticeDTO.getFiles() != null) {
            fileService.addFiles(POST_TYPE, nid,noticeDTO.getFiles());
        }
    }
}

