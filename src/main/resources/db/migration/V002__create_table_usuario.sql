CREATE TABLE IF NOT EXISTS NM_USUARIO (
    ID BINARY(16) PRIMARY KEY NOT NULL,
    NOME VARCHAR(50) NOT NULL,
    TELEFONE VARCHAR(16) NOT NULL,
    EMAIL VARCHAR(60) UNIQUE,
    PASSWORD VARCHAR (255) NOT NULL,
    FOTO VARCHAR(100),
    STATUS INTEGER NOT NULL DEFAULT 0,
    DATA_CADASTRO TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DATA_ALTERACAO DATE
)ENGINE=INNODB;
