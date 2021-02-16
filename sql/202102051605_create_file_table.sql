CREATE TABLE file (
                       `f_id` int NOT NULL primary KEY AUTO_INCREMENT,
                       `post_id` int not null COMMENT '등록한 게시글 id',
                       `file_name` varchar(50) DEFAULT NULL COMMENT '파일 이름',
                       `file_size` long DEFAULT NULL COMMENT '파일 크기',
                       `file_path` varchar(100) DEFAULT NULL COMMENT '저장 경로(서버)',
                       `upload_user` varchar(30) DEFAULT NULL COMMENT '등록자',
                       `post_type` varchar(20) DEFAULT NULL COMMENT '업로드한 게시판',
                       `upload_date` timestamp NULL DEFAULT NULL COMMENT '첨부 일자',
                       foreign key (post_id) references notice(n_id) on update cascade on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일 첨부 정보 테이블';

update version set version = '202102051405';
