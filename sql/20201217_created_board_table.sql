drop table board;

CREATE TABLE `board` (
                         `sid` int NOT NULL AUTO_INCREMENT,
                         `writer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '작성자명',
                         `title` varchar(100) DEFAULT NULL COMMENT '제목',
                         `content` text COMMENT '내용',
                         `reg_date` timestamp NULL DEFAULT NULL COMMENT '등록일자',
                         `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일자',
                         `del_date` timestamp NULL DEFAULT NULL COMMENT '삭제일자',
                         PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판'
