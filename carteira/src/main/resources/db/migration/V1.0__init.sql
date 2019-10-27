create table usuario (
	id int IDENTITY primary key,
	email varchar(255) not null,
	nome varchar(255) not null,
	senha varchar(255) not null
)