create table usuario_carteira (
	/*SQLSERVER id int IDENTITY primary key,*/
	id serial,
	id_carteira int not null,
	id_usuario int  not null,
	primary key (id),
	foreign key (id_carteira) references carteira(id),
	foreign key (id_usuario) references usuario(id)
);

create table carteira_itens (
	/*SQLSERVER id int IDENTITY primary key,*/
	id serial,
	id_carteira int not null,
	dt_criacao date,
	tipo varchar(2),
	descricao varchar(500),
	valor numeric(10,2),
	primary key (id),
	foreign key (id_carteira) references carteira(id)
);