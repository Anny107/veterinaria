CREATE DATABASE veterinaria;
USE veterinaria;

CREATE TABLE clientes
(
	idcliente	INT AUTO_INCREMENT PRIMARY KEY,
	apellidos	VARCHAR(50)		NOT NULL,
	nombres		VARCHAR(50)		NOT NULL,
	dni			CHAR(8)			NOT NULL,
	claveAcceso	VARCHAR(200)	NOT NULL,
	create_at	DATETIME 		NOT NULL DEFAULT NOW(),
	update_at	DATETIME			NULL,
	estado		CHAR(1)			NOT NULL DEFAULT "1",
	CONSTRAINT uk_dni_cli UNIQUE(dni)
)ENGINE=INNODB;

INSERT INTO clientes(apellidos,nombres,dni,claveAcceso) VALUES 
('Sanchez Mesias','Daniel','45693247','123456'),
('Lopez Cardenas','Josue','77693211','123456'),
('Cabrera Napa','Anny','71788436','123456');

-- SELECT * FROM clientes;

CREATE TABLE animales
(
	idanimal			INT AUTO_INCREMENT PRIMARY KEY,
	nombreanimal	VARCHAR(50)		NOT NULL,
	create_at		DATETIME 		NOT NULL DEFAULT NOW(),
	update_at		DATETIME 		NULL,
	CONSTRAINT uk_nombreanimal UNIQUE(nombreanimal)
)ENGINE=INNODB;

INSERT INTO animales(nombreanimal) VALUES
('Perro'),
('Gato'),
('Peces');

SELECT * FROM animales;

CREATE TABLE razas
(
	idraza		INT AUTO_INCREMENT PRIMARY KEY,
	idanimal	INT 				NOT NULL,
	nombreraza	VARCHAR(50) 	NOT NULL,
	create_at	DATETIME			NOT NULL DEFAULT NOW(),
	update_at	DATETIME 		NULL,
	CONSTRAINT fk_idanimal_razas FOREIGN KEY (idanimal) REFERENCES animales(idanimal),
	CONSTRAINT uk_nombreraza UNIQUE(nombreraza)
)ENGINE=INNODB;

INSERT INTO razas(idanimal,nombreraza) VALUES
(1,'Pastor alemán'),
(1,'Bulldog'),
(1,'Golden retriever'),
(1,'Rottweiler'),
(2,'Siberiano'),
(2,'Bombay'),
(2,'Persa'),
(2,'Siamés'),
(3,'Payaso'),
(3,'Guppy'),
(3,'Zebra pleco'),
(3,'Goldfish');

-- SELECT * FROM razas;

CREATE TABLE mascotas
(
	idmascota	INT AUTO_INCREMENT PRIMARY KEY,
	idcliente	INT 				NOT NULL,
	idraza		INT 				NOT NULL,
	nombre		VARCHAR(50) 	NOT NULL,
	fotografia	VARCHAR(200)	NULL,
	color			VARCHAR(50)		NOT NULL,
	genero		VARCHAR(50)		NOT NULL,
	create_at	DATETIME			NOT NULL DEFAULT NOW(),
	update_at	DATETIME 		NULL,
	CONSTRAINT fk_idcliente_mas FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
	CONSTRAINT fk_idraza_mas	FOREIGN KEY (idraza) REFERENCES razas(idraza)
)ENGINE=INNODB;

INSERT INTO mascotas (idcliente,idraza,nombre,fotografia,color,genero) VALUES
(1,12,'Goldi','pDorado.jpg','Dorado','Hembra'),
(1,2,'Rocky','bulldog.jpg','Marron con blanco','Macho'),
(2,7,'Felix','persa.jpg','Naranja','Macho'),
(3,4,'Luna','rottweiler.jpg','Negro','Hembra');

-- SELECT * FROM mascotas;
