USE Loja;
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
	idusuario		INT				NOT NULL auto_increment,
    nome			VARCHAR(45)		NOT NULL UNIQUE,
    senha			VARCHAR(60)		NOT NULL,
	CONSTRAINT pk_id_usuario PRIMARY KEY (idUsuario)
);

