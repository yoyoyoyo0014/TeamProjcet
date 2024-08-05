drop database if exists minigame;

create database if not exists minigame;

use minigame;

# User Table
drop table if exists `user`;

#비밀번호(me_pw)는 추후에 hash값, char(255)으로 교체 예정 
create table if not exists `user`(
	us_key int auto_increment primary key,
    us_id varchar(20) not null unique,
    us_pw varchar(30) not null,
    us_email varchar(30) not null
);

# Game Table
drop table if exists game;

create table if not exists game(
	gm_key int auto_increment primary key,
    gm_ti varchar(20) not null unique,
    gm_vPoint int not null default 100,
    gm_lPoint int not null default -50
);

# Score Table
drop table if exists score;

create table if not exists score(
	sc_key int auto_increment primary key,
    sc_win int not null default 0,
    sc_draw int not null default 0,
    sc_lose int not null default 0,
    sc_point int not null default 0,
    sc_us_key int not null,
    sc_gm_key int not null,
    
    foreign key(sc_us_key) references `user` (us_key) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key(sc_gm_key) references game (gm_key) ON DELETE CASCADE ON UPDATE CASCADE,
    unique(sc_us_key,sc_gm_key)
);

insert into game(gm_ti) values("BASEBALL"),("OMOK"),("TYPING"),("YACHT"),("SPEEDQUIZ");

insert into user(us_id, us_pw, us_email) values
("qwe", "1234", "qwe@gamil.com"),
("wer", "1234", "wer@gamil.com"),
("asd", "1234", "asd@gamil.com"),
("zxc", "1234", "zxc@gamil.com"),
("sdf", "1234", "sdf@gamil.com"),
("xcv", "1234", "xcv@gamil.com");
