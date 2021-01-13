alter table notice add views int not null default 0 comment '조회수';

update version set version = '202101131528';
