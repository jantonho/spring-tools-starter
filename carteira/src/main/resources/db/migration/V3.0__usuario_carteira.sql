create table usuario_carteira (
	id int IDENTITY primary key,
	id_carteira int not null,
	id_usuario int  not null,
	foreign key (id_carteira) references carteira(id),
	foreign key (id_usuario) references usuario(id)
);

create table carteira_itens (
	id int IDENTITY primary key,
	id_carteira int not null,
	dt_criacao date,
	tipo varchar(2),
	descricao varchar(500),
	valor numeric(10,2),
	foreign key (id_carteira) references carteira(id)
);