ALTER TABLE comment CHANGE gubun post_type VARCHAR(20) NULL COMMENT '게시판 정보';
ALTER TABLE comment MODIFY COLUMN post_type VARCHAR(20) NULL COMMENT '게시판 정보';
ALTER TABLE comment CHANGE fk_id post_id int NOT NULL COMMENT '게시물 FK';

update version set version = '202102182030';