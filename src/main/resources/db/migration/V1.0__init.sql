create table usuario (
	/*SQLSERVER id int IDENTITY primary key,*/
	id serial,
	email varchar(255) not null,
	nome varchar(255) not null,
	senha varchar(255) not null,
	primary key (id)
)