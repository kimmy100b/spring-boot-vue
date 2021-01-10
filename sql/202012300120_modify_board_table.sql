drop table board;

CREATE TABLE `board` (
                         `sid` int NOT NULL AUTO_INCREMENT,
                         `writer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '작성자명',
                         `title` varchar(100) DEFAULT NULL COMMENT '제목',
                         `content` text COMMENT '내용',
                         `views` int NOT NULL DEFAULT '0' COMMENT '조회수',
                         `reg_date` timestamp NULL DEFAULT NULL COMMENT '등록일자',
                         `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일자',
                         PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='게시판';

UPDATE version SET version = '202012300120';