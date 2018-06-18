USE tiki_taka;

ALTER DATABASE tiki_taka DEFAULT CHARACTER SET utf8;

/*
drop table user;
drop table team;
drop table member;
drop table matching;
drop table apply;
*/

select * from user;
select * from team;
select * from member;
select * from ground;
select * from ground_thumnail;
select * from matching;
select * from apply;

CREATE TABLE IF NOT EXISTS user(
	id BIGINT(20) AUTO_INCREMENT,
	email VARCHAR(50),
	name VARCHAR(20),
    fcmToken VARCHAR(300),
	pictureUrl VARCHAR(200),
	provider VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS team(
	id BIGINT(20) AUTO_INCREMENT,
	name VARCHAR(20),
    mainArea VARCHAR(50),
	level VARCHAR(4),
	ageGroup VARCHAR(7),
	description VARCHAR(400),
    pictureUrl VARCHAR(200),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member(
	id BIGINT(20) AUTO_INCREMENT,
	userId BIGINT(20),
	teamId BIGINT(20),
	role VARCHAR(20),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ground(
	id BIGINT(20) AUTO_INCREMENT,
	name VARCHAR(100),
	address VARCHAR(200),
    phone VARCHAR(20),
	url VARCHAR(400),
	indoor TINYINT(1),
	park TINYINT(1),
	light TINYINT(1),
	latitude VARCHAR(20),
	longitude VARCHAR(20),
    unit TINYINT(2) default 6,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ground_thumnail(
	id BIGINT(20) AUTO_INCREMENT,
    gid BIGINT(20),
    url VARCHAR(400),
    PRIMARY KEY (id)
);
	
CREATE TABLE IF NOT EXISTS matching(
	id BIGINT(20) AUTO_INCREMENT,
	start DATETIME,
	end DATETIME,
	homeTeamId BIGINT(20),
	awayTeamId BIGINT(20),
	playerNumber TINYINT,
	price INTEGER,
	groundId BIGINT(20),
    createdDate DATETIME,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS apply(
	id BIGINT(20) AUTO_INCREMENT,
	matchingId BIGINT(20),
	teamId BIGINT(20),
    createdDate DATETIME,
	PRIMARY KEY (id)
);

    

