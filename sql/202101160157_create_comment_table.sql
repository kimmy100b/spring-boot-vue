CREATE TABLE comment (
                           `c_id` int NOT NULL AUTO_INCREMENT,
                           `gubun` int DEFAULT NULL COMMENT '구분(1:board)',
                           `fk_id` int NOT NULL COMMENT '게시물 FK',
                           `level` int DEFAULT '0' COMMENT '레벨',
                           `parent_id` int DEFAULT '0' COMMENT '부모 댓글 id',
                           `writer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '작성자',
                           `content` text NOT NULL COMMENT '댓글 내용',
                           `reg_date` timestamp NULL DEFAULT NULL COMMENT '등록일자',
                           `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일자',
                           PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='댓글 테이블';


update version set version = '202101160157';
