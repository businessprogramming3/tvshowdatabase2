use tvshow;
drop table if exists tvshow;
create table tvshow(
	id int unsigned not null auto_increment,
	rating int not null,
	seasons int not null,
	name varchar(24) not null,
	director varchar(24) not null,
	writer varchar(24) not null, 
	actor varchar(24) not null,
	primary key(id)
);