CREATE DATABASE IF NOT EXISTS feibiao ;

USE feibiao;

drop table if exists tbl_user;

create table tbl_user (
	id bigint(10) NOT NULL AUTO_INCREMENT,
	login_name varchar(64) not null unique,
	nick_name varchar(64) null,
	password varchar(255) not null,
	mobile varchar(64) null,
	user_type varchar(64) null,
	photo varchar(255) null,
	create_date timestamp not null default 0,
	update_date timestamp,
	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


insert into tbl_user (id, login_name, nick_name, password, mobile, user_type, photo, create_date)
values(1,'admin','xiaoou','admin123','13895095322','1',null,'2015-06-12 10:00:00');

insert into tbl_user (id, login_name, nick_name, password, mobile, user_type, photo, create_date) 
values(2,'jenny','xiaoxiao','jenny123','13895095322','2',null,'2015-06-14 12:00:00');
