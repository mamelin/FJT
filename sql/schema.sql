CREATE DATABASE IF NOT EXISTS faces;
USE faces;

DROP TABLE IF EXISTS groupMembership;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS messageRecipient;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS pendingfriends;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS profile;

CREATE TABLE profile (
	userID INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) UNIQUE NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(10) NOT NULL,
	bdate DATE NOT NULL,
	picture_URL VARCHAR(255),
	aboutme TEXT NOT NULL,
	lastlogin DATETIME,
	PRIMARY KEY(userID)
);

CREATE TABLE groups (
	gID INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) UNIQUE NOT NULL,
	groups.desc TEXT,
	PRIMARY KEY (gID)
);

CREATE TABLE friends (
	userID1 INTEGER NOT NULL,
	userID2 INTEGER NOT NULL,
	JDate DATE NOT NULL,
	message TEXT NOT NULL,
	FOREIGN KEY fk_friends_user_1 (userID1) REFERENCES profile (userID),
	FOREIGN KEY fk_friends_user_2 (userID2) REFERENCES profile (userID),
	PRIMARY KEY (userID1, userID2)
);

CREATE TABLE pendingfriends (
	fromID INTEGER NOT NULL,
	toID INTEGER NOT NULL,
	message TEXT NOT NULL,
	FOREIGN KEY fk_pendingfriends_user_from (fromID) REFERENCES profile (userID),
	FOREIGN KEY fk_pendingfreinds_user_two (toID) REFERENCES profile (userID),
	PRIMARY KEY (fromID, toID)
);

CREATE TABLE messages (
	msgID INTEGER UNIQUE NOT NULL AUTO_INCREMENT,
	fromID INTEGER,
	message TEXT NOT NULL,
	ToUserID INTEGER,
	ToGroupID INTEGER,
	date DATE NOT NULL,
	PRIMARY KEY (msgID),
	FOREIGN KEY fk_message_user_from (fromID) REFERENCES profile (userID),
	FOREIGN KEY fk_messages_user_to (ToUserID) REFERENCES profile (userID),
	FOREIGN KEY fk_messages_group_to (ToGroupID) REFERENCES groups (gID)
);

CREATE TABLE messageRecipient (
	userID INTEGER NOT NULL,
	msgID INTEGER NOT NULL,
	FOREIGN KEY fk_messageRecipient_user (userID) REFERENCES profile (userID),
	FOREIGN KEY fk_messageRecipient_messages (msgID) REFERENCES messages (msgIdID),
	PRIMARY KEY (userID, msgID)
);

CREATE TABLE groupMembership (
	gID INTEGER AUTO_INCREMENT,
	userID INTEGER,
	FOREIGN KEY fk_groupMembership_group (gID) REFERENCES groups (gID),
	FOREIGN KEY fk_groupMembership_user (userID) REFERENCES profile (userID),
	PRIMARY KEY (gID, userID)
);