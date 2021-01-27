package com.springbootvue.Service;

import com.springbootvue.dao.FileDAO;
import com.springbootvue.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class FileServiceImpl implements FileService{

    final private FileDAO fileDAO;
    final private static Logger LOG = Logger.getGlobal();

    //    @Value("${spring..}")  // TODO 추후 application.properties 에서 가져오도록 수정(배포할때)
    private String ROOT_PATH = FileSystemView.getFileSystemView().getHomeDirectory().toString();
    //@Value("${spring.servlet.multipart.max-file-size}") : 10MB 문자가 들어가 있음
    private int FILE_SIZE = 10*1024*1024;

    @Autowired
    public FileServiceImpl(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }

    // 첨부파일 추가
    public void addFiles(String postType, int nid, List<MultipartFile> files) throws IOException {
        String basePath = ROOT_PATH + "/" + "uploaded" + "/" + postType + "/" + nid;

        // uploaded 폴더가 없을 경우 자동생성
        File folder = new File(basePath);
        if (! folder.exists()) {
            boolean isSuccess = folder.mkdirs();
            if (! isSuccess) {
                LOG.warning("폴더 생성에 실패하였습니다.");
            }
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            Long fileSize = file.getSize();

            if(fileSize > FILE_SIZE){
                showMessageDialog(null, "첨부파일 용량을 초과했습니다.");
                return ;
            }

            String filePath = basePath + "/" + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);

            // TODO 회원가입 기능 구현 후 user 추가
            int cnt = fileDAO.addFile(postType, nid, fileName, fileSize, filePath,"김현진");
            if (cnt < 1) {
                showMessageDialog(null, "첨부파일 추가에 실패하였습니다.");
            }
        }
    }

    // 특정 게시글의 파일 목록 가져오기
    @Override
    public List<FileDTO> getFileList(String postType, int postId) {

        return fileDAO.getFileList(postType, postId);
    }

    // fid로 첨부파일 찾기
    @Override
    public FileDTO findById(int id) {
        FileDTO fileDTO = fileDAO.findById(id);
        return fileDTO;
    }

    // 서버에서 첨부파일 폴더 삭제
    @Override
    public void deleteFolder(String postType, int postId) {
        int cnt = fileDAO.deleteFolder(postType, postId);
        if (cnt < 0) {
            showMessageDialog(null, "삭제에 실패하였습니다.");
        }

        String basePath = ROOT_PATH + "/" + "uploaded" + "/" + postType + "/" + postId;

        File folder = new File(basePath);
        try {
            while (folder.exists()) {
                File[] listFiles = folder.listFiles();

                for (File file : listFiles) {
                    file.delete();
                }

                if (listFiles.length == 0 && folder.isDirectory()) {
                    folder.delete();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 선택한 첨부파일 삭제
    @Transactional
    @Override
    public void deleteFile(String postType, int postId, List<Integer> delFids) {
        for( int fid : delFids ) {
            String fileName = fileDAO.findById(fid).getFileName();
            if(fileDAO.deleteFile(fid) == 1){
                String basePath = ROOT_PATH + "/" + "uploaded" + "/" + postType + "/" + postId + "/" + fileName;
                File file = new File(basePath);
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }
}
