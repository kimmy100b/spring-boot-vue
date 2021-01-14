CREATE TABLE `user` (
                        `u_id` int NOT NULL AUTO_INCREMENT,
                        `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 아이디',
                        `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 비밀번호',
                        `name` varchar(30) DEFAULT NULL COMMENT '사용자 이름',
                        `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 이메일',
                        `auth` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '권한',
                        `reg_date` timestamp NOT NULL COMMENT '가입일자',
                        `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일자',
                        PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

UPDATE version SET version = '202101120931';