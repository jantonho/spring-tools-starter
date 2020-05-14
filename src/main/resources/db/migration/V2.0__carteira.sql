create table carteira (
	/* SQLSERVER id int IDENTITY primary key,*/
	id serial,
	nome varchar(60) not null,
	valor numeric(10,2) not null,
	primary key (id)
)