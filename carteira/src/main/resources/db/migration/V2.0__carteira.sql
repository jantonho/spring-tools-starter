create table carteira (
	id int IDENTITY primary key,
	nome varchar(60) not null,
	valor numeric(10,2) not null
)