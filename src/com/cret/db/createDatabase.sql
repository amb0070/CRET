CREATE TABLE projects (
	name varchar(30) NOT NULL,
	PRIMARY KEY (name)
);

CREATE TABLE data (
	name varchar(20) NOT NULL,
	ID varchar(10) NOT NULL,
	byte varchar(2) NOT NULL,
	projectName varchar(30) NOT NULL,
	PRIMARY KEY (name, ID, byte, projectName),
	FOREIGN KEY (projectName) REFERENCES projects(name)
);