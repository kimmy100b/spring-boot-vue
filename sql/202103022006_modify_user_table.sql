ALTER TABLE study_db.`user` ADD img_url TEXT NULL;
ALTER TABLE study_db.`user` CHANGE img_url img_url TEXT NULL AFTER email;

update version set version = '202103022006';