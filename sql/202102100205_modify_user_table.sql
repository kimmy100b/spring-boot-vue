ALTER TABLE user MODIFY COLUMN email varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '사용자 이메일';

update version set version = '202102100205';
