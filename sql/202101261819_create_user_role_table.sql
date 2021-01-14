# user table
CREATE TABLE `user` (
                        `u_id` int NOT NULL AUTO_INCREMENT,
                        `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 아이디',
                        `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 비밀번호',
                        `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '사용자 이름',
                        `birth_date` datetime DEFAULT NULL COMMENT '생년월일',
                        `gender` varchar(10) DEFAULT NULL COMMENT '성별(M:남자, W:여자, U:선택안함)',
                        `tel` varchar(50) DEFAULT NULL COMMENT '전화번호',
                        `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '사용자 이메일',
                        `reg_date` timestamp NOT NULL COMMENT '가입일자',
                        `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일자',
                        PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# role table
CREATE TABLE `role` (
                        `r_id` int NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(20) NOT NULL,
                        PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# user_role table
CREATE TABLE `user_role` (
                             `ur_id` int NOT NULL AUTO_INCREMENT,
                             `user_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             `reg_date` timestamp NOT NULL COMMENT '등록일',
                             `mod_date` timestamp NULL DEFAULT NULL COMMENT '수정일',
                             PRIMARY KEY (`ur_id`),
                             KEY `role_FK` (`role_id`),
                             KEY `user_FK` (`user_id`),
                             CONSTRAINT `role_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`r_id`) ON UPDATE CASCADE,
                             CONSTRAINT `user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# role table DML
INSERT INTO role(r_id, role_name)
VALUES(1, 'ADMIN');

INSERT INTO role(r_id, role_name)
VALUES(2, 'USER');


update version set version = '202101261819';
